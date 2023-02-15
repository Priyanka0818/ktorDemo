package com.app.ktorcrud.activities

import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.ktorcrud.utils.Utils

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        if (sharedPreferenceUtils.preferenceGetBoolean(Utils.PreferenceKey.isLoggedIn, false)) {
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        finish()
    }
}