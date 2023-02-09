package com.app.ktorcrud.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.app.ktorcrud.adapter.DashboardAdapter
import com.app.ktorcrud.databinding.ActivityHomeBinding
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.launch


class HomeActivity : BaseActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    var show = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loginViewModel.getUsers()
        binding.fab.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        lifecycleScope.launch {
//            loginViewModel.getUsers()
            loginViewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AllEvents.Success<*> -> {
                        val dashboardAdapter = DashboardAdapter(
                            event.data as ArrayList<Data>,
                            this@HomeActivity
                        )

                        /*
                        val dashboardAdapter = DashboardAdapterPagination(this@HomeActivity)
                        dashboardAdapter.submitData(lifecycle, event.data as PagingData<Data>)*/
                        binding.rvSpends.adapter = dashboardAdapter
                    }
                    else -> {
                        val asString = event.asString(this@HomeActivity)
                        if (asString !is Unit && asString.toString().isNotBlank()) {
                            Toast.makeText(
                                this@HomeActivity,
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