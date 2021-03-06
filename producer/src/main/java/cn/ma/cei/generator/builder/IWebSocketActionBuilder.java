package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.sMethod;

public interface IWebSocketActionBuilder extends IBuilderBase {
    void startAction();

    IWebSocketImplementationBuilder createImplementationBuilderForTrigger();

    IWebSocketImplementationBuilder createImplementationBuilderForResponse();

    void newAction(Variable action);

    void setAsPersistentAction(Variable action);

    void registerAction(Variable action);

    void setTriggerToAction(Variable action, sMethod triggerMethod);

    void setActionToAction(Variable action, sMethod actionMethod);

    void endAction();
}
