package com.kaibo.aidl.demo.remote

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.kaibo.aidl.demo.IAidlInterfaceCallBack
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @author kaibo
 * @date 2019/4/2 17:54
 * @GitHub：https://github.com/yuxuelian
 * @email：kaibo1hao@gmail.com
 * @description：
 */

class RemoteService : Service() {

    private val remoteCallbackList = RemoteCallbackList<IAidlInterfaceCallBack>()

    private var disposable: Disposable? = null

    override fun onBind(intent: Intent?): IBinder = RemoteServiceStub(this)

    fun testDemo(test: String): String {
        Log.d("AidlDemo", "RemoteService  testDemo   test = $test")
        return "远程方法调用成功"
    }

    fun registerCallback(callback: IAidlInterfaceCallBack) {
        remoteCallbackList.register(callback)
    }

    fun unregisterCallback(callback: IAidlInterfaceCallBack) {
        remoteCallbackList.unregister(callback)
    }

    override fun onCreate() {
        super.onCreate()
        disposable = Observable.interval(1000, TimeUnit.MILLISECONDS).subscribe({ along ->
            remoteCallbackList.beginBroadcast()
            val count = remoteCallbackList.registeredCallbackCount
            repeat(count) { index ->
                val callBack: IAidlInterfaceCallBack? = remoteCallbackList.getBroadcastItem(index)
                callBack?.callBack(along)
            }
            remoteCallbackList.finishBroadcast()
        }) {
            it.printStackTrace()
        }
    }

    override fun onDestroy() {
        remoteCallbackList.kill()
        if (disposable?.isDisposed != true) {
            disposable?.dispose()
        }
        super.onDestroy()
    }
}