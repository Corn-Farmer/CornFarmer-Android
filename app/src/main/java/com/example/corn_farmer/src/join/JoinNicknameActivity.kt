package com.example.corn_farmer.src.join

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.model.getJoinAPI
import com.example.cornfarmer_android.databinding.ActivityJoinNicknameBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class JoinNicknameActivity : AppCompatActivity(), JoinView {

    private lateinit var binding: ActivityJoinNicknameBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nicknameFinishColorIv.setOnClickListener {
            signUp()

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val servertoken = sharedPreferences.getString("servertoken", "")
            val photo = sharedPreferences.getString("photo", null)
            val nickname = sharedPreferences.getString("nickname", null)
            var sex = sharedPreferences.getString("isMale", null)
            val birthday = sharedPreferences.getString("birthday", null)
            val ottList = sharedPreferences.getString("ottlist", null)
            val photoName = sharedPreferences.getString("photoname", null)
            val genreList = sharedPreferences.getString("genrelist", null)

            val nicknameRequest = RequestBody.create(MediaType.parse("text/plain"), nickname!!)
            val sexRequest = RequestBody.create(MediaType.parse("text/plain"), sex)
            val birthdayRequest = RequestBody.create(MediaType.parse("text/plain"), birthday!!)
            val ottListRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    ottList!!.replace("[", "").replace("]", "")
                )
            val genreRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    genreList!!.replace("[", "").replace("]", "")
                )

            val requestMap: HashMap<String, RequestBody> = HashMap()

            requestMap.put("nickname", nicknameRequest)
            requestMap.put("is_male", sexRequest)
            requestMap.put("birth", birthdayRequest)
            requestMap.put("ottList", ottListRequest)
            requestMap.put("genreList", genreRequest)

            Log.d("JOIN-token", servertoken.toString())
            Log.d("JOIN-nickname", nickname.toString())
            Log.d("JOIN-sex", sex.toString())
            Log.d("JOIN-birthday", birthday.toString())
            Log.d("JOIN-ottlist", ottList.toString().replace("[", "").replace("]", ""))
            Log.d("JOIN-genrelist", genreList.toString().replace("[", "").replace("]", ""))
            Log.d("JOIN-photoname", photoName.toString())


            if(photo == null){
                val requestFile = RequestBody.create(MediaType.parse("image/png"), "noimage")
                val body = MultipartBody.Part.createFormData("photo", "noimage", requestFile)
                var service = JoinService(this, servertoken.toString(), body, requestMap)
                service.tryPostJoin()
            }else{
                val file = File(photo.toString())
                val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
                val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                var service = JoinService(this, servertoken.toString(), body, requestMap)
                service.tryPostJoin()
            }


        }

        binding.nicknameNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayMonthEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayDayEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


    }

    private fun joinCheck() {
        if (binding.nicknameNicknameEt.length() < 3
            || binding.loginBirthdayEt.length() < 4
            || binding.loginBirthdayMonthEt.length() < 2
            || binding.loginBirthdayDayEt.length() < 2
            || binding.loginBirthdayEt.text.toString().toInt() > 2100
            || binding.loginBirthdayMonthEt.text.toString().toInt() > 12
            || binding.loginBirthdayDayEt.text.toString().toInt() > 31
        ) {
            binding.nicknameFinishIv.visibility = View.VISIBLE
            binding.nicknameFinishColorIv.visibility = View.GONE
        } else if (binding.nicknameNicknameEt.length() > 2 && binding.loginBirthdayEt.length() > 3 && binding.loginBirthdayMonthEt.length() > 1
            && binding.loginBirthdayDayEt.length() > 1
        ) {
            binding.nicknameFinishIv.visibility = View.GONE
            binding.nicknameFinishColorIv.visibility = View.VISIBLE
        }
    }

    private fun signUp() {

        var isMale: String

//        if (binding.loginBirthdayEt.text.toString().isEmpty()
//            || binding.loginBirthdayMonthEt.text.toString().isEmpty()
//            || binding.loginBirthdayDayEt.text.toString().isEmpty()){
//            Toast.makeText(this, "생녈월일 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        if (binding.nicknameNicknameEt.text.toString().isEmpty()){
//            Toast.makeText(this, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show()
//            return
//        }

        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (binding.nicknameMaleRb.isChecked) {
            isMale = "true"
            editor.putString("isMale", isMale)
        } else if (binding.nicknameFemaleRb.isChecked) {
            isMale = "false"
            editor.putString("isMale", isMale)
        }

        editor.putString("nickname", binding.nicknameNicknameEt.text.toString())
        editor.putString(
            "birthday", binding.loginBirthdayEt.text.toString() + "-" +
                    binding.loginBirthdayMonthEt.text.toString() + "-" +
                    binding.loginBirthdayDayEt.text.toString()
        )
        editor.commit()
    }

    override fun onPostJoinSuccess(response: getJoinAPI) {
        Log.d("JOIN-API", response.toString())

        if (response.code == 3015) {
            binding.nicknameUsingNicknameIv.visibility = View.VISIBLE
        } else {
            val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("useridx", response.result!!.userIdx)
            editor.commit()

            val intent = Intent(this, SplashJoinActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onPostJoinFailure(message: String) {
    }


}