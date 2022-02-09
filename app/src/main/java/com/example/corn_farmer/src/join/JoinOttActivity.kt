package com.example.corn_farmer.src.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.kakao.OttSelectCustomDialog
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityJoinOttBinding


class JoinOttActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityJoinOttBinding
    var ottNum = 0
    var ottList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinOttBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ottJumpIv.setOnClickListener {
            showDialog()
        }

        binding.ottFinishColorIv.setOnClickListener {

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("ottlist", ottList.toString())
            editor.commit()

            val intent = Intent(this, JoinGenreActivity::class.java)
            startActivity(intent)
        }

        binding.ottBackIv.setOnClickListener {
            finish()
        }

        bindOtt()

    }

    private fun bindOtt() {
        binding.ottNoUseIv.setOnClickListener(this)
        binding.ottNoUseSelectIv.setOnClickListener(this)
        binding.ottNetflixIv.setOnClickListener(this)
        binding.ottNetflixSelectIv.setOnClickListener(this)
        binding.ottWhatchaIv.setOnClickListener(this)
        binding.ottWhatchaSelectIv.setOnClickListener(this)
        binding.ottDisneyIv.setOnClickListener(this)
        binding.ottDisneySelectIv.setOnClickListener(this)
        binding.ottWavveIv.setOnClickListener(this)
        binding.ottWavveSelectIv.setOnClickListener(this)
        binding.ottTvingIv.setOnClickListener(this)
        binding.ottTvingSelectIv.setOnClickListener(this)
        binding.ottCoupangIv.setOnClickListener(this)
        binding.ottCoupangSelectIv.setOnClickListener(this)
        binding.ottPrimeVideoIv.setOnClickListener(this)
        binding.ottPrimeVideoSelectIv.setOnClickListener(this)
        binding.ottAppleTvIv.setOnClickListener(this)
        binding.ottAppleTvSelectIv.setOnClickListener(this)


    }


    private fun showDialog() {
        val dialog = OttSelectCustomDialog(this)
        dialog.show()

        dialog.window?.setGravity(Gravity.CENTER_VERTICAL)

        dialog.findViewById<Button>(R.id.select_next_time_bt)?.setOnClickListener {
            val intent = Intent(this, JoinNicknameActivity::class.java)
            startActivity(intent)
        }

        dialog.findViewById<Button>(R.id.select_ok_bt)?.setOnClickListener {
            dialog.dismiss()
            binding.ottJumpIv.visibility = View.GONE
            binding.ottFinishNoColorIv.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            binding.ottNoUseIv.id -> {
                binding.ottNoUseIv.visibility = View.GONE
                binding.ottNoUseSelectIv.visibility = View.VISIBLE
                binding.ottNetflixIv.visibility = View.VISIBLE
                binding.ottNetflixSelectIv.visibility = View.GONE
                binding.ottWhatchaIv.visibility = View.VISIBLE
                binding.ottWhatchaSelectIv.visibility = View.GONE
                binding.ottDisneyIv.visibility = View.VISIBLE
                binding.ottDisneySelectIv.visibility = View.GONE
                binding.ottWavveIv.visibility = View.VISIBLE
                binding.ottWavveSelectIv.visibility = View.GONE
                binding.ottTvingIv.visibility = View.VISIBLE
                binding.ottTvingSelectIv.visibility = View.GONE
                binding.ottCoupangIv.visibility = View.VISIBLE
                binding.ottCoupangSelectIv.visibility = View.GONE
                binding.ottPrimeVideoIv.visibility = View.VISIBLE
                binding.ottPrimeVideoSelectIv.visibility = View.GONE
                binding.ottAppleTvIv.visibility = View.VISIBLE
                binding.ottAppleTvSelectIv.visibility = View.GONE
                ottList.clear()
                ottNum++
            }
            binding.ottNoUseSelectIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                ottNum = 0
                binding.ottNoUseSelectIv.visibility = View.GONE
            }
            binding.ottNetflixIv.id -> {
                binding.ottNetflixIv.visibility = View.GONE
                binding.ottNetflixSelectIv.visibility = View.VISIBLE
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("7")
            }
            binding.ottNetflixSelectIv.id -> {
                ottNum--
                ottList.remove("7")
                binding.ottNetflixIv.visibility = View.VISIBLE
                binding.ottNetflixSelectIv.visibility = View.GONE
            }
            binding.ottWhatchaIv.id -> {
                binding.ottWhatchaIv.visibility = View.GONE
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("8")
                binding.ottWhatchaSelectIv.visibility = View.VISIBLE
            }
            binding.ottWhatchaSelectIv.id -> {
                ottNum--
                ottList.remove("8")
                binding.ottWhatchaIv.visibility = View.VISIBLE
                binding.ottWhatchaSelectIv.visibility = View.GONE
            }
            binding.ottDisneyIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("3")
                binding.ottDisneyIv.visibility = View.GONE
                binding.ottDisneySelectIv.visibility = View.VISIBLE
            }
            binding.ottDisneySelectIv.id -> {
                ottNum--
                ottList.remove("3")
                binding.ottDisneyIv.visibility = View.VISIBLE
                binding.ottDisneySelectIv.visibility = View.GONE
            }
            binding.ottWavveIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("5")
                binding.ottWavveIv.visibility = View.GONE
                binding.ottWavveSelectIv.visibility = View.VISIBLE
            }
            binding.ottWavveSelectIv.id -> {
                ottNum--
                ottList.remove("5")
                binding.ottWavveIv.visibility = View.VISIBLE
                binding.ottWavveSelectIv.visibility = View.GONE
            }
            binding.ottTvingIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("6")
                binding.ottTvingIv.visibility = View.GONE
                binding.ottTvingSelectIv.visibility = View.VISIBLE
            }
            binding.ottTvingSelectIv.id -> {
                ottNum--
                ottList.remove("6")
                binding.ottTvingIv.visibility = View.VISIBLE
                binding.ottTvingSelectIv.visibility = View.GONE
            }
            binding.ottCoupangIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("4")
                binding.ottCoupangIv.visibility = View.GONE
                binding.ottCoupangSelectIv.visibility = View.VISIBLE
            }
            binding.ottCoupangSelectIv.id -> {
                ottNum--
                ottList.remove("4")
                binding.ottCoupangIv.visibility = View.VISIBLE
                binding.ottCoupangSelectIv.visibility = View.GONE
            }
            binding.ottPrimeVideoIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("2")
                binding.ottPrimeVideoIv.visibility = View.GONE
                binding.ottPrimeVideoSelectIv.visibility = View.VISIBLE
            }
            binding.ottPrimeVideoSelectIv.id -> {
                ottNum--
                ottList.remove("2")
                binding.ottPrimeVideoIv.visibility = View.VISIBLE
                binding.ottPrimeVideoSelectIv.visibility = View.GONE
            }
            binding.ottAppleTvIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                binding.ottNoUseSelectIv.visibility = View.GONE
                ottNum++
                ottList.add("1")
                binding.ottAppleTvIv.visibility = View.GONE
                binding.ottAppleTvSelectIv.visibility = View.VISIBLE
            }
            binding.ottAppleTvSelectIv.id -> {
                ottNum--
                ottList.remove("1")
                binding.ottAppleTvIv.visibility = View.VISIBLE
                binding.ottAppleTvSelectIv.visibility = View.GONE
            }
        }

        binding.ottJumpIv.visibility = View.GONE

        if (ottNum == 0) {
            binding.ottFinishColorIv.visibility = View.GONE
            binding.ottFinishNoColorIv.visibility = View.VISIBLE
        } else {
            binding.ottFinishColorIv.visibility = View.VISIBLE
            binding.ottFinishNoColorIv.visibility = View.GONE
        }


        ottList.sort()
    }


}