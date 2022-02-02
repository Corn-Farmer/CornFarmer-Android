package com.example.cornfarmer_android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.net.Uri
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.KakaoService
import com.example.KakaoView
import com.example.cornfarmer_android.databinding.ActivityJoinProfileBinding
import kotlin.math.min

class JoinProfileActivity : AppCompatActivity(), KakaoView {

    private lateinit var binding: ActivityJoinProfileBinding

    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100

    private val BUTTON1 = 100
    private val BUTTON2 = 200
    private val BUTTON3 = 300
    private val BUTTON4 = 400
    private val BUTTON5 = 500

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
            val intent = Intent(this, JoinNicknameActivity::class.java)
            startActivity(intent)
        }

        binding.profileBackIv.setOnClickListener {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            binding.profileFinishIv.visibility = View.GONE
            binding.profileNextIv.visibility = View.VISIBLE
            var dataUri = data?.data
            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
            binding.profileImageIv.setImageBitmap(bitmap.cropCircularArea(50))
        } else if (resultCode == RESULT_OK && requestCode == 100) {
            binding.profileFinishIv.visibility = View.GONE
            binding.profileNextIv.visibility = View.VISIBLE
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImageIv.setImageBitmap(imageBitmap.cropCircularArea(10))
        }

    }

    private fun Bitmap.cropCircularArea(heightY: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            width, // width in pixels
            height, // height in pixels
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        // create a circular path
        val path = Path()
        val radius = min(width / 2f, height / 2f)
        path.apply {
            addCircle(
                width / 2f,
                height / 2f,
                radius,
                Path.Direction.CCW
            )
        }

        // draw circular bitmap on canvas
        canvas.clipPath(path)
        canvas.drawBitmap(this, 0f, 0f, null)

        val diameter = (radius * 2).toInt()
        val x = (width - diameter) / 2
        val y = (height - diameter) / 2

        // return cropped circular bitmap
        return Bitmap.createBitmap(
            bitmap, // source bitmap
            x, // x coordinate of the first pixel in source
            y, // y coordinate of the first pixel in source
            diameter, // width
            diameter + heightY // height
        )
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

    override fun onStart() {
        super.onStart()

        val sharedPreferences = getSharedPreferences("kakaotoken", MODE_PRIVATE)
        val kakaotoken = sharedPreferences.getString("kakaotoken", null)

        kLogin(kakaotoken.toString())
    }

    private fun kLogin(accessToken: String) {
        val kakaoService = KakaoService()
        kakaoService.setKakaoView(this)

        kakaoService.kLogin(accessToken)
    }

    override fun onKakaoLoginLoading() {
    }

    override fun onKakaoLoginSuccess() {
    }

    override fun onKakaoLoginFailure(code: Int, message: String) {
    }

}

