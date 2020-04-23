/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IBuilderBase;
import cn.ma.cei.model.restful.xAuthentication;
import cn.ma.cei.model.websocket.xWSAuthentication;
import cn.ma.cei.utils.Checker;

/**
 * @author U0151316
 */
public class BuildAuthentication {

    public static void buildRestful(xAuthentication authentication, IBuilderBase builder) {
        Checker.isNull(builder, BuildAuthentication.class, "AuthenticationBuilder");


//        Variable request = GlobalContext.getCurrentMethod().createInputVariable(RestfulRequest.getType(), "request");
//        Variable options = GlobalContext.getCurrentMethod().createInputVariable(RestfulOptions.getType(), "option");
//
//        builder.startMethod(null,
//                GlobalContext.getCurrentMethod().getDescriptor(),
//                GlobalContext.getCurrentMethod().getInputVariableList());
//
//        BuildDataProcessor.build(authentication.items,
//                Checker.checkBuilder(builder.createDataProcessorBuilder(), builder.getClass(), "DataProcessorBuilder"));
//        builder.endMethod(null);
    }

    public static void buildWebSocket(xWSAuthentication authentication, IBuilderBase builder) {

    }
}
