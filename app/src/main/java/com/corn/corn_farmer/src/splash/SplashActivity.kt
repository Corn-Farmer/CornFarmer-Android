package com.corn.corn_farmer.src.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.corn.corn_farmer.src.kakao.LoginActivity
import com.corn.corn_farmer.util.binding.BindingActivity
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
