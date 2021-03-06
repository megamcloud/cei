/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Code;

/**
 *
 * @author U0151316
 */
public class CppCode extends Code {

    public void appendStatementWordsln(String... args) {
        appendWords(args);
        appendln(";");
    }

    public void appendStatementln(String str) {
        appendln(str + ";");
    }

    public void appendInclude(String name) {
        if (name != null && !name.equals("") && !name.equals(BuilderContext.NO_REF)) {
            appendWordsln("#include", name);
        }
    }
    
    public String toCppString(String str) {
        return "\"" + str + "\"";
    }
}
