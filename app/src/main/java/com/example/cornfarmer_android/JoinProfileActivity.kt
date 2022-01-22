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
import com.example.cornfarmer_android.databinding.ActivityJoinProfileBinding

class JoinProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileImagePlusIv.setOnClickListener{
            showDialog()
        }

        binding.profileNextIv.setOnClickListener {
            val intent = Intent(this, JoinNicknameActivity::class.java)
            startActivity(intent)
        }



    }

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.image_select_custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(mDialogView)

        val alertDialog = mBuilder.show()
        alertDialog.window?.setGravity(Gravity.BOTTOM)



        alertDialog.findViewById<Button>(R.id.select_camera_bt)?.setOnClickListener {
        }

        alertDialog.findViewById<Button>(R.id.select_album_bt)?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            binding.profileFinishIv.visibility = View.GONE
            binding.profileNextIv.visibility = View.VISIBLE
        }

        alertDialog.findViewById<ImageView>(R.id.select_cancel_bt)?.setOnClickListener {
            alertDialog.dismiss()
        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){

        }
    }

}