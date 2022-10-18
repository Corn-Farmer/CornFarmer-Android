package org.cornfarmer.persentation.join

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivitySplashJoinBinding
import org.cornfarmer.di.Application
import org.cornfarmer.persentation.main.MainActivity

class SplashJoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_join)

        val sharedPreferences = Application.joinSharedPreferences
        val nickname = sharedPreferences.getString("nickname", null)
        val photo = sharedPreferences.getString("photo", null)
        binding.splashJoinNicknameTv.text = nickname.toString()

        if (photo == null) {
            binding.splashJoinImageIv.setImageResource(R.drawable.cornfarmerprofile)
        } else {
            binding.splashJoinImageIv.setImageURI(Uri.parse(photo))
        }

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
