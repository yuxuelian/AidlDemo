package com.kaibo.aidl.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kaibo.aidl.demo.remote.RemoteServiceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var bindToken: RemoteServiceManager.BindToken? = null

    private val iAidlInterfaceCallBack: IAidlInterfaceCallBack = object : IAidlInterfaceCallBack.Stub() {
        override fun callBack(along: Long) {
            Log.d("AidlDemo", "远程回调  along = $along")
            runOnUiThread {
                text.text = "远程回调  along = $along"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindToken = RemoteServiceManager.bindService(this, connected = {
            val remoteRes = RemoteServiceManager.testDemo("123123123123")
            Log.d("AidlDemo", "remoteRes = $remoteRes")
            RemoteServiceManager.registerCallback(iAidlInterfaceCallBack)
        })
    }

    override fun onDestroy() {
        RemoteServiceManager.unregisterCallback(iAidlInterfaceCallBack)
        RemoteServiceManager.unbindService(bindToken)
        super.onDestroy()
    }
}
