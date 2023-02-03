package com.app.ktorcrud.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.ktorcrud.R
import com.app.ktorcrud.utils.Utils
import io.ktor.utils.io.concurrent.*

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        if(sharedPreferenceUtils.preferenceGetBoolean(Utils.PreferenceKey.isLoggedIn,false)){
            startActivity(Intent(this,HomeActivity::class.java))
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}