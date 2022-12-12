package com.app.ktorcrud.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.app.ktorcrud.adapter.DashboardAdapter
import com.app.ktorcrud.databinding.ActivityHomeBinding
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.launch


class HomeActivity : BaseActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loginViewModel.getUsers(1)
        binding.fab.setOnClickListener {
            startActivity(Intent(this@HomeActivity, UserActivity::class.java))
        }
        lifecycleScope.launch {
            loginViewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AllEvents.Success<*> -> {
                        val dashboardAdapter = DashboardAdapter(
                            event.data as ArrayList<Data>,
                            this@HomeActivity
                        )
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