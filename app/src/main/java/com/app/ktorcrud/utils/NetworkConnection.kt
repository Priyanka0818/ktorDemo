package com.app.ktorcrud.utils

import android.content.Context
import android.hardware.usb.UsbManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkConnection(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _networkStatus = MutableStateFlow(false)
    val networkStatus: StateFlow<Boolean> = _networkStatus

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val hasInternet =
                    networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                val isActive = connectivityManager.activeNetwork == network
                _networkStatus.value = hasInternet && isActive
            }

            override fun onLost(network: Network) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val networkCapabilities =
                        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    _networkStatus.value =
                        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true || networkCapabilities?.hasTransport(
                            NetworkCapabilities.TRANSPORT_CELLULAR
                        ) == true
                },500)
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.requestNetwork(networkRequest, callback)
    }

}