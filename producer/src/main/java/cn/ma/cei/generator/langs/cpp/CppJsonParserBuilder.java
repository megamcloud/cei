/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.environment.Variable;

/**
 *
 * @author U0151316
 */
public class CppJsonParserBuilder extends JsonParserBuilder {

    @Override
    public void getJsonString(Variable to, Variable jsonObject, String itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, String itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, String itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonObjectArray(Variable jsonObject, Variable parentJsonObject, String itemName) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endJsonObjectArrayLoop(Variable to, Variable model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineModel(Variable model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}