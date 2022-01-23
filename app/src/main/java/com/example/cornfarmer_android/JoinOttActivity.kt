package com.example.cornfarmer_android

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corn_farmer.MainActivity
import com.example.cornfarmer_android.databinding.ActivityJoinOttBinding
import java.util.*


class JoinOttActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityJoinOttBinding


    var ottNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinOttBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ottJumpIv.setOnClickListener {
            showDialog()
        }

        binding.ottFinishColorIv.setOnClickListener {
            val intent = Intent(this, JoinGenreActivity::class.java)
            startActivity(intent)
        }

        binding.ottBackIv.setOnClickListener {
            finish()
        }

        bindOtt()

    }

    private fun bindOtt(){
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
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.ott_select_custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this, R.style.OttAlertDialog)
            .setView(mDialogView)

        val alertDialog = mBuilder.show()
        alertDialog.window?.setGravity(Gravity.CENTER_VERTICAL)

        alertDialog.findViewById<Button>(R.id.select_next_time_bt)?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        alertDialog.findViewById<Button>(R.id.select_ok_bt)?.setOnClickListener {
            alertDialog.dismiss()
            binding.ottJumpIv.visibility = View.GONE
            binding.ottFinishNoColorIv.visibility = View.VISIBLE
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.ottNoUseIv.id -> {
                binding.ottNoUseIv.visibility = View.GONE
                binding.ottNoUseSelectIv.visibility = View.VISIBLE
                ottNum++
            }
            binding.ottNoUseSelectIv.id -> {
                binding.ottNoUseIv.visibility = View.VISIBLE
                ottNum--
                binding.ottNoUseSelectIv.visibility = View.GONE
            }
            binding.ottNetflixIv.id -> {
                binding.ottNetflixIv.visibility = View.GONE
                binding.ottNetflixSelectIv.visibility = View.VISIBLE
                ottNum++
            }
            binding.ottNetflixSelectIv.id -> {
                ottNum--
                binding.ottNetflixIv.visibility = View.VISIBLE
                binding.ottNetflixSelectIv.visibility = View.GONE
            }
            binding.ottWhatchaIv.id -> {
                binding.ottWhatchaIv.visibility = View.GONE
                ottNum++
                binding.ottWhatchaSelectIv.visibility = View.VISIBLE
            }
            binding.ottWhatchaSelectIv.id -> {
                ottNum--
                binding.ottWhatchaIv.visibility = View.VISIBLE
                binding.ottWhatchaSelectIv.visibility = View.GONE
            }
            binding.ottDisneyIv.id -> {
                ottNum++
                binding.ottDisneyIv.visibility = View.GONE
                binding.ottDisneySelectIv.visibility = View.VISIBLE
            }
            binding.ottDisneySelectIv.id -> {
                ottNum--
                binding.ottDisneyIv.visibility = View.VISIBLE
                binding.ottDisneySelectIv.visibility = View.GONE
            }
            binding.ottWavveIv.id -> {
                ottNum++
                binding.ottWavveIv.visibility = View.GONE
                binding.ottWavveSelectIv.visibility = View.VISIBLE
            }
            binding.ottWavveSelectIv.id -> {
                ottNum--
                binding.ottWavveIv.visibility = View.VISIBLE
                binding.ottWavveSelectIv.visibility = View.GONE
            }
            binding.ottTvingIv.id -> {
                ottNum++
                binding.ottTvingIv.visibility = View.GONE
                binding.ottTvingSelectIv.visibility = View.VISIBLE
            }
            binding.ottTvingSelectIv.id -> {
                ottNum--
                binding.ottTvingIv.visibility = View.VISIBLE
                binding.ottTvingSelectIv.visibility = View.GONE
            }
            binding.ottCoupangIv.id -> {
                ottNum++
                binding.ottCoupangIv.visibility = View.GONE
                binding.ottCoupangSelectIv.visibility = View.VISIBLE
            }
            binding.ottCoupangSelectIv.id -> {
                ottNum--
                binding.ottCoupangIv.visibility = View.VISIBLE
                binding.ottCoupangSelectIv.visibility = View.GONE
            }
            binding.ottPrimeVideoIv.id -> {
                ottNum++
                binding.ottPrimeVideoIv.visibility = View.GONE
                binding.ottPrimeVideoSelectIv.visibility = View.VISIBLE
            }
            binding.ottPrimeVideoSelectIv.id -> {
                ottNum--
                binding.ottPrimeVideoIv.visibility = View.VISIBLE
                binding.ottPrimeVideoSelectIv.visibility = View.GONE
            }
            binding.ottAppleTvIv.id -> {
                ottNum++
                binding.ottAppleTvIv.visibility = View.GONE
                binding.ottAppleTvSelectIv.visibility = View.VISIBLE
            }
            binding.ottAppleTvSelectIv.id -> {
                ottNum--
                binding.ottAppleTvIv.visibility = View.VISIBLE
                binding.ottAppleTvSelectIv.visibility = View.GONE
            }
        }

        binding.ottJumpIv.visibility = View.GONE

        if(ottNum == 0){
            binding.ottFinishColorIv.visibility = View.GONE
            binding.ottFinishNoColorIv.visibility = View.VISIBLE
        }else{
            binding.ottFinishColorIv.visibility = View.VISIBLE
            binding.ottFinishNoColorIv.visibility = View.GONE
        }

    }




}