package com.app.ktorcrud.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.ktorcrud.utils.ConnectionLiveData
import com.app.ktorcrud.utils.isConnectedToInternet
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Priyanka.
 */

open class BaseActivity : AppCompatActivity() {

    private var connectionLiveData: ConnectionLiveData? = null
    val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
        loginViewModel.isNetworkAvailable.value = isConnectedToInternet()
    }
}