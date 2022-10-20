package org.cornfarmer.presentation.join

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityJoinGenreBinding
import org.cornfarmer.di.Application

class JoinGenreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityJoinGenreBinding

    var genreNum = 0
    var genreList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_genre)

        binding.ivFinishColor.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("genrelist", genreList.toString())
            editor.apply()
            startActivity(Intent(this, JoinNicknameActivity::class.java))
            finish()
        }
        bindOtt()
    }

    private fun bindOtt() {
        binding.ivDacu.setOnClickListener(this)
        binding.ivDacuColor.setOnClickListener(this)
        binding.ivAnime.setOnClickListener(this)
        binding.ivAnimeColor.setOnClickListener(this)
        binding.ivFantasy.setOnClickListener(this)
        binding.ivFantasyColor.setOnClickListener(this)
        binding.ivThrill.setOnClickListener(this)
        binding.ivThrillColor.setOnClickListener(this)
        binding.ivSport.setOnClickListener(this)
        binding.ivSportColor.setOnClickListener(this)
        binding.ivRomance.setOnClickListener(this)
        binding.ivRomanceColor.setOnClickListener(this)
        binding.ivDrama.setOnClickListener(this)
        binding.ivDramaColor.setOnClickListener(this)
        binding.ivComedy.setOnClickListener(this)
        binding.ivComedyColor.setOnClickListener(this)
        binding.ivFamily.setOnClickListener(this)
        binding.ivFamilyColor.setOnClickListener(this)
        binding.ivMusic.setOnClickListener(this)
        binding.ivMusicColor.setOnClickListener(this)
        binding.ivSf.setOnClickListener(this)
        binding.ivSfColor.setOnClickListener(this)
        binding.ivAction.setOnClickListener(this)
        binding.ivActionColor.setOnClickListener(this)
        binding.ivHistory.setOnClickListener(this)
        binding.ivHistoryColor.setOnClickListener(this)
        binding.ivHoror.setOnClickListener(this)
        binding.ivHororColor.setOnClickListener(this)
        binding.ivCrime.setOnClickListener(this)
        binding.ivCrimeColor.setOnClickListener(this)
        binding.ivWar.setOnClickListener(this)
        binding.ivWarColor.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.ivDacu.id -> {
                binding.ivDacu.visibility = View.GONE
                binding.ivDacuColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("5")
            }
            binding.ivDacuColor.id -> {
                binding.ivDacu.visibility = View.VISIBLE
                genreNum--
                genreList.remove("5")
                binding.ivDacuColor.visibility = View.GONE
            }
            binding.ivAnime.id -> {
                binding.ivAnime.visibility = View.GONE
                binding.ivAnimeColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("2")
            }
            binding.ivAnimeColor.id -> {
                binding.ivAnime.visibility = View.VISIBLE
                genreNum--
                genreList.remove("2")
                binding.ivAnimeColor.visibility = View.GONE
            }
            binding.ivFantasy.id -> {
                binding.ivFantasy.visibility = View.GONE
                binding.ivFantasyColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("7")
            }
            binding.ivFantasyColor.id -> {
                binding.ivFantasy.visibility = View.VISIBLE
                genreNum--
                genreList.remove("7")
                binding.ivFantasyColor.visibility = View.GONE
            }
            binding.ivThrill.id -> {
                binding.ivThrill.visibility = View.GONE
                binding.ivThrillColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("12")
            }
            binding.ivThrillColor.id -> {
                binding.ivThrill.visibility = View.VISIBLE
                genreNum--
                genreList.remove("12")
                binding.ivThrillColor.visibility = View.GONE
            }
            binding.ivSport.id -> {
                binding.ivSport.visibility = View.GONE
                binding.ivSportColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("15")
            }
            binding.ivSportColor.id -> {
                binding.ivSport.visibility = View.VISIBLE
                genreNum--
                genreList.remove("15")
                binding.ivSportColor.visibility = View.GONE
            }
            binding.ivRomance.id -> {
                binding.ivRomance.visibility = View.GONE
                binding.ivRomanceColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("13")
            }
            binding.ivRomanceColor.id -> {
                binding.ivRomance.visibility = View.VISIBLE
                genreNum--
                genreList.remove("13")
                binding.ivRomanceColor.visibility = View.GONE
            }
            binding.ivDrama.id -> {
                binding.ivDrama.visibility = View.GONE
                binding.ivDramaColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("6")
            }
            binding.ivDramaColor.id -> {
                binding.ivDrama.visibility = View.VISIBLE
                genreNum--
                genreList.remove("6")
                binding.ivDramaColor.visibility = View.GONE
            }
            binding.ivComedy.id -> {
                binding.ivComedy.visibility = View.GONE
                binding.ivComedyColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("3")
            }
            binding.ivComedyColor.id -> {
                binding.ivComedy.visibility = View.VISIBLE
                genreNum--
                genreList.remove("3")
                binding.ivComedyColor.visibility = View.GONE
            }
            binding.ivFamily.id -> {
                binding.ivFamily.visibility = View.GONE
                binding.ivFamilyColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("10")
            }
            binding.ivFamilyColor.id -> {
                binding.ivFamily.visibility = View.VISIBLE
                genreNum--
                genreList.remove("10")
                binding.ivFamilyColor.visibility = View.GONE
            }
            binding.ivMusic.id -> {
                binding.ivMusic.visibility = View.GONE
                binding.ivMusicColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("11")
            }
            binding.ivMusicColor.id -> {
                binding.ivMusic.visibility = View.VISIBLE
                genreNum--
                genreList.remove("11")
                binding.ivMusicColor.visibility = View.GONE
            }
            binding.ivSf.id -> {
                binding.ivSf.visibility = View.GONE
                binding.ivSfColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("14")
            }
            binding.ivSfColor.id -> {
                binding.ivSf.visibility = View.VISIBLE
                genreNum--
                genreList.remove("14")
                binding.ivSfColor.visibility = View.GONE
            }
            binding.ivAction.id -> {
                binding.ivAction.visibility = View.GONE
                binding.ivActionColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("1")
            }
            binding.ivActionColor.id -> {
                binding.ivAction.visibility = View.VISIBLE
                genreNum--
                genreList.remove("1")
                binding.ivActionColor.visibility = View.GONE
            }
            binding.ivHistory.id -> {
                binding.ivHistory.visibility = View.GONE
                binding.ivHistoryColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("8")
            }
            binding.ivHistoryColor.id -> {
                binding.ivHistory.visibility = View.VISIBLE
                genreNum--
                genreList.remove("8")
                binding.ivHistoryColor.visibility = View.GONE
            }
            binding.ivHoror.id -> {
                binding.ivHoror.visibility = View.GONE
                binding.ivHororColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("9")
            }
            binding.ivHororColor.id -> {
                binding.ivHoror.visibility = View.VISIBLE
                genreNum--
                genreList.remove("9")
                binding.ivHororColor.visibility = View.GONE
            }
            binding.ivCrime.id -> {
                binding.ivCrime.visibility = View.GONE
                binding.ivCrimeColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("4")
            }
            binding.ivCrimeColor.id -> {
                binding.ivCrime.visibility = View.VISIBLE
                genreNum--
                genreList.remove("4")
                binding.ivCrimeColor.visibility = View.GONE
            }
            binding.ivWar.id -> {
                binding.ivWar.visibility = View.GONE
                binding.ivWarColor.visibility = View.VISIBLE
                genreNum++
                genreList.add("16")
            }
            binding.ivWarColor.id -> {
                binding.ivWar.visibility = View.VISIBLE
                genreNum--
                genreList.remove("16")
                binding.ivWarColor.visibility = View.GONE
            }
        }
        binding.ivFinish.visibility = View.GONE

        if (genreNum == 0) {
            binding.ivFinishColor.visibility = View.GONE
            binding.ivFinish.visibility = View.VISIBLE
        } else {
            binding.ivFinishColor.visibility = View.VISIBLE
            binding.ivFinish.visibility = View.GONE
        }

        genreList.sort()
    }
}
