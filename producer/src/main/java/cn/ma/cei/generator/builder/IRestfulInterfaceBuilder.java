package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IRestfulInterfaceBuilder extends IMethodBuilder {

    void setRequestTarget(Variable request, Variable target);

    void addHeader(Variable request, Variable tag, Variable value);

    void defineRequest(Variable request, Variable option);

    void addToQueryString(Variable request, Variable queryStringName, Variable variable);

    void setPostBody(Variable request, Variable postBody);

    void invokeQuery(Variable response, Variable request);

    void setRequestMethod(Variable request, Variable requestMethod);
}
