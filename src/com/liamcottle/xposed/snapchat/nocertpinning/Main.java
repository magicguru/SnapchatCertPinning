package com.liamcottle.xposed.snapchat.nocertpinning;

import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        if(!lpparam.packageName.equals("com.snapchat.android")) {
            return;
        }

        XposedHelpers.findAndHookMethod(TrustManagerFactory.class, "init", KeyStore.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Removed Snapchat's KeyStore! ;)");
                param.args[0] = null;
            }
        });

    }

}
