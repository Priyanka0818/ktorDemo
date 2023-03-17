package com.app.ktorcrud.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
import com.app.ktorcrud.adapter.DashboardAdapterPagination
import com.app.ktorcrud.adapter.DashboardListAdapter
import com.app.ktorcrud.databinding.ActivityHomeBinding
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.launch


class HomeActivity : BaseActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        loginViewModel.getUsers(1)
        binding.fab.setOnClickListener {
            startActivity(Intent(this@HomeActivity, UserActivity::class.java))
        }

        lifecycleScope.launch {
            loginViewModel.getUsers()
            loginViewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AllEvents.Success<*> -> {
                        val dashboardAdapter = DashboardListAdapter()
                        /*(event.data as PagingData<Data>).map {
                            Log.e("Logger", "onCreate: ${it.id!!}")
                        }
                        val dashboardAdapter = DashboardAdapterPagination(this@HomeActivity)*/
                        dashboardAdapter.submitList(((event.data as UsersListResponse).data as MutableList<Data>))
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