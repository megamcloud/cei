package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3WebSocketImplementationBuilder implements IWebSocketImplementationBuilder {
    public Python3Method method;

    public Python3WebSocketImplementationBuilder(Python3Class clientClass) {
        this.method = new Python3Method(clientClass);
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new Python3DataProcessorBuilder(method);
    }

    @Override
    public void send(Variable send) {
        method.addInvoke("self.send_ws", send);
    }

    @Override
    public void callback(Variable callbackVariable, Variable response) {
        method.addInvoke(callbackVariable.getDescriptor() + ".invoke", response);
    }
}
