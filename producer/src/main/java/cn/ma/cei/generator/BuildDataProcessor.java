package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.dataprocessor.*;
import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.model.json.xJsonBuilder;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.processor.*;
import cn.ma.cei.model.string.xStringBuilder;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xProcedure;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.RegexHelper;

/**
 * To build each line in User Procedure.
 */
public class BuildDataProcessor {

    public static class Context {
        public xProcedure procedure = null;
        public String returnVariableName = null;
        public VariableType specifiedReturnType = null;
        public Variable defaultInput = null;
        public IDataProcessorBuilder dataProcessorBuilder = null;
    }

    private static final NormalMap<Class<?>, DataProcessorBase<?>> processorMap = new NormalMap<>();

    static {
        processorMap.put(xGetNow.class, new BuildGetNow());
        processorMap.put(xBase64.class, new BuildBase64());
        processorMap.put(xHmacSHA256.class, new BuildHmacSHA256());
        processorMap.put(xGetRequestInfo.class, new BuildGetRequestInfo());
        processorMap.put(xAddQueryString.class, new BuildAddQueryString());
        processorMap.put(xCombineQueryString.class, new BuildCombineQueryString());
        processorMap.put(xJsonParser.class, new BuildJsonParser());
        processorMap.put(xJsonBuilder.class, new BuildJsonBuilder());
        processorMap.put(xStringBuilder.class, new BuildStringWrapper());
        processorMap.put(xURLEscape.class, new BuildURLEscape());
        processorMap.put(xGZip.class, new BuildGZip());
        // processorMap.put(xInvoke.class);
    }

    public static VariableType getReturnType(xProcedure procedure, String returnVariableName) {
        if (procedure == null || Checker.isNull(procedure.items)) {
            return null;
        }
        if (procedure.items.size() == 1 && Checker.isEmpty(returnVariableName)) {
            // Only one item in the processor list, return the only item. Do not define output name in this item.
            xDataProcessorItem firstItem = procedure.items.get(0);
            if (processorMap.containsKey(firstItem.getClass())) {
                return processorMap.get(firstItem.getClass()).callReturnType(firstItem);
            } else {
                reportNotSupporting(firstItem);
            }
        } else {
            if (RegexHelper.isReference(returnVariableName) == null) {
                return xString.inst.getType();
            }
            for (xDataProcessorItem item : procedure.items) {
                if (processorMap.containsKey(item.getClass())) {
                    DataProcessorBase<?> processor = processorMap.get(item.getClass());
                    String resultInProcessor = processor.callResultVariableName(item);
                    if (!Checker.isEmpty(resultInProcessor))
                        if (resultInProcessor.equals(returnVariableName)) {
                            return processor.callReturnType(item);
                        }
                } else {
                    reportNotSupporting(item);
                }
            }
        }
        return null;
    }

    private static void reportNotSupporting(xDataProcessorItem item) {
        CEIErrors.showFailure(CEIErrorType.CODE, "Processor is not supporting %s", item.getClass().getName());
    }


    public static Variable build(Context context) {
        if (context.dataProcessorBuilder == null) {
            CEIErrors.showCodeFailure(BuildDataProcessor.class, "DataProcessorBuilder is null");
        }
        if (context.procedure == null || Checker.isNull(context.procedure.items)) {
            return null;
        }
        Variable result = null;
        if (Checker.isEmpty(context.returnVariableName)) {
            if (context.procedure.items.size() == 1) {
                // Only one item in the processor list, return the only item. Do not define output name in this item.
                result = processSingleItem(context.procedure.items.get(0), context.defaultInput, context.dataProcessorBuilder);
            }
        } else {
            context.procedure.items.forEach(item -> {
                processSingleItem(item, context.defaultInput, context.dataProcessorBuilder);
            });

            result = GlobalContext.getCurrentMethod().queryVariableOrConstant(context.returnVariableName, context.dataProcessorBuilder);
            if (result == null) {
                CEIErrors.showFailure(CEIErrorType.XML, "Cannot find the result variable: %s", RegexHelper.isReference(context.returnVariableName));
            }
        }
        if (context.specifiedReturnType != null && result != null) {
            return TypeConverter.convertType(result, context.specifiedReturnType, context.dataProcessorBuilder);
        } else {
            return result;
        }
    }

    public static void build(xProcedure procedure, IDataProcessorBuilder builder) {
        if (procedure != null && !Checker.isNull(procedure.items)) {
            procedure.items.forEach(item -> {
                processSingleItem(item, null, builder);
            });
        }
    }

    public static Variable build(xProcedure procedure, Variable defaultInput, String resultVariableName, IDataProcessorBuilder builder) {
        if (procedure == null || Checker.isNull(procedure.items)) {
            return null;
        }
        if (Checker.isEmpty(resultVariableName)) {
            if (procedure.items.size() == 1) {
                // Only one item in the processor list, return the only item. Do not define output name in this item.
                return processSingleItem(procedure.items.get(0), defaultInput, builder);
            } else {
                CEIErrors.showFailure(CEIErrorType.XML, "Must define result, there are multi-processor items.");
            }
        }
        procedure.items.forEach(item -> {
            processSingleItem(item, defaultInput, builder);
        });

        Variable result = GlobalContext.getCurrentMethod().queryVariable(resultVariableName);
        if (result == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot find the result variable: %s", RegexHelper.isReference(resultVariableName));
        }
        return result;
    }

    private static Variable processSingleItem(xDataProcessorItem item, Variable defaultInput, IDataProcessorBuilder builder) {
        if (processorMap.containsKey(item.getClass())) {
            return processorMap.get(item.getClass()).callBuild(item, defaultInput, builder);
        } else {
            // TODO
            CEIErrors.showFailure(CEIErrorType.CODE, "not supported");
            return null;
        }
    }
}
