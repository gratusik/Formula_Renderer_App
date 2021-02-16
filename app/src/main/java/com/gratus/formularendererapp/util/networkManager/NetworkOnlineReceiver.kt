package com.gratus.formularendererapp.util.networkManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gratus.formularendererapp.util.constants.AppConstants.Companion.IS_NETWORK_AVAILABLE
import com.gratus.formularendererapp.util.constants.AppConstants.Companion.NETWORK_AVAILABLE_ACTION
import javax.inject.Inject

class NetworkOnlineReceiver @Inject constructor(private val networkOnlineCheck: NetworkOnlineCheck) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, networkCheck(context))
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)
    }

    private fun networkCheck(context: Context): Boolean {
        return networkOnlineCheck.isNetworkOnline
    }
}