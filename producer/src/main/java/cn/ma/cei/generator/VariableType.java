package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.TwoTuple;
import cn.ma.cei.utils.UniqueList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VariableType {

    public static VariableType VOID = new VariableType();

    private UniqueList<String, sMethod> methods = new UniqueList<>();
    private UniqueList<String, Variable> members = new UniqueList<>();

    private final String typeName;
    private final List<VariableType> genericList = new LinkedList<>();

    private VariableType() {
        this.typeName = "CEI_VOID_TYPE";
    }

    public VariableType(String typeName, VariableType... subTypes) {
        if (subTypes != null && subTypes.length != 0) {
            Collections.addAll(genericList, subTypes);
        }
        this.typeName = typeName;
    }

    public sMethod createMethod(String methodName) {
        if (methods.containsKey(methodName)) {
            throw new CEIException("[VariableType] Duplicate method: " + methodName);
        }
        sMethod method = new sMethod(this, methodName);
        methods.put(methodName, method);
        return method;
    }

    public sMethod getMethod(String methodName) {
        if (methods.containsKey(methodName)) {
            return methods.get(methodName);
        }
        return null;
    }

    public Variable addPrivateMember(VariableType type, String memberName) {
        if (members.containsKey(memberName)) {
            throw new CEIException("[VariableType] Duplicate member: " + memberName);
        }
        Variable member = VariableCreator.createPrivateMemberVariable(type, memberName);
        members.put(memberName, member);
        return member;
    }

    public Variable addMember(VariableType type, String memberName) {
        if (members.containsKey(memberName)) {
            throw new CEIException("[VariableType] Duplicate member: " + memberName);
        }
        Variable member = VariableCreator.createMemberVariable(type, memberName);
        members.put(memberName, member);
        return member;
    }

    public Variable getMember(String memberName) {
        return members.get(memberName);
    }

    public Variable tryGetMember(String memberName) {
        Variable result = members.get(memberName);
        if (result == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot get the member: %s in model: %s", memberName, typeName);
        }
        return result;
    }

//    public boolean isGeneric() {
//        return !genericList.isEmpty();
//    }

    public List<VariableType> getGenericList() {
        return new LinkedList<>(genericList);
    }

    public String getName() {
        return typeName;
    }

    public boolean isValid() {
        return !(typeName == null || typeName.equals(""));
    }

//    public boolean equalTo(VariableType obj) {
//        return this.equals(obj);
//    }

    public boolean equalTo(String typeName) {
        return this.typeName.equals(typeName);
    }

//    public boolean isCustomModel() {
//        return VariableFactory.isCustomModel(this);
//    }

//    public boolean isCustomModelArray() {
//        if (!typeName.contains("array#")) {
//            return false;
//        }
//        if (genericList.isEmpty()) {
//            return false;
//        }
//        return VariableFactory.isCustomModel(genericList.get(0));
//    }

    // TODO remove it,
    // Calculate the descriptor in VariableFactory.
    public String getDescriptor() {
        TwoTuple<String, List<String>> modelInfo = GlobalContext.getModelInfo(typeName);
        if (modelInfo == null) {
            throw new CEIException("[] cannot find the model " + typeName);
        }
        return GlobalContext.getModelInfo(typeName).get1();
    }

    public List<String> getReferences() {
        TwoTuple<String, List<String>> modelInfo = GlobalContext.getModelInfo(typeName);
        if (modelInfo == null) {
            throw new CEIException("[] cannot find the model " + typeName);
        }
        return GlobalContext.getModelInfo(typeName).get2();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VariableType tmp = (VariableType) o;
        return typeName.equals(tmp.typeName);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.typeName);
        return hash;
    }
}
