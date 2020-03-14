package cn.ma.cei.finalizer;

import cn.ma.cei.model.base.xReferable;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Finalizer {
    private Map<String, xSDK> sdkMap = new HashMap<>();
    private List<xSDK> orgSDKList = new LinkedList<>();

    public void addSDK(List<xSDK> sdks) {
        for (xSDK sdk : sdks) {
            addSDK(sdk);
        }
    }

    public void addSDK(xSDK sdk) {
        // Register Database
        //  - Model
        //  - Restful
        //  - Signature
        if (sdk.modelList != null) {
            sdk.modelList.forEach(model -> XMLDatabase.registerModel(sdk.name, model.name, model));
        }
        orgSDKList.add(sdk);
    }

    public List<xSDK> finalizeSDK() {
        System.out.println("-- Start finalize");
        // Merge SDK
        orgSDKList.forEach(sdk-> {
            if (sdkMap.containsKey(sdk.name)) {
                xSDK orgSdk = sdkMap.get(sdk.name);
                orgSdk.merge(sdk);
            } else {
                sdkMap.put(sdk.name, sdk);
            }
        });

        sdkMap.values().forEach((sdk) -> {
            sdk.doCheck();
            XMLDatabase.attachSDK(sdk);
        });

        // Check model dependency
        sdkMap.values().forEach(sdk -> {
            if (Checker.isNull(sdk.modelList)) {
                return;
            }
            Dependence<xModel> dependence = new Dependence<>();
            sdk.modelList.forEach((model) -> {
                if (Checker.isNull(model.memberList)) {
                    return;
                }
                boolean referToOther = false;
                for (xType item : model.memberList) {
                    if (item instanceof xReferable) {
                        referToOther = true;
                        xModel refer = XMLDatabase.lookupModel(sdk.name, ((xReferable) item).model);
                        dependence.addNode(model, refer);
                    }
                }
                if (!referToOther) {
                    dependence.addNode(model, null);
                }
            });
            sdk.modelList = dependence.decision();
        });
        // Check error
        System.out.println("-- Start end");

        return new LinkedList<>(sdkMap.values());
    }
}
