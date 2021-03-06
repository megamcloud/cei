/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.exception;

import cn.ma.cei.utils.ReflectionHelper;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Stack;

import io.vertx.core.json.JsonObject;
import javafx.util.Pair;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author u0151316
 */
public class BuildTracer {

    private static int currentLevel = 0;
    private static final Stack<Pair<Integer, JsonObject>> info = new Stack<>();

    public static void startBuilding(Object obj) {
        if (obj.getClass().isAnnotationPresent(XmlRootElement.class)) {
            String name = obj.getClass().getAnnotation(XmlRootElement.class).name();
            List<Field> fields = ReflectionHelper.getAllFields(obj);
            JsonObject json = new JsonObject();
            fields.forEach(item -> {
                if (item.isAnnotationPresent(XmlAttribute.class)) {
                    try {
                        Object value = item.get(obj);
                        if (value != null) {
                            json.put(item.getName(), value);
                        }
                    } catch (Exception e) {
                        // Cannot process the field
                    }
                }
            });
            json.put("CEITypeID", name);
            info.push(new Pair<>(currentLevel++, json));
        } else {
            // Cannot trace this item.
        }
    }

    public static String getTraceString() {
        StringBuilder stringBuilder = new StringBuilder();
        info.forEach(item -> {
            int level = item.getKey();
            for (int i = 0; i < level; i++) {
                stringBuilder.append("  ");
            }
            stringBuilder.append(item.getValue().toString());
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    public static void endBuilding() {
        currentLevel--;
        info.pop();
    }
}
