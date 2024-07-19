package com.app.ktorcrud.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.ktorcrud.activities.ui.theme.KtorCRUDTheme
import com.app.ktorcrud.ui.LoadUsers
import com.app.ktorcrud.utils.NetworkConnection
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavigationActivity : ComponentActivity() {

    private var startRoute: String = ""
    private val loginViewModel: LoginViewModel by viewModel()
    private val networkConnection: NetworkConnection by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            KtorCRUDTheme {
                // A surface container using the 'background' color from the theme
                if (true) {
                    startRoute = "1"
                }
                NavigationComponent(navController, networkConnection)
            }
        }
    }

    @Composable
    private fun NavigationComponent(
        navController: NavHostController,
        networkConnectivityManager: NetworkConnection
    ) {
        val isNetworkAvailable by networkConnectivityManager.networkStatus.collectAsState()
        NavHost(navController = navController, startDestination = startRoute) {
            composable("1") {
                LoadUsers(loginViewModel,isNetworkAvailable)
            }
        }
    }
}