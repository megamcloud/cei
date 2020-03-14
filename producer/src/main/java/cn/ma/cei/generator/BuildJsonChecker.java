package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.buildin.JsonChecker;
import cn.ma.cei.model.json.xJsonChecker;
import cn.ma.cei.model.json.xJsonEqual;
import cn.ma.cei.model.json.xJsonNotEqual;

public class BuildJsonChecker {
    static class JsonCheckContext {
        Variable jsonCheckerObject;
        IJsonCheckerBuilder jsonCheckerBuilder;
        Variable key;
        Variable value;
    }

    public static void build(xJsonChecker jsonChecker, Variable jsonParser, IJsonCheckerBuilder builder, IJsonCheckerBuilder.UsedFor usedFor) {
        Variable jsonCheckerVar = GlobalContext.getCurrentMethod().createLocalVariable(JsonChecker.getType(), "jsonChecker");
        builder.defineJsonChecker(jsonCheckerVar, jsonParser);
        jsonChecker.items.forEach(item -> {
            JsonCheckContext context = new JsonCheckContext();
            context.jsonCheckerObject = jsonCheckerVar;
            context.jsonCheckerBuilder = builder;

            if (item instanceof xJsonEqual) {
                ProcessEqual((xJsonEqual) item, builder);
            } else if (item instanceof xJsonNotEqual) {
                ProcessNotEqual((xJsonNotEqual) item);
            }
        });
        if (usedFor == IJsonCheckerBuilder.UsedFor.REPORT_ERROR) {
            builder.reportError(jsonCheckerVar);
        } else if (usedFor == IJsonCheckerBuilder.UsedFor.RETURN_RESULT) {
            builder.returnResult(jsonCheckerVar);
        }
    }

    private static void ProcessEqual(xJsonEqual jsonEqual, IJsonCheckerBuilder builder) {
//        Variable key = VariableFactory.createHardcodeStringVariable(jsonEqual.key);
//        builder.setEqual();
    }

    private static void ProcessNotEqual(xJsonNotEqual jsonNotEqual) {

    }
}