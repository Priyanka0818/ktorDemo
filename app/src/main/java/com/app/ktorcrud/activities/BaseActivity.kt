package com.app.ktorcrud.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.ktorcrud.utils.ConnectionLiveData
import com.app.ktorcrud.utils.SharedPreferenceUtils
import com.app.ktorcrud.utils.isConnectedToInternet
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Priyanka.
 */

open class BaseActivity : AppCompatActivity() {

    private var connectionLiveData: ConnectionLiveData? = null

    val loginViewModel: LoginViewModel by viewModel()

    val sharedPreferenceUtils: SharedPreferenceUtils by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.isNetworkAvailable.value = isConnectedToInternet()
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData?.observe(this
        ) { connection ->
            if (connection?.getIsConnected()!!) {
                loginViewModel.isNetworkAvailable.value = true
            } else {
                loginViewModel.isNetworkAvailable.value = false
                Toast.makeText(
                    this@BaseActivity,
                    String.format("Connection turned OFF"),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }


    override fun onResume() {
        super.onResume()
    }
}