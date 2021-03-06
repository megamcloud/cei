package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IWebSocketImplementationBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.websocket.xAction;
import cn.ma.cei.model.websocket.xWSConnection;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;
import java.util.List;

public class BuildWebSocketConnection {
    public static void build(xWSConnection connection, List<xAction> actions, IWebSocketInterfaceBuilder builder) {
        sMethod connectMethod = GlobalContext.getCurrentMethod();
        //Variable ooo = connectMethod.getVariable("option");
        Variable option = connectMethod.getVariable("option");

        // Build input
        List<Variable> inputVariableList = new LinkedList<>();
        if (connection.inputList != null) {
            connection.inputList.forEach((input) -> input.doBuild(() -> {
                Variable inputVariable = connectMethod.createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
            }));
        }

        builder.onAddReference(WebSocketConnection.getType());
        builder.startMethod(null, connectMethod.getDescriptor(), inputVariableList);
        {
            // Actions
            if (actions != null) {
                actions.forEach((action) -> action.doBuild(() -> {
                    BuildWebSocketAction.build(action,
                            Checker.checkNull(builder.createWebSocketActionBuilder(), builder, "WebSocketActionBuilder"));
                }));
            }

            if (connection.onConnect != null && connection.onConnect.send != null) {
                sMethod onConnect = connectMethod.createNestedMethod("onConnect");
                GlobalContext.setCurrentMethod(onConnect);
                Variable connectionVariable = onConnect.createInputVariable(WebSocketConnection.getType(), "connection");
                IWebSocketImplementationBuilder implementationBuilder =
                        Checker.checkNull(builder.createOnConnectBuilder(), builder, "OnConnectBuilder");
                BuildWebSocketImplementation.buildSendInAction(connection.onConnect.send, connectionVariable, implementationBuilder);
                builder.setupOnConnect(onConnect);
                GlobalContext.setCurrentMethod(connectMethod);
            }

            // Connect
            IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
            Variable url = GlobalContext.getCurrentMethod().queryVariableOrConstant(connection.url.target, xString.inst.getType(), dataProcessorBuilder);
            // TODO
            // if url is not string, convert to string
            builder.connect(url, option);
        }
        builder.endMethod();
    }
}
