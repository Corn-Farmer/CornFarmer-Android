package com.example.corn_farmer.src.join

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.model.getJoinAPI
import com.example.cornfarmer_android.databinding.ActivityJoinGenreBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.util.*
import kotlin.collections.HashMap

class JoinGenreActivity : AppCompatActivity(), View.OnClickListener, JoinView {

    private lateinit var binding: ActivityJoinGenreBinding

    var genreNum = 0
    var genreList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.genreBackIv.setOnClickListener {
            finish()
        }

        binding.genreFinishColorIv.setOnClickListener {

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val servertoken = sharedPreferences.getString("servertoken", null)
            val photo = sharedPreferences.getString("photo", null)
            val nickname = sharedPreferences.getString("nickname", null)
            var sex = sharedPreferences.getString("isMale",null)
            val birthday = sharedPreferences.getString("birthday", null)
            val ottList = sharedPreferences.getString("ottlist", null)
            val photoName = sharedPreferences.getString("photoname", null)




            val nicknameRequest = RequestBody.create(MediaType.parse("text/plain"), nickname!!)
            val sexRequest = RequestBody.create(MediaType.parse("text/plain"), sex)
            val birthdayRequest = RequestBody.create(MediaType.parse("text/plain"), birthday!!)
            val ottListRequest =
                RequestBody.create(MediaType.parse("text/plain"), ottList!!.replace("[","").replace("]",""))
            val genreRequest =
                RequestBody.create(MediaType.parse("text/plain"), genreList.toString().replace("[","").replace("]",""))

            val fileBody: RequestBody =
                RequestBody.create(MediaType.parse("image/png"), photo!!);
            val filePart: MultipartBody.Part =
                MultipartBody.Part.createFormData("photo", photoName!!, fileBody)

            val requestMap: HashMap<String, RequestBody> = HashMap()

            requestMap.put("nickname", nicknameRequest)
            requestMap.put("is_male", sexRequest)
            requestMap.put("birth", birthdayRequest)
            requestMap.put("ottList", ottListRequest)
            requestMap.put("genreList", genreRequest)

            Log.d("JOIN-token", servertoken.toString())
            Log.d("JOIN-photo", filePart.toString())
            Log.d("JOIN-nickname", nickname.toString())
            Log.d("JOIN-sex", sex.toString())
            Log.d("JOIN-birthday", birthday.toString())
            Log.d("JOIN-ottlist", ottList.toString().replace("[","").replace("]",""))
            Log.d("JOIN-genrelist", genreList.toString().replace("[","").replace("]",""))
            Log.d("JOIN-photoname", photoName.toString())

            val editor = sharedPreferences.edit()
            editor.putString("genrelist",genreList.toString())
            editor.commit()


            var service = JoinService(this, servertoken.toString(), filePart, requestMap)
            service.tryPostJoin()

//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)


        }

        bindOtt()

    }

    private fun bindOtt() {
        binding.genreDacuIv.setOnClickListener(this)
        binding.genreDacuColorIv.setOnClickListener(this)
        binding.genreAnimeIv.setOnClickListener(this)
        binding.genreAnimeColorIv.setOnClickListener(this)
        binding.genreFantasyIv.setOnClickListener(this)
        binding.genreFantasyColorIv.setOnClickListener(this)
        binding.genreThrillIv.setOnClickListener(this)
        binding.genreThrillColorIv.setOnClickListener(this)
        binding.genreSportIv.setOnClickListener(this)
        binding.genreSportColorIv.setOnClickListener(this)
        binding.genreRomanceIv.setOnClickListener(this)
        binding.genreRomanceColorIv.setOnClickListener(this)
        binding.genreDramaIv.setOnClickListener(this)
        binding.genreDramaColorIv.setOnClickListener(this)
        binding.genreComedyIv.setOnClickListener(this)
        binding.genreComedyColorIv.setOnClickListener(this)
        binding.genreFamilyIv.setOnClickListener(this)
        binding.genreFamilyColorIv.setOnClickListener(this)
        binding.genreMusicIv.setOnClickListener(this)
        binding.genreMusicColorIv.setOnClickListener(this)
        binding.genreSfIv.setOnClickListener(this)
        binding.genreSfColorIv.setOnClickListener(this)
        binding.genreActionIv.setOnClickListener(this)
        binding.genreActionColorIv.setOnClickListener(this)
        binding.genreHistoryIv.setOnClickListener(this)
        binding.genreHistoryColorIv.setOnClickListener(this)
        binding.genreHororIv.setOnClickListener(this)
        binding.genreHororColorIv.setOnClickListener(this)
        binding.genreCrimeIv.setOnClickListener(this)
        binding.genreCrimeColorIv.setOnClickListener(this)
        binding.genreWarIv.setOnClickListener(this)
        binding.genreWarColorIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.genreDacuIv.id -> {
                binding.genreDacuIv.visibility = View.GONE
                binding.genreDacuColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("5")
            }
            binding.genreDacuColorIv.id -> {
                binding.genreDacuIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("5")
                binding.genreDacuColorIv.visibility = View.GONE
            }
            binding.genreAnimeIv.id -> {
                binding.genreAnimeIv.visibility = View.GONE
                binding.genreAnimeColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("2")
            }
            binding.genreAnimeColorIv.id -> {
                binding.genreAnimeIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("2")
                binding.genreAnimeColorIv.visibility = View.GONE
            }
            binding.genreFantasyIv.id -> {
                binding.genreFantasyIv.visibility = View.GONE
                binding.genreFantasyColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("7")
            }
            binding.genreFantasyColorIv.id -> {
                binding.genreFantasyIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("7")
                binding.genreFantasyColorIv.visibility = View.GONE
            }
            binding.genreThrillIv.id -> {
                binding.genreThrillIv.visibility = View.GONE
                binding.genreThrillColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("12")
            }
            binding.genreThrillColorIv.id -> {
                binding.genreThrillIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("12")
                binding.genreThrillColorIv.visibility = View.GONE
            }
            binding.genreSportIv.id -> {
                binding.genreSportIv.visibility = View.GONE
                binding.genreSportColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("15")
            }
            binding.genreSportColorIv.id -> {
                binding.genreSportIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("15")
                binding.genreSportColorIv.visibility = View.GONE
            }
            binding.genreRomanceIv.id -> {
                binding.genreRomanceIv.visibility = View.GONE
                binding.genreRomanceColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("13")
            }
            binding.genreRomanceColorIv.id -> {
                binding.genreRomanceIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("13")
                binding.genreRomanceColorIv.visibility = View.GONE
            }
            binding.genreDramaIv.id -> {
                binding.genreDramaIv.visibility = View.GONE
                binding.genreDramaColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("6")
            }
            binding.genreDramaColorIv.id -> {
                binding.genreDramaIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("6")
                binding.genreDramaColorIv.visibility = View.GONE
            }
            binding.genreComedyIv.id -> {
                binding.genreComedyIv.visibility = View.GONE
                binding.genreComedyColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("3")
            }
            binding.genreComedyColorIv.id -> {
                binding.genreComedyIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("3")
                binding.genreComedyColorIv.visibility = View.GONE
            }
            binding.genreFamilyIv.id -> {
                binding.genreFamilyIv.visibility = View.GONE
                binding.genreFamilyColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("10")
            }
            binding.genreFamilyColorIv.id -> {
                binding.genreFamilyIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("10")
                binding.genreFamilyColorIv.visibility = View.GONE
            }
            binding.genreMusicIv.id -> {
                binding.genreMusicIv.visibility = View.GONE
                binding.genreMusicColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("11")
            }
            binding.genreMusicColorIv.id -> {
                binding.genreMusicIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("11")
                binding.genreMusicColorIv.visibility = View.GONE
            }
            binding.genreSfIv.id -> {
                binding.genreSfIv.visibility = View.GONE
                binding.genreSfColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("14")
            }
            binding.genreSfColorIv.id -> {
                binding.genreSfIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("14")
                binding.genreSfColorIv.visibility = View.GONE
            }
            binding.genreActionIv.id -> {
                binding.genreActionIv.visibility = View.GONE
                binding.genreActionColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("1")
            }
            binding.genreActionColorIv.id -> {
                binding.genreActionIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("1")
                binding.genreActionColorIv.visibility = View.GONE
            }
            binding.genreHistoryIv.id -> {
                binding.genreHistoryIv.visibility = View.GONE
                binding.genreHistoryColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("8")
            }
            binding.genreHistoryColorIv.id -> {
                binding.genreHistoryIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("8")
                binding.genreHistoryColorIv.visibility = View.GONE
            }
            binding.genreHororIv.id -> {
                binding.genreHororIv.visibility = View.GONE
                binding.genreHororColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("9")
            }
            binding.genreHororColorIv.id -> {
                binding.genreHororIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("9")
                binding.genreHororColorIv.visibility = View.GONE
            }
            binding.genreCrimeIv.id -> {
                binding.genreCrimeIv.visibility = View.GONE
                binding.genreCrimeColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("4")
            }
            binding.genreCrimeColorIv.id -> {
                binding.genreCrimeIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("4")
                binding.genreCrimeColorIv.visibility = View.GONE
            }
            binding.genreWarIv.id -> {
                binding.genreWarIv.visibility = View.GONE
                binding.genreWarColorIv.visibility = View.VISIBLE
                genreNum++
                genreList.add("16")
            }
            binding.genreWarColorIv.id -> {
                binding.genreWarIv.visibility = View.VISIBLE
                genreNum--
                genreList.remove("16")
                binding.genreWarColorIv.visibility = View.GONE
            }

        }
        binding.genreFinishIv.visibility = View.GONE

        if (genreNum == 0) {
            binding.genreFinishColorIv.visibility = View.GONE
            binding.genreFinishIv.visibility = View.VISIBLE
        } else {
            binding.genreFinishColorIv.visibility = View.VISIBLE
            binding.genreFinishIv.visibility = View.GONE
        }

        genreList.sort()

    }

    override fun onPostJoinSuccess(response: getJoinAPI) {
        Log.d("JOIN-API", response.toString())

        val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("useridx", response.result!!.userIdx)
        editor.commit()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onPostJoinFailure(message: String) {
        Log.d("JOIN-API", message.toString())
    }

}