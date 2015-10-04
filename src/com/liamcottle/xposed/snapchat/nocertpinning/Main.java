package com.liamcottle.xposed.snapchat.nocertpinning;

import android.app.Application;
import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        if(!lpparam.packageName.equals("com.snapchat.android")) {
            return;
        }

        XposedBridge.log("Loaded Package: com.snapchat.android");

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                XposedBridge.log("Hooked Method: " + param.method.getName());

                Class aauClass = XposedHelpers.findClass("aau", lpparam.classLoader);

                XposedHelpers.findAndHookConstructor(aauClass, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        XposedBridge.log("Hooked Constructor: " + param.method.getName());
                        XposedHelpers.setBooleanField(param.thisObject, "mAllowURIMigration", false);
                    }
                });

            }
        });

    }

}
