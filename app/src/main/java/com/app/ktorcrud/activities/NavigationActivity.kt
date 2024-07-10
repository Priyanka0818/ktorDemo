package com.app.ktorcrud.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.ktorcrud.activities.ui.theme.KtorCRUDTheme
import com.app.ktorcrud.ui.loadUsers
import com.app.ktorcrud.utils.isConnectedToInternet
import com.app.ktorcrud.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NavigationActivity : ComponentActivity() {

    var startRoute: String = ""
    val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.isNetworkAvailable.value = isConnectedToInternet()
        setContent {
            val navController = rememberNavController()
            KtorCRUDTheme {
                // A surface container using the 'background' color from the theme
                if (true) {
                    startRoute = "1"
                }
                NavigationComponent(navController)
            }
        }
    }

    @Composable
    private fun NavigationComponent(navController: NavHostController) {
        NavHost(navController = navController, startDestination = startRoute) {
            composable("1") {
                loadUsers(loginViewModel)
            }
        }
    }
}