package org.cornfarmer.presentation.join

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.cornfarmer.R
import org.cornfarmer.databinding.ActivityJoinOttBinding
import org.cornfarmer.di.Application

class JoinOttActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityJoinOttBinding

    var ottNum = 0
    var ottList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_ott)

        binding.ivJump.setOnClickListener {
            showDialog()
        }

        binding.ivFinishColor.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("ottlist", ottList.toString())
            editor.apply()

            val intent = Intent(this, JoinGenreActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindOtt()
    }

    private fun bindOtt() {
        binding.ivNone.setOnClickListener(this)
        binding.ivNoneColor.setOnClickListener(this)
        binding.ivNetflix.setOnClickListener(this)
        binding.ivNetflixColor.setOnClickListener(this)
        binding.ivWhatcha.setOnClickListener(this)
        binding.ivWhatchaColor.setOnClickListener(this)
        binding.ivDisney.setOnClickListener(this)
        binding.ivDisneyColor.setOnClickListener(this)
        binding.ivWavve.setOnClickListener(this)
        binding.ivWavveColor.setOnClickListener(this)
        binding.ivTving.setOnClickListener(this)
        binding.ivTvingColor.setOnClickListener(this)
        binding.ivCoupang.setOnClickListener(this)
        binding.ivCoupangColor.setOnClickListener(this)
        binding.ivPrimeVideo.setOnClickListener(this)
        binding.ivPrimeVideoColor.setOnClickListener(this)
        binding.ivApple.setOnClickListener(this)
        binding.ivAppleColor.setOnClickListener(this)
    }

    private fun showDialog() {
        val dialog = OttSelectCustomDialog(this)
        dialog.show()

        dialog.window?.setGravity(Gravity.CENTER_VERTICAL)

        dialog.findViewById<Button>(R.id.bt_back)?.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("ottlist", ottList.toString())
            editor.apply()
            val intent = Intent(this, JoinGenreActivity::class.java)
            startActivity(intent)
        }

        dialog.findViewById<Button>(R.id.bt_yes)?.setOnClickListener {
            dialog.dismiss()
            binding.ivJump.visibility = View.GONE
            binding.ivFinish.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.ivNone.id -> {
                binding.ivNone.visibility = View.GONE
                binding.ivNoneColor.visibility = View.VISIBLE
                binding.ivNetflix.visibility = View.VISIBLE
                binding.ivNetflixColor.visibility = View.GONE
                binding.ivWhatcha.visibility = View.VISIBLE
                binding.ivWhatchaColor.visibility = View.GONE
                binding.ivDisney.visibility = View.VISIBLE
                binding.ivDisneyColor.visibility = View.GONE
                binding.ivWavve.visibility = View.VISIBLE
                binding.ivWavveColor.visibility = View.GONE
                binding.ivTving.visibility = View.VISIBLE
                binding.ivTvingColor.visibility = View.GONE
                binding.ivCoupang.visibility = View.VISIBLE
                binding.ivCoupangColor.visibility = View.GONE
                binding.ivPrimeVideo.visibility = View.VISIBLE
                binding.ivPrimeVideoColor.visibility = View.GONE
                binding.ivApple.visibility = View.VISIBLE
                binding.ivAppleColor.visibility = View.GONE
                ottList.clear()
                ottNum++
            }
            binding.ivNoneColor.id -> {
                binding.ivNone.visibility = View.VISIBLE
                ottNum = 0
                binding.ivNoneColor.visibility = View.GONE
            }
            binding.ivNetflix.id -> {
                binding.ivNetflix.visibility = View.GONE
                binding.ivNetflixColor.visibility = View.VISIBLE
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("7")
            }
            binding.ivNetflixColor.id -> {
                ottNum--
                ottList.remove("7")
                binding.ivNetflix.visibility = View.VISIBLE
                binding.ivNetflixColor.visibility = View.GONE
            }
            binding.ivWhatcha.id -> {
                binding.ivWhatcha.visibility = View.GONE
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("8")
                binding.ivWhatchaColor.visibility = View.VISIBLE
            }
            binding.ivWhatchaColor.id -> {
                ottNum--
                ottList.remove("8")
                binding.ivWhatcha.visibility = View.VISIBLE
                binding.ivWhatchaColor.visibility = View.GONE
            }
            binding.ivDisney.id -> {
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("3")
                binding.ivDisney.visibility = View.GONE
                binding.ivDisneyColor.visibility = View.VISIBLE
            }
            binding.ivDisneyColor.id -> {
                ottNum--
                ottList.remove("3")
                binding.ivDisney.visibility = View.VISIBLE
                binding.ivDisneyColor.visibility = View.GONE
            }
            binding.ivWavve.id -> {
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("5")
                binding.ivWavve.visibility = View.GONE
                binding.ivWavveColor.visibility = View.VISIBLE
            }
            binding.ivWavveColor.id -> {
                ottNum--
                ottList.remove("5")
                binding.ivWavve.visibility = View.VISIBLE
                binding.ivWavveColor.visibility = View.GONE
            }
            binding.ivTving.id -> {
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("6")
                binding.ivTving.visibility = View.GONE
                binding.ivTvingColor.visibility = View.VISIBLE
            }
            binding.ivTvingColor.id -> {
                ottNum--
                ottList.remove("6")
                binding.ivTving.visibility = View.VISIBLE
                binding.ivTvingColor.visibility = View.GONE
            }
            binding.ivCoupang.id -> {
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("4")
                binding.ivCoupang.visibility = View.GONE
                binding.ivCoupangColor.visibility = View.VISIBLE
            }
            binding.ivCoupangColor.id -> {
                ottNum--
                ottList.remove("4")
                binding.ivCoupang.visibility = View.VISIBLE
                binding.ivCoupangColor.visibility = View.GONE
            }
            binding.ivPrimeVideo.id -> {
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("2")
                binding.ivPrimeVideo.visibility = View.GONE
                binding.ivPrimeVideoColor.visibility = View.VISIBLE
            }
            binding.ivPrimeVideoColor.id -> {
                ottNum--
                ottList.remove("2")
                binding.ivPrimeVideo.visibility = View.VISIBLE
                binding.ivPrimeVideoColor.visibility = View.GONE
            }
            binding.ivApple.id -> {
                binding.ivNone.visibility = View.VISIBLE
                binding.ivNoneColor.visibility = View.GONE
                ottNum++
                ottList.add("1")
                binding.ivApple.visibility = View.GONE
                binding.ivAppleColor.visibility = View.VISIBLE
            }
            binding.ivAppleColor.id -> {
                ottNum--
                ottList.remove("1")
                binding.ivApple.visibility = View.VISIBLE
                binding.ivAppleColor.visibility = View.GONE
            }
        }

        binding.ivJump.visibility = View.GONE

        if (ottNum == 0) {
            binding.ivFinishColor.visibility = View.GONE
            binding.ivFinish.visibility = View.VISIBLE
        } else {
            binding.ivFinishColor.visibility = View.VISIBLE
            binding.ivFinish.visibility = View.GONE
        }

        ottList.sort()
    }
}
