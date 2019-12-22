package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.database.Environment;
import cn.ma.cei.generator.langs.java.tools.JavaClass;

public class JavaRestfulClientBuilder extends RestfulClientBuilder {
    JavaClass javaClass = null;

    @Override
    public void startClient(String clientDescriptor) {
        javaClass = new JavaClass(clientDescriptor, JavaKeyword.CURRENT_PACKAGE + Environment.getCurrentExchange() + ".services");
    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new JavaRestfulInterfaceBuilder(javaClass);
    }

    @Override
    public void endClient() {
        javaClass.build();
    }


}
