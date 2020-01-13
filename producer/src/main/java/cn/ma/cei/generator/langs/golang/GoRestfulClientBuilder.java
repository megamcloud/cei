/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.generator.langs.golang.tools.GoMethod;
import cn.ma.cei.generator.langs.golang.tools.GoPtrVar;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;

/**
 *
 * @author U0151316
 */
public class GoRestfulClientBuilder extends RestfulClientBuilder {

    private GoFile clinetFile;
    private GoStruct clinetStruct;

    public GoRestfulClientBuilder(GoFile clientFile) {
        this.clinetFile = clientFile;
    }

    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        clinetStruct = new GoStruct(clientDescriptor);
        clinetStruct.addMember(new GoPtrVar(VariableFactory.createLocalVariable(RestfulOptions.getType(), "options")));
        GoMethod constructor = new GoMethod(null);
        constructor.getCode().appendWordsln("func", "New" + clientDescriptor + "(options *" + RestfulOptions.getType().getDescriptor() + ")", "*" + clientDescriptor, "{");
        constructor.getCode().newBlock(() -> {
            constructor.getCode().appendWordsln("inst", ":=", "new(" + clientDescriptor + ")");
            Variable url = VariableFactory.createHardcodeStringVariable(options.url);
            constructor.getCode().appendWordsln("if", "options", "!=", "nil", "{");
            constructor.getCode().newBlock(() -> {
                constructor.getCode().appendln("inst.options = *options");
            });
            constructor.getCode().appendln("} else {");
            constructor.getCode().newBlock(() -> {
                constructor.getCode().appendWordsln("inst.options.URL", "=", url.getDescriptor());
                if (options.connectionTimeout != null) {
                    constructor.getCode().appendWordsln("inst.options.connectionTimeout", "=", options.connectionTimeout.toString());
                }
            });
            constructor.getCode().appendln("}");
            constructor.getCode().appendWordsln("return", "inst");
        });
        constructor.getCode().appendWordsln("}");
        clinetStruct.addMethod(constructor);
    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new GoRestfulInterfaceBuilder(clinetStruct);
    }

    @Override
    public void endClient() {
        clinetFile.addStruct(clinetStruct);
    }

}