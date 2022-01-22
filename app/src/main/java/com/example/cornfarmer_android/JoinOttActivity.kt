package com.example.cornfarmer_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.cornfarmer_android.databinding.ActivityJoinOttBinding

class JoinOttActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinOttBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinOttBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ottJumpIv.setOnClickListener {
            showDialog()
        }


    }

    private fun showDialog(){
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


}