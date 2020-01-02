/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.JsonBuilderBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

/**
 *
 * @author u0151316
 */
public class Python3JsonBuilderBuilder extends JsonBuilderBuilder {

    private Python3Method method;

    public Python3JsonBuilderBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject) {
        method.addAssign(method.defineVariable(jsonObject), method.newInstance(jsonObject.type));
    }

    @Override
    public void addJsonString(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.nameDescriptor + ".add_json_string", itemName, from);
    }

    @Override
    public void addJsonInteger(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.nameDescriptor + ".add_json_integer", itemName, from);
    }

    @Override
    public void addJsonLong(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.nameDescriptor + ".add_json_long", itemName, from);
    }

    @Override
    public void addJsonBoolean(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.nameDescriptor + ".add_json_boolean", itemName, from);
    }

    @Override
    public void addJsonDecimal(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.nameDescriptor + ".add_json_decimal", itemName, from);
    }
}
