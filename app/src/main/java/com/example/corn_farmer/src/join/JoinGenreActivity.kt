package com.example.corn_farmer.src.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.model.getJoinAPI
import com.example.cornfarmer_android.databinding.ActivityJoinGenreBinding

class JoinGenreActivity : AppCompatActivity(), View.OnClickListener, JoinView {

    private lateinit var binding: ActivityJoinGenreBinding

    var genreNum = 0
    var genreList = mutableListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
        val servertoken = sharedPreferences.getString("servertoken", null)
//        val galleryPic = sharedPreferences.getString("gallerypic", null)
        val cameraPic = sharedPreferences.getString("camerapic", null)
        val nickname = sharedPreferences.getString("nickname", null)
        val sex = sharedPreferences.getString("sex", null)
        val birthday = sharedPreferences.getString("birthday", null)
//        val ottList = sharedPreferences.getString("ottlist", null)
//        val genreList = sharedPreferences.getString("genrelist", null)

        Log.d("JOIN-SHARE", servertoken.toString())
        Log.d("JOIN-SHARE", cameraPic.toString())
        Log.d("JOIN-SHARE", nickname.toString())
        Log.d("JOIN-SHARE", sex.toString())
        Log.d("JOIN-SHARE", birthday.toString())

//

//        Toast.makeText(this, cameraPic.toString(), Toast.LENGTH_LONG).show()
//        Toast.makeText(this, nickname.toString(), Toast.LENGTH_LONG).show()
//        Toast.makeText(this, sex.toString(), Toast.LENGTH_LONG).show()
//        Toast.makeText(this, birthday.toString(), Toast.LENGTH_LONG).show()
//        Toast.makeText(this, ottList.toString(), Toast.LENGTH_LONG).show()
//        Toast.makeText(this, genreList.toString(), Toast.LENGTH_LONG).show()

//        val join = sendJoinAPI()
//        var service = JoinService(this, join)
//        service.tryPostJoin()

        binding.genreBackIv.setOnClickListener {
            finish()
        }

        binding.genreFinishColorIv.setOnClickListener {

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("genrelist", genreList.toString())
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
                genreList[0]++
            }
            binding.genreDacuColorIv.id -> {
                binding.genreDacuIv.visibility = View.VISIBLE
                genreNum--
                genreList[0]--
                binding.genreDacuColorIv.visibility = View.GONE
            }
            binding.genreAnimeIv.id -> {
                binding.genreAnimeIv.visibility = View.GONE
                binding.genreAnimeColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[1]++
            }
            binding.genreAnimeColorIv.id -> {
                binding.genreAnimeIv.visibility = View.VISIBLE
                genreNum--
                genreList[1]--
                binding.genreAnimeColorIv.visibility = View.GONE
            }
            binding.genreFantasyIv.id -> {
                binding.genreFantasyIv.visibility = View.GONE
                binding.genreFantasyColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[2]++
            }
            binding.genreFantasyColorIv.id -> {
                binding.genreFantasyIv.visibility = View.VISIBLE
                genreNum--
                genreList[2]--
                binding.genreFantasyColorIv.visibility = View.GONE
            }
            binding.genreThrillIv.id -> {
                binding.genreThrillIv.visibility = View.GONE
                binding.genreThrillColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[3]++
            }
            binding.genreThrillColorIv.id -> {
                binding.genreThrillIv.visibility = View.VISIBLE
                genreNum--
                genreList[3]--
                binding.genreThrillColorIv.visibility = View.GONE
            }
            binding.genreSportIv.id -> {
                binding.genreSportIv.visibility = View.GONE
                binding.genreSportColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[4]++
            }
            binding.genreSportColorIv.id -> {
                binding.genreSportIv.visibility = View.VISIBLE
                genreNum--
                genreList[4]--
                binding.genreSportColorIv.visibility = View.GONE
            }
            binding.genreRomanceIv.id -> {
                binding.genreRomanceIv.visibility = View.GONE
                binding.genreRomanceColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[5]++
            }
            binding.genreRomanceColorIv.id -> {
                binding.genreRomanceIv.visibility = View.VISIBLE
                genreNum--
                genreList[5]--
                binding.genreRomanceColorIv.visibility = View.GONE
            }
            binding.genreDramaIv.id -> {
                binding.genreDramaIv.visibility = View.GONE
                binding.genreDramaColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[6]++
            }
            binding.genreDramaColorIv.id -> {
                binding.genreDramaIv.visibility = View.VISIBLE
                genreNum--
                genreList[6]--
                binding.genreDramaColorIv.visibility = View.GONE
            }
            binding.genreComedyIv.id -> {
                binding.genreComedyIv.visibility = View.GONE
                binding.genreComedyColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[7]++
            }
            binding.genreComedyColorIv.id -> {
                binding.genreComedyIv.visibility = View.VISIBLE
                genreNum--
                genreList[7]--
                binding.genreComedyColorIv.visibility = View.GONE
            }
            binding.genreFamilyIv.id -> {
                binding.genreFamilyIv.visibility = View.GONE
                binding.genreFamilyColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[8]++
            }
            binding.genreFamilyColorIv.id -> {
                binding.genreFamilyIv.visibility = View.VISIBLE
                genreNum--
                genreList[8]--
                binding.genreFamilyColorIv.visibility = View.GONE
            }
            binding.genreMusicIv.id -> {
                binding.genreMusicIv.visibility = View.GONE
                binding.genreMusicColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[9]++
            }
            binding.genreMusicColorIv.id -> {
                binding.genreMusicIv.visibility = View.VISIBLE
                genreNum--
                genreList[9]--
                binding.genreMusicColorIv.visibility = View.GONE
            }
            binding.genreSfIv.id -> {
                binding.genreSfIv.visibility = View.GONE
                binding.genreSfColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[10]++
            }
            binding.genreSfColorIv.id -> {
                binding.genreSfIv.visibility = View.VISIBLE
                genreNum--
                genreList[10]--
                binding.genreSfColorIv.visibility = View.GONE
            }
            binding.genreActionIv.id -> {
                binding.genreActionIv.visibility = View.GONE
                binding.genreActionColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[11]++
            }
            binding.genreActionColorIv.id -> {
                binding.genreActionIv.visibility = View.VISIBLE
                genreNum--
                genreList[11]--
                binding.genreActionColorIv.visibility = View.GONE
            }
            binding.genreHistoryIv.id -> {
                binding.genreHistoryIv.visibility = View.GONE
                binding.genreHistoryColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[12]++
            }
            binding.genreHistoryColorIv.id -> {
                binding.genreHistoryIv.visibility = View.VISIBLE
                genreNum--
                genreList[12]--
                binding.genreHistoryColorIv.visibility = View.GONE
            }
            binding.genreHororIv.id -> {
                binding.genreHororIv.visibility = View.GONE
                binding.genreHororColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[13]++
            }
            binding.genreHororColorIv.id -> {
                binding.genreHororIv.visibility = View.VISIBLE
                genreNum--
                genreList[13]--
                binding.genreHororColorIv.visibility = View.GONE
            }
            binding.genreCrimeIv.id -> {
                binding.genreCrimeIv.visibility = View.GONE
                binding.genreCrimeColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[14]++
            }
            binding.genreCrimeColorIv.id -> {
                binding.genreCrimeIv.visibility = View.VISIBLE
                genreNum--
                genreList[14]--
                binding.genreCrimeColorIv.visibility = View.GONE
            }
            binding.genreWarIv.id -> {
                binding.genreWarIv.visibility = View.GONE
                binding.genreWarColorIv.visibility = View.VISIBLE
                genreNum++
                genreList[15]++
            }
            binding.genreWarColorIv.id -> {
                binding.genreWarIv.visibility = View.VISIBLE
                genreNum--
                genreList[15]--
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

    }

    override fun onPostJoinSuccess(response: getJoinAPI) {
        Log.d("JOIN-API", response.toString())
    }

    override fun onPostJoinFailure(message: String) {
        Log.d("JOIN-API", message.toString())
    }


}