package com.app.ktorcrud.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.app.ktorcrud.databinding.ActivityUserBinding
import com.app.ktorcrud.response.UpdateUserResponse
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.launch

class UserActivity : BaseActivity() {

    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    var isUpdate: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        binding.loginViewModel = loginViewModel2
/*
        lifecycleScope.launch {
            loginViewModel2.allEventsFlow.collect { event ->
                when (event) {
                    is AllEvents.SuccessBool -> {
                        isUpdate = event.code == 3
                    }
                    is AllEvents.Success<*> -> {
                        if (isUpdate) {
                            val updateUserResponse = event.data as UpdateUserResponse
                            Toast.makeText(
                                this@UserActivity,
                                updateUserResponse.job,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Log.e("TAG", "onCreate: " )
                        }
                    }
                    else -> {
                        val asString = event.asString(this@UserActivity)
                        if (asString !is Unit && asString.toString().isNotBlank()) {
                            Toast.makeText(
                                this@UserActivity,
                                asString.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
*/
    }
}