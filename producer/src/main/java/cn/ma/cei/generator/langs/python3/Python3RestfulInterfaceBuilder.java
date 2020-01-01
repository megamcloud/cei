/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3RestfulInterfaceBuilder extends RestfulInterfaceBuilder {

    private Python3Method method;
    private Python3Class clientClass;

    public Python3RestfulInterfaceBuilder(Python3Class clientClass) {
        this.clientClass = clientClass;
        method = new Python3Method(clientClass);
    }

    @Override
    public ResponseBuilder getResponseBuilder() {
        return new Python3ResponseBuilder(method);
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        method.invoke(request.nameDescriptor + ".set_target", target);
    }

    @Override
    public void defineRequest(Variable request) {
        method.addAssign(method.defineVariable(request), method.newInstance(request.type, this.queryVariable("options")));
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.defineVariable(response), method.invoke("RestfulConnection.query", request));
    }

    @Override
    public void setUrl(Variable request, Variable url) {
        method.addInvoke(request.nameDescriptor + ".set_url", url);
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.nameDescriptor + ".set_method", requestMethod);
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.addReturn(returnVariable);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        clientClass.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.nameDescriptor + ".add_header", tag, value);
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.nameDescriptor + ".add_query_string", queryStringName, variable);
    }

}
