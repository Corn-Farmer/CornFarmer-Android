package com.corn.corn_farmer.src.join

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityJoinProfileBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat

class JoinProfileActivity() : AppCompatActivity(){

    private lateinit var binding: ActivityJoinProfileBinding

    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100

    private var photoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)

        binding.profileImagePlusIv.setOnClickListener {
            showDialog()
        }

        binding.profileNextIv.setOnClickListener {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            sharedPreferences.edit().putString("check_camera","완료").commit()
            val intent = Intent(this, JoinOttActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun showDialog() {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.image_select_custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this, R.style.SelectAlertDialog)
            .setView(mDialogView)

        val alertDialog = mBuilder.show()
        alertDialog.window?.setGravity(Gravity.BOTTOM)

        alertDialog.findViewById<Button>(R.id.select_camera_bt)?.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)
            startActivityForResult(takePictureIntent, 100)
            alertDialog.dismiss()
        }

        alertDialog.findViewById<Button>(R.id.select_album_bt)?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 200)
            alertDialog.dismiss()
        }

        alertDialog.findViewById<ImageView>(R.id.select_cancel_bt)?.setOnClickListener {
            alertDialog.dismiss()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {

            var dataUri = data?.data
            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
            saveBitmapAsPNGFile(bitmap)
            binding.profileImageIv.setImageBitmap(bitmap)


        } else if (resultCode == RESULT_OK && requestCode == 100) {


            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveBitmapAsPNGFile(imageBitmap)
            binding.profileImageIv.setImageBitmap(imageBitmap)
        }

    }

    private fun checkPermissions(permissions: Array<String>, permissionsRequest: Int): Boolean {
        val permissionList: MutableList<String> = mutableListOf()
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionList.toTypedArray(),
                PERMISSIONS_REQUEST
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 승인 부탁드립니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun newPngFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.png"
    }

    private fun saveBitmapAsPNGFile(bitmap: Bitmap) {
        val path = File(filesDir, "image")
        if(!path.exists()){
            path.mkdirs()
        }

        val photoName = newPngFileName()

        val file = File(path, photoName)
        var imageFile: OutputStream? = null
        try{
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageFile)
            imageFile.close()

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("photo", file.absolutePath.toString())
            Log.d("Photo",file.absolutePath.toString())
            editor.putString("photoname", photoName)
            editor.commit()

        }catch (e: Exception){
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

