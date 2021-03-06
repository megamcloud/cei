/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.langs.golang.GoCode;
import cn.ma.cei.utils.UniqueList;
import cn.ma.cei.utils.WordSplitter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author U0151316
 */
public class GoStruct {

    private GoCode code = new GoCode();
    private String structName;

    private UniqueList<String, GoVar> publicMemberList = new UniqueList<>();
    private UniqueList<String, GoVar> privateMemberList = new UniqueList<>();
    private Set<String> importList = new HashSet<>();
    private List<GoMethod> methodList = new LinkedList<>();

    public GoStruct(String structName) {
        this.structName = structName;
    }

    public GoCode getCode() {
        return code;
    }

    public void addPublicMember(GoVar memberVariable) {
        if (privateMemberList.containsKey(memberVariable.getName())) {
            throw new CEIException("Duplicate member in GoStruct");
        }
        publicMemberList.put(memberVariable.getName(), memberVariable);
    }

    // TODO to be removed, use Variable.PRIVATE instead of
    public void addPrivateMember(GoVar memberVariable) {
        if (publicMemberList.containsKey(memberVariable.getName())) {
            throw new CEIException("Duplicate member in GoStruct");
        }
        privateMemberList.put(memberVariable.getName(), memberVariable);
    }

    public void addMethod(GoMethod method) {
        methodList.add(method);
    }

    public String getStructName() {
        return structName;
    }

    public void addReference(VariableType type) {
        importList.addAll(type.getReferences());
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void build() {
        defineStruct();
    }

    private void defineStruct() {
        code.appendWordsln("type", structName, "struct", "{");
        code.newBlock(this::defineMembers);
        code.appendln("}");
        writeMethod();
    }

    private void defineMembers() {
        int maxMemberLen = 0;

        for (GoVar member : publicMemberList.values()) {
            if (member.getNameDescriptor().length() > maxMemberLen) {
                maxMemberLen = member.getNameDescriptor().length();
            }
        }
        for (GoVar member : privateMemberList.values()) {
            if (member.getNameDescriptor().length() > maxMemberLen) {
                maxMemberLen = member.getNameDescriptor().length();
            }
        }
        maxMemberLen++;
        for (GoVar member : publicMemberList.values()) {
            code.addMemberln(member.getNameDescriptor(), member.getTypeDescriptor(), maxMemberLen);
        }
        for (GoVar member : privateMemberList.values()) {
            code.addMemberln(WordSplitter.getLowerCamelCase(member.getNameDescriptor()), member.getTypeDescriptor(), maxMemberLen);
        }
    }

    private void writeMethod() {
        if (!methodList.isEmpty()) {
            code.endln();
        }
        methodList.forEach(method -> {
            code.appendCode(method.getCode());
            code.endln();
        });
    }
}
