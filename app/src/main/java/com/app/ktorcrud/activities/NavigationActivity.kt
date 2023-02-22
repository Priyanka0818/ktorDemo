package com.app.ktorcrud.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
            composable("2") {
                Greeting2()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtorCRUDTheme {
        Greeting("Android")
    }
}

@Composable
fun Greeting2() {
    Text(text = "Hello Greeting2")
}