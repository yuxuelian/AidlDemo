package com.kaibo.aidl.demo.remote

import com.kaibo.aidl.demo.IAidlInterfaceCallBack
import com.kaibo.aidl.demo.IAidlInterfaceDemo
import java.lang.ref.WeakReference

/**
 * @author kaibo
 * @date 2019/4/2 17:54
 * @GitHub：https://github.com/yuxuelian
 * @email：kaibo1hao@gmail.com
 * @description：
 */

class RemoteServiceStub(remoteService: RemoteService) : IAidlInterfaceDemo.Stub() {

    private val weakReference = WeakReference<RemoteService>(remoteService)

    override fun testDemo(test: String): String {
        return weakReference.get()?.testDemo(test) ?: ""
    }

    override fun registerCallback(callback: IAidlInterfaceCallBack) {
        weakReference.get()?.registerCallback(callback)
    }

    override fun unregisterCallback(callback: IAidlInterfaceCallBack) {
        weakReference.get()?.unregisterCallback(callback)
    }
}