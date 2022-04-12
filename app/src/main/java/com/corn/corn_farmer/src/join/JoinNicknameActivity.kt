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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.join.model.getJoinAPI
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityJoinNicknameBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class JoinNicknameActivity : AppCompatActivity(), JoinView {

    private lateinit var binding: ActivityJoinNicknameBinding


    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100

    private var photoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_nickname)

        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)

        binding.profileImagePlusIv.setOnClickListener {
            showDialog()
        }

        binding.nicknameFinishColorIv.setOnClickListener {

            var today: String = binding.loginBirthdayEt.text.toString() +
                    "" + binding.loginBirthdayMonthEt.text.toString() +
                    "" + binding.loginBirthdayDayEt.text.toString()

            var currentTime: Long = System.currentTimeMillis()
            val dataFormat1 = SimpleDateFormat("yyyyMMdd")
            val dataFormat2 = SimpleDateFormat("yyyy")

            if(today.toInt() > dataFormat1.format(currentTime).toInt()){
                Toast.makeText(this, "오늘 날짜 보다 큽니다!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else if( today.toInt() < dataFormat1.format(currentTime).toInt() - 1000000){
                Toast.makeText(this, "${dataFormat2.format(currentTime).toInt() - 100}년 이상 기입해 주세요", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            signUp()

            val sharedPreferences = Application.joinSharedPreferences
            val servertoken = sharedPreferences.getString("servertoken", "")
            val photo = sharedPreferences.getString("photo", null)
            val nickname = sharedPreferences.getString("nickname", null)
            var sex = sharedPreferences.getString("isMale", null)
            val birthday = sharedPreferences.getString("birthday", null)
            val ottList = sharedPreferences.getString("ottlist", null)
            val genreList = sharedPreferences.getString("genrelist", null)

            val joinSharedPreferences = Application.joinSharedPreferences
            joinSharedPreferences.edit().putString("check_camera", "완료").commit()

            val nicknameRequest = RequestBody.create(MediaType.parse("text/plain"), nickname!!)
            val sexRequest = RequestBody.create(MediaType.parse("text/plain"), sex)
            val birthdayRequest = RequestBody.create(MediaType.parse("text/plain"), birthday!!)
            val ottListRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    ottList!!.replace("[", "").replace("]", "")
                )
            val genreRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    genreList!!.replace("[", "").replace("]", "")
                )

            val requestMap: HashMap<String, RequestBody> = HashMap()

            requestMap.put("nickname", nicknameRequest)
            requestMap.put("is_male", sexRequest)
            requestMap.put("birth", birthdayRequest)
            requestMap.put("ottList", ottListRequest)
            requestMap.put("genreList", genreRequest)

            Log.d("JOIN-token", servertoken.toString())
            Log.d("JOIN-nickname", nickname.toString())
            Log.d("JOIN-sex", sex.toString())
            Log.d("JOIN-birthday", birthday.toString())
            Log.d("JOIN-ottlist", ottList.toString().replace("[", "").replace("]", ""))
            Log.d("JOIN-genrelist", genreList.toString().replace("[", "").replace("]", ""))


            if (photo == null) {
                val requestFile = RequestBody.create(MediaType.parse("image/png"), "noimage")
                val body = MultipartBody.Part.createFormData("photo", "noimage", requestFile)
                var service = JoinService(this, servertoken.toString(), body, requestMap)
                service.tryPostJoin()
            } else {
                val file = File(photo.toString())
                val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
                val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                var service = JoinService(this, servertoken.toString(), body, requestMap)
                service.tryPostJoin()
            }


        }

        binding.nicknameNicknameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
                if (binding.nicknameNicknameEt.length() < 3) {
                    binding.nicknamePassOk.visibility = View.GONE
                } else {
                    binding.nicknamePassOk.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayMonthEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayDayEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


    }

    private fun joinCheck() {
        if (binding.nicknameNicknameEt.length() < 3 ||
            binding.loginBirthdayEt.length() < 4 ||
            binding.loginBirthdayMonthEt.length() < 2 ||
            binding.loginBirthdayDayEt.length() < 2 ||
            binding.loginBirthdayEt.text.toString().toInt() > 2022 ||
            binding.loginBirthdayMonthEt.text.toString().toInt() > 12 ||
            binding.loginBirthdayDayEt.text.toString().toInt() > 31 ||
            binding.loginBirthdayEt.text.toString().toInt() < 1900
        ) {
            binding.nicknameFinishIv.visibility = View.VISIBLE
            binding.nicknameFinishColorIv.visibility = View.GONE
        } else if (binding.nicknameNicknameEt.length() > 2 && binding.loginBirthdayEt.length() > 3 && binding.loginBirthdayMonthEt.length() > 1
            && binding.loginBirthdayDayEt.length() > 1
        ) {
            binding.nicknameFinishIv.visibility = View.GONE
            binding.nicknameFinishColorIv.visibility = View.VISIBLE
        }

    }

    private fun signUp() {

        var isMale: String

        val sharedPreferences = Application.joinSharedPreferences
        val editor = sharedPreferences.edit()

        if (binding.nicknameMaleRb.isChecked) {
            isMale = "true"
            editor.putString("isMale", isMale)
        } else if (binding.nicknameFemaleRb.isChecked) {
            isMale = "false"
            editor.putString("isMale", isMale)
        }

        editor.putString("nickname", binding.nicknameNicknameEt.text.toString())
        editor.putString(
            "birthday", binding.loginBirthdayEt.text.toString() + "-" +
                    binding.loginBirthdayMonthEt.text.toString() + "-" +
                    binding.loginBirthdayDayEt.text.toString()
        )
        editor.commit()
    }

    override fun onPostJoinSuccess(response: getJoinAPI) {
        Log.d("JOIN-API", response.toString())

        when (response.code) {
            2022 -> {
                binding.nicknameNicknameNumIv.visibility = View.VISIBLE
                binding.nicknameNicknameNumIv.text = response.message
                return
            }
            3015 -> {
                binding.nicknameUsingNicknameIv.visibility = View.VISIBLE
                binding.nicknameUsingNicknameIv.text = response.message
                return
            }
            4000 -> {
                Toast.makeText(this, "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("useridx", response.result!!.userIdx)
                editor.commit()

                val intent = Intent(this, SplashJoinActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    override fun onPostJoinFailure(message: String) {
        Log.d("JoinFail", message)
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

    private fun newPngFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.png"
    }

    private fun saveBitmapAsPNGFile(bitmap: Bitmap) {
        val path = File(filesDir, "image")
        if (!path.exists()) {
            path.mkdirs()
        }

        val photoName = newPngFileName()
        val file = File(path, photoName)
        var imageFile: OutputStream? = null
        try {
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageFile)
            imageFile.close()

            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("photo", file.absolutePath.toString())
            Log.d("Photo", file.absolutePath.toString())
            editor.commit()

        } catch (e: Exception) {
            null
        }
    }



}