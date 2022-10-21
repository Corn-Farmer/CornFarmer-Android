package org.cornfarmer.presentation.join

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.cornfarmer.R
import org.cornfarmer.data.model.response.ResponseJoin
import org.cornfarmer.data.repository.JoinService
import org.cornfarmer.data.view.JoinView
import org.cornfarmer.databinding.ActivityJoinNicknameBinding
import org.cornfarmer.di.Application
import org.cornfarmer.util.ext.showToast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import kotlin.collections.HashMap

class JoinNicknameActivity : AppCompatActivity(), JoinView {

    private lateinit var binding: ActivityJoinNicknameBinding

    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_nickname)

        checkPermissions(PERMISSIONS)

        binding.ivCamera.setOnClickListener {
            showDialog()
        }

        binding.ivFinishColor.setOnClickListener {
            val today: String = binding.etYear.text.toString() +
                "" + binding.etMonth.text.toString() +
                "" + binding.etDay.text.toString()

            val currentTime: Long = System.currentTimeMillis()
            val dataFormat1 = SimpleDateFormat("yyyyMMdd")
            val dataFormat2 = SimpleDateFormat("yyyy")

            if (today.toInt() > dataFormat1.format(currentTime).toInt()) {
                showToast("오늘 날짜 보다 큽니다!")
                return@setOnClickListener
            } else if (today.toInt() < dataFormat1.format(currentTime).toInt() - 1000000) {
                showToast("${dataFormat2.format(currentTime).toInt() - 100}년 이상 기입해 주세요")
                return@setOnClickListener
            }

            signUp()

            val sharedPreferences = Application.joinSharedPreferences
            val servertoken = sharedPreferences.getString("servertoken", "")
            val photo = sharedPreferences.getString("photo", null)
            val nickname = sharedPreferences.getString("nickname", null)
            val sex = sharedPreferences.getString("isMale", null)
            val birthday = sharedPreferences.getString("birthday", null)
            val ottList = sharedPreferences.getString("ottlist", null)
            val genreList = sharedPreferences.getString("genrelist", null)

            val joinSharedPreferences = Application.joinSharedPreferences
            joinSharedPreferences.edit().putString("check_camera", "완료").apply()

            val nicknameRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), nickname!!)
            val sexRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), sex!!)
            val birthdayRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), birthday!!)
            val ottListRequest =
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    ottList!!.replace("[", "").replace("]", "")
                )
            val genreRequest =
                RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    genreList!!.replace("[", "").replace("]", "")
                )

            val requestMap: HashMap<String, RequestBody> = HashMap()

            requestMap["nickname"] = nicknameRequest
            requestMap["is_male"] = sexRequest
            requestMap["birth"] = birthdayRequest
            requestMap["ottList"] = ottListRequest
            requestMap["genreList"] = genreRequest

            if (photo == null) {
                val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), "noimage")
                val body = MultipartBody.Part.createFormData("photo", "noimage", requestFile)
                val service = JoinService(this, servertoken.toString(), body, requestMap)
                service.tryPostJoin()
            } else {
                val file = File(photo.toString())
                val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                val service = JoinService(this, servertoken.toString(), body, requestMap)
                service.tryPostJoin()
            }
        }

        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
                if (binding.etNickname.length() < 3) {
                    binding.ivNicknameOk.visibility = View.GONE
                } else {
                    binding.ivNicknameOk.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etYear.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etMonth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                joinCheck()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etDay.addTextChangedListener(object : TextWatcher {
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
        if (binding.etNickname.length() < 3 ||
            binding.etYear.length() < 4 ||
            binding.etMonth.length() < 2 ||
            binding.etDay.length() < 2 ||
            binding.etYear.text.toString().toInt() > 2022 ||
            binding.etMonth.text.toString().toInt() > 12 ||
            binding.etDay.text.toString().toInt() > 31 ||
            binding.etYear.text.toString().toInt() < 1900
        ) {
            binding.ivFinish.visibility = View.VISIBLE
            binding.ivFinishColor.visibility = View.GONE
        } else if (binding.etNickname.length() > 2 && binding.etYear.length() > 3 && binding.etMonth.length() > 1 &&
            binding.etDay.length() > 1
        ) {
            binding.ivFinish.visibility = View.GONE
            binding.ivFinishColor.visibility = View.VISIBLE
        }
    }

    private fun signUp() {
        val isMale: String

        val sharedPreferences = Application.joinSharedPreferences
        val editor = sharedPreferences.edit()

        if (binding.rbFemale.isChecked) {
            isMale = "true"
            editor.putString("isMale", isMale)
        } else if (binding.rbFemale.isChecked) {
            isMale = "false"
            editor.putString("isMale", isMale)
        }

        editor.putString("nickname", binding.etNickname.text.toString())
        editor.putString(
            "birthday",
            binding.etDay.text.toString() + "-" +
                binding.etMonth.text.toString() + "-" +
                binding.etDay.text.toString()
        )
        editor.apply()
    }

    override fun onPostJoinSuccess(response: ResponseJoin) {
        Log.d("JOIN-API", response.toString())

        when (response.code) {
            2022 -> {
                binding.tvNicknameLength.visibility = View.VISIBLE
                binding.tvNicknameLength.text = response.message
                return
            }
            3015 -> {
                binding.btCheckDuplicate.visibility = View.VISIBLE
                binding.btCheckDuplicate.text = response.message
                return
            }
            4000 -> {
                showToast("데이터베이스 연결에 실패하였습니다.")
                return
            }
            else -> {
                val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("useridx", response.result!!.userIdx)
                editor.apply()

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

        alertDialog.findViewById<Button>(R.id.bt_camera)?.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)
            startActivityForResult(takePictureIntent, 100)
            alertDialog.dismiss()
        }

        alertDialog.findViewById<Button>(R.id.bt_album)?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 200)
            alertDialog.dismiss()
        }

        alertDialog.findViewById<ImageView>(R.id.bt_cancel)?.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            val dataUri = data?.data
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
            saveBitmapAsPNGFile(bitmap)
            binding.ivImage.setImageBitmap(bitmap)
        } else if (resultCode == RESULT_OK && requestCode == 100) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveBitmapAsPNGFile(imageBitmap)
            binding.ivImage.setImageBitmap(imageBitmap)
        }
    }

    private fun checkPermissions(permissions: Array<String>): Boolean {
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
                showToast("권한 승인 부탁드립니다.")
                finish()
            }
        }
    }

    private fun newPngFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.png"
    }

    private fun saveBitmapAsPNGFile(bitmap: Bitmap) {
        val path = File(filesDir, "image")
        if (!path.exists()) {
            path.mkdirs()
        }

        val photoName = newPngFileName()
        val file = File(path, photoName)
        var imageFile: OutputStream?
        try {
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageFile)
            imageFile.close()

            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("photo", file.absolutePath.toString())
            Log.d("Photo", file.absolutePath.toString())
            editor.apply()
        } catch (e: Exception) {
            Log.d("Exception", e.toString())
        }
    }
}
