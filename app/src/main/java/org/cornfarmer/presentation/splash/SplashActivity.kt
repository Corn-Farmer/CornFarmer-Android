package org.cornfarmer.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.cornfarmer.R
import org.cornfarmer.databinding.ActivitySplashBinding
import org.cornfarmer.presentation.login.LoginActivity
import org.cornfarmer.util.binding.BindingActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }
    }
}
