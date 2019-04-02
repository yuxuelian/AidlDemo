// IAidlInterfaceDemo.aidl
package com.kaibo.aidl.demo;

import com.kaibo.aidl.demo.IAidlInterfaceCallBack;

interface IAidlInterfaceDemo {
    String testDemo(String test);
    void registerCallback(IAidlInterfaceCallBack callback);
    void unregisterCallback(IAidlInterfaceCallBack callback);
}
