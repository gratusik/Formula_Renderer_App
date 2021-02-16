package com.gratus.formularendererapp.view.base

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.gratus.formularendererapp.R
import com.gratus.formularendererapp.util.constants.AppConstantCode.Companion.REQUEST_CODE
import com.gratus.formularendererapp.util.constants.AppConstants
import com.gratus.formularendererapp.util.interceptor.AppInterceptor
import com.gratus.formularendererapp.util.networkManager.NetworkOnlineCheck
import com.gratus.formularendererapp.util.networkManager.NetworkOnlineReceiver
import com.gratus.formularendererapp.util.pref.AppPreferencesHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var prefs: AppPreferencesHelper

    @Inject
    lateinit var mInterceptor: AppInterceptor

    @Inject
    lateinit var networkOnlineCheck: NetworkOnlineCheck
    private var snackbar: Snackbar? = null
    private var initial: Boolean = false
    private val TAG = "Permission"
    fun isNetworkConnected(): Boolean {
        return networkOnlineCheck.isNetworkOnline
    }

    internal fun networkReceiver() {
        val myReceiver = NetworkOnlineReceiver(networkOnlineCheck)
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(myReceiver, filter)
    }

    // network check for internet connection
    internal fun networkCheck(view: View) {
        networkReceiver()
        val intentFilter =
            IntentFilter(AppConstants.NETWORK_AVAILABLE_ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val isNetworkAvailable =
                    intent.getBooleanExtra(AppConstants.IS_NETWORK_AVAILABLE, false)
                if (!isNetworkAvailable) {
                    initial = true
                }
                if (initial) {
                    showSnack(isNetworkAvailable, view)
                    initial = isNetworkAvailable
                }
                initial = true
            }
        }, intentFilter)
    }

    internal fun showSnack(networkConnected: Boolean, parent: View?) {
        if (networkConnected) {
            snackbar = Snackbar.make(parent!!, R.string.network_online, Snackbar.LENGTH_SHORT)
            getSnackBarCustom(snackbar!!.view, true)
        } else {
            snackbar = Snackbar.make(parent!!, R.string.network_offline, Snackbar.LENGTH_INDEFINITE)
            getSnackBarCustom(snackbar!!.view, false)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun getSnackBarCustom(view: View, b: Boolean) {
        val textView =
            view.findViewById<View>(R.id.snackbar_text) as TextView
        if (b) {
            textView.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackbar!!.setBackgroundTint(ContextCompat.getColor(applicationContext, R.color.black))
        } else {
            textView.setTextColor(Color.YELLOW)
        }
        snackbar!!.show()
    }

    override fun onBackPressed() {
    }

    override fun onResume() {
        super.onResume()
    }

    //Permission check
    fun setupPermissions() {
        val permission_1 = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission_2 = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission_1 != PackageManager.PERMISSION_GRANTED &&
            permission_2 != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "Permission to write and read denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {

            REQUEST_CODE ->

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
        }
    }
}