package com.app.ktorcrud.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.app.ktorcrud.databinding.ActivityLoginBinding
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.utils.Utils
import kotlinx.coroutines.launch


class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginViewModel = loginViewModel
        lifecycleScope.launch {
            loginViewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AllEvents.SuccessBool -> {
                        when (event.code) {
                            1 -> {
                                sharedPreferenceUtils.preferencePutBoolean(
                                    Utils.PreferenceKey.isLoggedIn,
                                    true
                                )
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            }
                        }
                    }
                    else -> {
                        val asString = event.asString(this@LoginActivity)
                        if (asString !is Unit && asString.toString().isNotBlank()) {
                            Toast.makeText(
                                this@LoginActivity,
                                asString.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}