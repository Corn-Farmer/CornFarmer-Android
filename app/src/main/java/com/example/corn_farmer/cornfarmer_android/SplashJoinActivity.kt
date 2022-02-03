package com.example.cornfarmer_android

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cornfarmer_android.databinding.ActivitySplashJoinBinding
import android.graphics.BitmapFactory
import android.util.Base64
import java.lang.Exception


class SplashJoinActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
        val nickname = sharedPreferences.getString("nickname", null)
        binding.splashJoinNicknameTv.text = nickname.toString()

        val image = sharedPreferences.getString("camerapic", null)
        binding.splashJoinImageIv.setImageBitmap(stringToBitmap(image.toString()))





//        Handler().postDelayed({
//
//
//
//            val intent = Intent(this, JoinOttActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }, 3000)
    }

    fun stringToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }


}