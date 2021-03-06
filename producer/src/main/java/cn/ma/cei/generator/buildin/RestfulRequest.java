package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class RestfulRequest {

    static public class RequestMethod {

        public final static String GET = "get";
        public final static String POST = "post";
    }

    public final static String typeName = "RestfulRequest";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
