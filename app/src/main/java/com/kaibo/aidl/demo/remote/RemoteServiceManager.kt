package com.kaibo.aidl.demo.remote

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.kaibo.aidl.demo.IAidlInterfaceCallBack
import com.kaibo.aidl.demo.IAidlInterfaceDemo

/**
 * @author kaibo
 * @date 2019/4/2 18:06
 * @GitHub：https://github.com/yuxuelian
 * @email：kaibo1hao@gmail.com
 * @description：
 */

object RemoteServiceManager {
    private var iAidlInterfaceDemo: IAidlInterfaceDemo? = null

    fun testDemo(test: String): String {
        return iAidlInterfaceDemo?.testDemo(test) ?: ""
    }

    fun registerCallback(callback: IAidlInterfaceCallBack) {
        iAidlInterfaceDemo?.registerCallback(callback)
    }

    fun unregisterCallback(callback: IAidlInterfaceCallBack) {
        iAidlInterfaceDemo?.unregisterCallback(callback)
    }

    fun bindService(context: Context, connected: () -> Unit = {}, disconnected: () -> Unit = {}): BindToken? {
        context.startService(Intent(context, RemoteService::class.java))
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                iAidlInterfaceDemo = IAidlInterfaceDemo.Stub.asInterface(service)
                connected()
            }

            override fun onServiceDisconnected(name: ComponentName) {
                disconnected()
                iAidlInterfaceDemo = null
            }
        }
        return if (context.bindService(
                Intent(context, RemoteService::class.java),
                serviceConnection,
                Context.BIND_IMPORTANT
            )
        ) {
            BindToken(context, serviceConnection)
        } else {
            null
        }
    }

    fun unbindService(bindToken: BindToken?) {
        bindToken?.serviceConnection?.let {
            bindToken.context.unbindService(it)
        }
    }

    data class BindToken(
        val context: Context,
        val serviceConnection: ServiceConnection
    )
}