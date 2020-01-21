package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.generator.langs.java.buildin.TheLinkedList;

public class JavaJsonParserBuilder extends JsonParserBuilder {

    private final JavaMethod method;

    public JavaJsonParserBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getString", itemName));
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getLong", itemName));
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getStringArray", itemName));
    }

    @Override
    public void startJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObject", itemName));
    }

    @Override
    public void endJsonObject(Variable to, Variable model) {
        if (to != null) {
            method.addAssign(method.useVariable(to), method.useVariable(model));
        }
    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {
        method.startFor(eachItemJsonObject, method.invoke(parentJsonObject.getDescriptor() + ".getObjectArray", itemName));
    }

    @Override
    public void endJsonObjectArray(Variable to, Variable model) {
        method.startIf(to.getDescriptor() + " == null");
        method.addAssign(method.useVariable(to), method.newInstance(TheLinkedList.getType()));
        method.endIf();
        method.addInvoke(to.getDescriptor() + ".add", model);
        method.endFor();
    }

    @Override
    public void startArray(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {
        method.startFor(eachItemJsonObject, method.invoke(parentJsonObject.getDescriptor() + ".getArray", itemName));
    }

    @Override
    public void endJsonArray(Variable to, Variable model) {
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = VariableFactory.createConstantVariable(responseVariable.getDescriptor() + ".getJson()");
        method.addAssign(method.defineVariable(jsonObject), method.useVariable(value));
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getBoolean", itemName));
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".getDecimal", itemName));
    }
}
