/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3.tools;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.python3.Python3Code;

/**
 *
 * @author U0151316
 */
public class Python3Method {

    private Python3Code code = new Python3Code();
    private Python3Class parent;

    public Python3Method(Python3Class parent) {
        this.parent = parent;
    }

    public String defineVariable(Variable variable) {
        return variable.nameDescriptor;
    }

    public String useVariable(Variable variable) {
        return variable.nameDescriptor;
    }

    public String newInstance(VariableType type, Variable... params) {
        parent.addReference(type);
        return type.getDescriptor() + "(" + invokeParamString(params) + ")";
    }

    public void addReturn(Variable variable) {
        code.appendWordsln("return", variable.nameDescriptor);
    }

    public void startFor(Variable item, String statement) {
        code.appendWordsln("for", item.nameDescriptor, "in", statement);
        code.startBlock();
    }

    public void endFor() {
        code.endBlock();
    }

    public void startIf(String statement) {
        code.appendWordsln("if", statement + ":");
        code.startBlock();
    }

    public void endIf() {
        code.endBlock();
    }

    public void addAssign(String left, String right) {
        code.appendWordsln(left, "=", right);
    }

    public void addInvoke(String method, Variable... params) {
        code.appendWordsln(invoke(method, params));
    }

    public String invoke(String method, Variable... params) {
        return method + "(" + invokeParamString(params) + ")";
    }

    public void startMethod(VariableType returnType, String methodName, VariableList params) {
        String paramString = defineParamString(params);
        if (paramString.isEmpty()) {
            code.appendWordsln("def", methodName + "(self):");
        } else {
            code.appendWordsln("def", methodName + "(self, " + defineParamString(params) + "):");
        }
        code.startBlock();
    }

    public void startStaticMethod(VariableType returnType, String methodName, VariableList params) {
        code.appendln("@staticmethod");
        startMethod(returnType, methodName, params);
    }

    public Python3Code getCode() {
        return code;
    }

    public void endMethod() {
        code.endBlock();
    }

    private String invokeParamString(Variable... params) {
        if (params == null) {
            return "";
        }
        String paramString = "";
        for (Variable p : params) {
            if (p == null) {
                continue;
            }
            if (paramString.equals("")) {
                paramString += p.nameDescriptor;
            } else {
                paramString += ", " + p.nameDescriptor;
            }
        }
        return paramString;
    }

    private String defineParamString(VariableList params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        String paramString = "";
        for (Variable variable : params.getVariableList()) {
            if (paramString.equals("")) {
                paramString += variable.nameDescriptor;
            } else {
                paramString += ", " + variable.nameDescriptor;
            }
        }
        return paramString;
    }
}