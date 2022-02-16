package com.example.corn_farmer.src.profile_modify

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.JoinService
import com.example.corn_farmer.src.profile.ProfileFragmentView
import com.example.corn_farmer.src.profile.ProfileService
import com.example.corn_farmer.src.profile.model.ModifyResponse
import com.example.corn_farmer.src.profile.model.ProfileGenre
import com.example.corn_farmer.src.profile.model.ProfileOtt
import com.example.corn_farmer.src.profile.model.ProfileResponse
import com.example.corn_farmer.src.profile_modify.model.ModifyProfileResponse
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityProfileModifyBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat

class ProfileModifyActivity : AppCompatActivity(), ModifyView {

    lateinit var binding: ActivityProfileModifyBinding
    var ottList = mutableListOf<String>()
    var genreList = mutableListOf<String>()

    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPreferences = getSharedPreferences("join", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        val userIdx: Int = sharedPreferences2.getInt("useridx", 0)

        val serverToken = sharedPreferences.getString("servertoken", "")

        val service = ModifyProfileService(this, userIdx, serverToken)
        service.tryGetModifyProfile()


//        val photo = sharedPreferences.getString("photo", null)
//        binding.modifyImageIv.setImageURI(Uri.parse(photo))

        binding.profileImageDelete2Iv.setOnClickListener {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("photo", "noimage")
            editor.commit()

            binding.modifyImageIv.setImageResource(R.drawable.cornfarmerprofile)

        }



        binding.profileImagePlusIv.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)
            startActivityForResult(takePictureIntent, 100)
        }

        binding.profileImageDeleteIv.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 200)
        }




        binding.modifyCancelIv.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.modifyCompleteIv.setOnClickListener { //완료버튼 눌렀을 때
            val photo = sharedPreferences.getString("photo", "noimage")

            var modifiedNickname: String = binding.modifyNicknameInfoEt.text.toString()

            val nicknameRequest =
                RequestBody.create(MediaType.parse("text/plain"), modifiedNickname!!)

            val ottListRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    ottList!!.toString().replace("[", "").replace("]", "")
                )
            val genreRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    genreList.toString().replace("[", "").replace("]", "")
                )

            val servertoken = sharedPreferences.getString("servertoken", "")
            val requestMap: HashMap<String, RequestBody> = HashMap()



            requestMap.put("nickname", nicknameRequest)
            requestMap.put("ottList", ottListRequest)
            requestMap.put("genreList", genreRequest)

            if(photo == "noimage"){
                val requestFile = RequestBody.create(MediaType.parse("image/png"), "noimage")
                val body = MultipartBody.Part.createFormData("photo", "noimage", requestFile)
                var service = ModifyService(this, servertoken.toString(), body, requestMap, userIdx)
                service.tryPutModify()
            }else{
                val file = File(photo.toString())
                val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
                val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                var service = ModifyService(this, servertoken.toString(), body, requestMap, userIdx)
                service.tryPutModify()
            }

//            val file = File(photo.toString())
//            val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
//            val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
//
//            var service = ModifyService(this, servertoken.toString(), body, requestMap, userIdx)
//            service.tryPutModify()
//

        }
    }//onCreate

    private fun genreVisibility(nowUserHasGenreList: List<String>) {
        if (nowUserHasGenreList.contains("1")) {
            genreList.add("1")
            binding.profileActionColorTv.visibility = View.VISIBLE
            binding.profileActionDeleteIv.visibility = View.VISIBLE
            binding.profileActionTv.visibility = View.GONE
        } else {
            binding.profileActionColorTv.visibility = View.GONE
            binding.profileActionDeleteIv.visibility = View.INVISIBLE
            binding.profileActionTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("2")) {
            genreList.add("2")
            binding.profileAnimeColorTv.visibility = View.VISIBLE
            binding.profileAnimeDeleteIv.visibility = View.VISIBLE
            binding.profileAnimeTv.visibility = View.GONE
        } else {
            binding.profileAnimeColorTv.visibility = View.GONE
            binding.profileAnimeDeleteIv.visibility = View.INVISIBLE
            binding.profileAnimeTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("3")) {
            genreList.add("3")
            binding.profileComedyColorTv.visibility = View.VISIBLE
            binding.profileComedyDeleteIv.visibility = View.VISIBLE
            binding.profileComedyTv.visibility = View.GONE
        } else {
            binding.profileComedyColorTv.visibility = View.GONE
            binding.profileComedyDeleteIv.visibility = View.INVISIBLE
            binding.profileComedyTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("4")) {
            genreList.add("4")
            binding.profileCrimeColorTv.visibility = View.VISIBLE
            binding.profileCrimeDeleteIv.visibility = View.VISIBLE
            binding.profileCrimeTv.visibility = View.GONE
        } else {
            binding.profileCrimeColorTv.visibility = View.GONE
            binding.profileCrimeDeleteIv.visibility = View.INVISIBLE
            binding.profileCrimeTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("5")) {
            genreList.add("5")
            binding.profileDacuColorTv.visibility = View.VISIBLE
            binding.profileDacuDeleteIv.visibility = View.VISIBLE
            binding.profileDacuTv.visibility = View.GONE
        } else {
            binding.profileDacuColorTv.visibility = View.GONE
            binding.profileDacuDeleteIv.visibility = View.INVISIBLE
            binding.profileDacuTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("6")) {
            genreList.add("6")
            binding.profileDramaColorTv.visibility = View.VISIBLE
            binding.profileDramaDeleteIv.visibility = View.VISIBLE
            binding.profileDramaTv.visibility = View.GONE
        } else {
            binding.profileDramaColorTv.visibility = View.GONE
            binding.profileDramaDeleteIv.visibility = View.INVISIBLE
            binding.profileDramaTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("7")) {
            genreList.add("7")
            binding.profileFantasyColorTv.visibility = View.VISIBLE
            binding.profileFantasyDeleteIv.visibility = View.VISIBLE
            binding.profileFantasyTv.visibility = View.GONE
        } else {
            binding.profileFantasyColorTv.visibility = View.GONE
            binding.profileFantasyDeleteIv.visibility = View.INVISIBLE
            binding.profileFantasyTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("8")) {
            genreList.add("8")
            binding.profileHistoryColorTv.visibility = View.VISIBLE
            binding.profileHistoryDeleteIv.visibility = View.VISIBLE
            binding.profileHistoryTv.visibility = View.GONE
        } else {
            binding.profileHistoryColorTv.visibility = View.GONE
            binding.profileHistoryDeleteIv.visibility = View.INVISIBLE
            binding.profileHistoryTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("9")) {
            genreList.add("9")
            binding.profileHororColorTv.visibility = View.VISIBLE
            binding.profileHororDeleteIv.visibility = View.VISIBLE
            binding.profileHororTv.visibility = View.GONE
        } else {
            binding.profileHororColorTv.visibility = View.GONE
            binding.profileHororDeleteIv.visibility = View.INVISIBLE
            binding.profileHororTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("10")) {
            genreList.add("10")
            binding.profileFamilyColorTv.visibility = View.VISIBLE
            binding.profileFamilyDeleteIv.visibility = View.VISIBLE
            binding.profileFamilyTv.visibility = View.GONE
        } else {
            binding.profileFamilyColorTv.visibility = View.GONE
            binding.profileFamilyDeleteIv.visibility = View.INVISIBLE
            binding.profileFamilyTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("11")) {
            genreList.add("11")
            binding.profileMusicColorTv.visibility = View.VISIBLE
            binding.profileMusicDeleteIv.visibility = View.VISIBLE
            binding.profileMusicTv.visibility = View.GONE
        } else {
            binding.profileMusicColorTv.visibility = View.GONE
            binding.profileMusicDeleteIv.visibility = View.INVISIBLE
            binding.profileMusicTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("12")) {
            genreList.add("12")
            binding.profileThrillColorTv.visibility = View.VISIBLE
            binding.profileThrillDeleteIv.visibility = View.VISIBLE
            binding.profileThrillTv.visibility = View.GONE
        } else {
            binding.profileThrillColorTv.visibility = View.GONE
            binding.profileThrillDeleteIv.visibility = View.INVISIBLE
            binding.profileThrillTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("13")) {
            genreList.add("13")
            binding.profileRomanceColorTv.visibility = View.VISIBLE
            binding.profileRomanceDeleteIv.visibility = View.VISIBLE
            binding.profileRomanceTv.visibility = View.GONE
        } else {
            binding.profileRomanceColorTv.visibility = View.GONE
            binding.profileRomanceDeleteIv.visibility = View.INVISIBLE
            binding.profileRomanceTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("14")) {
            genreList.add("14")
            binding.profileSfColorTv.visibility = View.VISIBLE
            binding.profileSfDeleteIv.visibility = View.VISIBLE
            binding.profileSfTv.visibility = View.GONE
        } else {
            binding.profileSfColorTv.visibility = View.GONE
            binding.profileSfDeleteIv.visibility = View.INVISIBLE
            binding.profileSfTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("15")) {
            genreList.add("15")
            binding.profileSportColorTv.visibility = View.VISIBLE
            binding.profileSportDeleteIv.visibility = View.VISIBLE
            binding.profileSportTv.visibility = View.GONE
        } else {
            binding.profileSportColorTv.visibility = View.GONE
            binding.profileSportDeleteIv.visibility = View.INVISIBLE
            binding.profileSportTv.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("16")) {
            genreList.add("16")
            binding.profileWarColorTv.visibility = View.VISIBLE
            binding.profileWarDeleteIv.visibility = View.VISIBLE
            binding.profileWarTv.visibility = View.GONE
        } else {
            binding.profileWarColorTv.visibility = View.GONE
            binding.profileWarDeleteIv.visibility = View.INVISIBLE
            binding.profileWarTv.visibility = View.VISIBLE
        }


        binding.profileActionTv.setOnClickListener {

            genreList.add("1")
            binding.profileActionColorTv.visibility = View.VISIBLE
            binding.profileActionDeleteIv.visibility = View.VISIBLE
            binding.profileActionTv.visibility = View.GONE
        }
        binding.profileActionDeleteIv.setOnClickListener {
            binding.profileActionColorTv.visibility = View.GONE
            binding.profileActionDeleteIv.visibility = View.INVISIBLE
            binding.profileActionTv.visibility = View.VISIBLE
            genreList.remove("1")
        }
        binding.profileAnimeTv.setOnClickListener {

            genreList.add("2")
            binding.profileAnimeColorTv.visibility = View.VISIBLE
            binding.profileAnimeDeleteIv.visibility = View.VISIBLE
            binding.profileAnimeTv.visibility = View.GONE
        }
        binding.profileAnimeDeleteIv.setOnClickListener {
            genreList.remove("2")
            binding.profileAnimeColorTv.visibility = View.GONE
            binding.profileAnimeDeleteIv.visibility = View.INVISIBLE
            binding.profileAnimeTv.visibility = View.VISIBLE
        }
        binding.profileComedyTv.setOnClickListener {

            genreList.add("3")
            binding.profileComedyColorTv.visibility = View.VISIBLE
            binding.profileComedyDeleteIv.visibility = View.VISIBLE
            binding.profileComedyTv.visibility = View.GONE

        }
        binding.profileComedyDeleteIv.setOnClickListener {
            genreList.remove("3")
            binding.profileComedyColorTv.visibility = View.GONE
            binding.profileComedyDeleteIv.visibility = View.INVISIBLE
            binding.profileComedyTv.visibility = View.VISIBLE
        }
        binding.profileCrimeTv.setOnClickListener {

            genreList.add("4")
            binding.profileCrimeColorTv.visibility = View.VISIBLE
            binding.profileCrimeDeleteIv.visibility = View.VISIBLE
            binding.profileCrimeTv.visibility = View.GONE
        }
        binding.profileCrimeDeleteIv.setOnClickListener {
            genreList.remove("4")
            binding.profileCrimeColorTv.visibility = View.GONE
            binding.profileCrimeDeleteIv.visibility = View.INVISIBLE
            binding.profileCrimeTv.visibility = View.VISIBLE
        }
        binding.profileDacuTv.setOnClickListener {

            genreList.add("5")
            binding.profileDacuColorTv.visibility = View.VISIBLE
            binding.profileDacuDeleteIv.visibility = View.VISIBLE
            binding.profileDacuTv.visibility = View.GONE
        }
        binding.profileDacuDeleteIv.setOnClickListener {
            genreList.remove("5")
            binding.profileDacuColorTv.visibility = View.GONE
            binding.profileDacuDeleteIv.visibility = View.INVISIBLE
            binding.profileDacuTv.visibility = View.VISIBLE
        }
        binding.profileDramaTv.setOnClickListener {

            genreList.add("6")
            binding.profileDramaColorTv.visibility = View.VISIBLE
            binding.profileDramaDeleteIv.visibility = View.VISIBLE
            binding.profileDramaTv.visibility = View.GONE
        }
        binding.profileDramaDeleteIv.setOnClickListener {
            genreList.remove("6")
            binding.profileDramaColorTv.visibility = View.GONE
            binding.profileDramaDeleteIv.visibility = View.INVISIBLE
            binding.profileDramaTv.visibility = View.VISIBLE
        }
        binding.profileFantasyTv.setOnClickListener {

            genreList.add("7")
            binding.profileFantasyColorTv.visibility = View.VISIBLE
            binding.profileFantasyDeleteIv.visibility = View.VISIBLE
            binding.profileFantasyTv.visibility = View.GONE
        }
        binding.profileFantasyDeleteIv.setOnClickListener {
            genreList.remove("7")
            binding.profileFantasyColorTv.visibility = View.GONE
            binding.profileFantasyDeleteIv.visibility = View.INVISIBLE
            binding.profileFantasyTv.visibility = View.VISIBLE
        }
        binding.profileHistoryTv.setOnClickListener {

            genreList.add("8")
            binding.profileHistoryColorTv.visibility = View.VISIBLE
            binding.profileHistoryDeleteIv.visibility = View.VISIBLE
            binding.profileHistoryTv.visibility = View.GONE
        }
        binding.profileHistoryDeleteIv.setOnClickListener {
            genreList.remove("8")
            binding.profileHistoryColorTv.visibility = View.GONE
            binding.profileHistoryDeleteIv.visibility = View.INVISIBLE
            binding.profileHistoryTv.visibility = View.VISIBLE
        }
        binding.profileHororTv.setOnClickListener {

            genreList.add("9")
            binding.profileHororColorTv.visibility = View.VISIBLE
            binding.profileHororDeleteIv.visibility = View.VISIBLE
            binding.profileHororTv.visibility = View.GONE
        }
        binding.profileHororDeleteIv.setOnClickListener {
            genreList.remove("9")
            binding.profileHororColorTv.visibility = View.GONE
            binding.profileHororDeleteIv.visibility = View.INVISIBLE
            binding.profileHororTv.visibility = View.VISIBLE
        }
        binding.profileFamilyTv.setOnClickListener {

            genreList.add("10")
            binding.profileFamilyColorTv.visibility = View.VISIBLE
            binding.profileFamilyDeleteIv.visibility = View.VISIBLE
            binding.profileFamilyTv.visibility = View.GONE
        }
        binding.profileFamilyDeleteIv.setOnClickListener {
            genreList.remove("10")
            binding.profileFamilyColorTv.visibility = View.GONE
            binding.profileFamilyDeleteIv.visibility = View.INVISIBLE
            binding.profileFamilyTv.visibility = View.VISIBLE
        }
        binding.profileMusicTv.setOnClickListener {

            genreList.add("11")
            binding.profileMusicColorTv.visibility = View.VISIBLE
            binding.profileMusicDeleteIv.visibility = View.VISIBLE
            binding.profileMusicTv.visibility = View.GONE
        }
        binding.profileMusicDeleteIv.setOnClickListener {
            genreList.remove("11")
            binding.profileMusicColorTv.visibility = View.GONE
            binding.profileMusicDeleteIv.visibility = View.INVISIBLE
            binding.profileMusicTv.visibility = View.VISIBLE
        }
        binding.profileThrillTv.setOnClickListener {

            genreList.add("12")
            binding.profileThrillColorTv.visibility = View.VISIBLE
            binding.profileThrillDeleteIv.visibility = View.VISIBLE
            binding.profileThrillTv.visibility = View.GONE
        }
        binding.profileThrillDeleteIv.setOnClickListener {
            genreList.remove("12")
            binding.profileThrillColorTv.visibility = View.GONE
            binding.profileThrillDeleteIv.visibility = View.INVISIBLE
            binding.profileThrillTv.visibility = View.VISIBLE
        }
        binding.profileRomanceTv.setOnClickListener {

            genreList.add("13")
            binding.profileRomanceColorTv.visibility = View.VISIBLE
            binding.profileRomanceDeleteIv.visibility = View.VISIBLE
            binding.profileRomanceTv.visibility = View.GONE
        }
        binding.profileRomanceDeleteIv.setOnClickListener {
            genreList.remove("13")
            binding.profileRomanceColorTv.visibility = View.GONE
            binding.profileRomanceDeleteIv.visibility = View.INVISIBLE
            binding.profileRomanceTv.visibility = View.VISIBLE
        }
        binding.profileSfTv.setOnClickListener {

            genreList.add("14")
            binding.profileSfColorTv.visibility = View.VISIBLE
            binding.profileSfDeleteIv.visibility = View.VISIBLE
            binding.profileSfTv.visibility = View.GONE
        }
        binding.profileSfDeleteIv.setOnClickListener {
            genreList.remove("14")
            binding.profileSfColorTv.visibility = View.GONE
            binding.profileSfDeleteIv.visibility = View.INVISIBLE
            binding.profileSfTv.visibility = View.VISIBLE
        }
        binding.profileSportTv.setOnClickListener {
            genreList.add("15")
            binding.profileSportColorTv.visibility = View.VISIBLE
            binding.profileSportDeleteIv.visibility = View.VISIBLE
            binding.profileSportTv.visibility = View.GONE
        }
        binding.profileSportDeleteIv.setOnClickListener {
            genreList.remove("15")
            binding.profileSportColorTv.visibility = View.GONE
            binding.profileSportDeleteIv.visibility = View.INVISIBLE
            binding.profileSportTv.visibility = View.VISIBLE
        }
        binding.profileWarTv.setOnClickListener {
            genreList.add("16")
            binding.profileWarColorTv.visibility = View.VISIBLE
            binding.profileWarDeleteIv.visibility = View.VISIBLE
            binding.profileWarTv.visibility = View.GONE
        }
        binding.profileWarDeleteIv.setOnClickListener {
            genreList.remove("16")
            binding.profileWarColorTv.visibility = View.GONE
            binding.profileWarDeleteIv.visibility = View.INVISIBLE
            binding.profileWarTv.visibility = View.VISIBLE
        }
    }

    private fun ottVisibility(nowUserHasOttList: List<String>) {
        if (nowUserHasOttList.contains("1")) {
            binding.ottAppleTvIv.visibility = View.VISIBLE
            binding.ottAppleTvSelectIv.visibility = View.GONE
            binding.ottAppleTvSelectCancelIv.visibility = View.VISIBLE
            ottList.add("1")
        } else {
            binding.ottAppleTvSelectIv.visibility = View.VISIBLE
            binding.ottAppleTvIv.visibility = View.GONE
            binding.ottAppleTvSelectCancelIv.visibility = View.GONE
        }

        if (nowUserHasOttList.contains("2")) {
            binding.ottPrimeVideoIv.visibility = View.VISIBLE
            binding.ottPrimeVideoSelectIv.visibility = View.GONE
            binding.ottPrimeVideoSelectCancelIv.visibility = View.VISIBLE
            ottList.add("2")
        } else {
            binding.ottPrimeVideoIv.visibility = View.GONE
            binding.ottPrimeVideoSelectIv.visibility = View.VISIBLE
            binding.ottPrimeVideoSelectCancelIv.visibility = View.GONE
        }

        if (nowUserHasOttList.contains("3")) {
            binding.ottDisneyIv.visibility = View.VISIBLE
            binding.ottDisneySelectIv.visibility = View.GONE
            binding.ottDisneySelectCancelIv.visibility = View.VISIBLE
            ottList.add("3")
        } else {
            binding.ottDisneyIv.visibility = View.GONE
            binding.ottDisneySelectIv.visibility = View.VISIBLE
            binding.ottDisneySelectCancelIv.visibility = View.GONE

        }
        if (nowUserHasOttList.contains("4")) {
            binding.ottCoupangIv.visibility = View.VISIBLE
            binding.ottCoupangSelectIv.visibility = View.GONE
            binding.ottCoupangSelectCancelIv.visibility = View.VISIBLE
            ottList.add("4")
        } else {
            binding.ottCoupangIv.visibility = View.GONE
            binding.ottCoupangSelectIv.visibility = View.VISIBLE
            binding.ottCoupangSelectCancelIv.visibility = View.GONE

        }
        if (nowUserHasOttList.contains("5")) {
            binding.ottWavveIv.visibility = View.VISIBLE
            binding.ottWavveSelectIv.visibility = View.GONE
            binding.ottWavveSelectCancelIv.visibility = View.VISIBLE
            ottList.add("5")
        } else {
            binding.ottWavveIv.visibility = View.GONE
            binding.ottWavveSelectIv.visibility = View.VISIBLE
            binding.ottWavveSelectCancelIv.visibility = View.GONE

        }
        if (nowUserHasOttList.contains("6")) {
            binding.ottTvingIv.visibility = View.VISIBLE
            binding.ottTvingSelectIv.visibility = View.GONE
            binding.ottTvingSelectCancelIv.visibility = View.VISIBLE
            ottList.add("6")
        } else {
            binding.ottTvingIv.visibility = View.GONE
            binding.ottTvingSelectIv.visibility = View.VISIBLE
            binding.ottTvingSelectCancelIv.visibility = View.GONE

        }
        if (nowUserHasOttList.contains("7")) {
            binding.ottNetflixIv.visibility = View.VISIBLE
            binding.ottNetflixSelectIv.visibility = View.GONE
            binding.ottNetflixSelectCancelIv.visibility = View.VISIBLE
            ottList.add("7")
        } else {
            binding.ottNetflixIv.visibility = View.GONE
            binding.ottNetflixSelectIv.visibility = View.VISIBLE
            binding.ottNetflixSelectCancelIv.visibility = View.GONE
        }
        if (nowUserHasOttList.contains("8")) {
            binding.ottWhatchaIv.visibility = View.VISIBLE
            binding.ottWhatchaSelectIv.visibility = View.GONE
            binding.ottWhatchaSelectCancelIv.visibility = View.VISIBLE
            ottList.add("8")
        } else {
            binding.ottWhatchaIv.visibility = View.GONE
            binding.ottWhatchaSelectIv.visibility = View.VISIBLE
            binding.ottWhatchaSelectCancelIv.visibility = View.GONE
        }


        binding.ottAppleTvSelectCancelIv.setOnClickListener {
            binding.ottAppleTvIv.visibility = View.GONE
            binding.ottAppleTvSelectIv.visibility = View.VISIBLE
            binding.ottAppleTvSelectCancelIv.visibility = View.GONE
            ottList.remove("1")
        }
        binding.ottAppleTvSelectIv.setOnClickListener {
            binding.ottAppleTvIv.visibility = View.VISIBLE
            binding.ottAppleTvSelectIv.visibility = View.GONE
            binding.ottAppleTvSelectCancelIv.visibility = View.VISIBLE
            ottList.add("1")
        }

        binding.ottPrimeVideoSelectCancelIv.setOnClickListener {
            binding.ottPrimeVideoIv.visibility = View.GONE
            binding.ottPrimeVideoSelectIv.visibility = View.VISIBLE
            binding.ottPrimeVideoSelectCancelIv.visibility = View.GONE
            ottList.remove("2")
        }
        binding.ottPrimeVideoSelectIv.setOnClickListener {
            binding.ottPrimeVideoIv.visibility = View.VISIBLE
            binding.ottPrimeVideoSelectIv.visibility = View.GONE
            binding.ottPrimeVideoSelectCancelIv.visibility = View.VISIBLE
            ottList.add("2")
        }
        binding.ottDisneySelectCancelIv.setOnClickListener {
            binding.ottDisneyIv.visibility = View.GONE
            binding.ottDisneySelectIv.visibility = View.VISIBLE
            binding.ottDisneySelectCancelIv.visibility = View.GONE
            ottList.remove("3")
        }
        binding.ottDisneySelectIv.setOnClickListener {
            binding.ottDisneyIv.visibility = View.VISIBLE
            binding.ottDisneySelectIv.visibility = View.GONE
            binding.ottDisneySelectCancelIv.visibility = View.VISIBLE
            ottList.add("3")
        }

        binding.ottCoupangSelectCancelIv.setOnClickListener {
            binding.ottCoupangIv.visibility = View.GONE
            binding.ottCoupangSelectIv.visibility = View.VISIBLE
            binding.ottCoupangSelectCancelIv.visibility = View.GONE
            ottList.remove("4")
        }
        binding.ottCoupangSelectIv.setOnClickListener {
            binding.ottCoupangIv.visibility = View.VISIBLE
            binding.ottCoupangSelectIv.visibility = View.GONE
            binding.ottCoupangSelectCancelIv.visibility = View.VISIBLE
            ottList.add("4")
        }

        binding.ottWavveSelectCancelIv.setOnClickListener {
            binding.ottWavveIv.visibility = View.GONE
            binding.ottWavveSelectIv.visibility = View.VISIBLE
            binding.ottWavveSelectCancelIv.visibility = View.GONE
            ottList.remove("5")
        }

        binding.ottWavveSelectIv.setOnClickListener {
            ottList.add("5")
            binding.ottWavveIv.visibility = View.VISIBLE
            binding.ottWavveSelectIv.visibility = View.GONE
            binding.ottWavveSelectCancelIv.visibility = View.VISIBLE
        }

        binding.ottTvingSelectCancelIv.setOnClickListener {
            binding.ottTvingIv.visibility = View.GONE
            binding.ottTvingSelectIv.visibility = View.VISIBLE
            binding.ottTvingSelectCancelIv.visibility = View.GONE
            ottList.remove("6")
        }
        binding.ottTvingSelectIv.setOnClickListener {
            binding.ottTvingIv.visibility = View.VISIBLE
            binding.ottTvingSelectIv.visibility = View.GONE
            binding.ottTvingSelectCancelIv.visibility = View.VISIBLE
            ottList.add("6")
        }
        binding.ottNetflixSelectCancelIv.setOnClickListener {
            binding.ottNetflixIv.visibility = View.GONE
            binding.ottNetflixSelectIv.visibility = View.VISIBLE
            binding.ottNetflixSelectCancelIv.visibility = View.GONE
            ottList.remove("7")
        }
        binding.ottNetflixSelectIv.setOnClickListener {
            binding.ottNetflixIv.visibility = View.VISIBLE
            binding.ottNetflixSelectIv.visibility = View.GONE
            binding.ottNetflixSelectCancelIv.visibility = View.VISIBLE
            ottList.add("7")
        }
        binding.ottWhatchaSelectCancelIv.setOnClickListener {
            binding.ottWhatchaIv.visibility = View.GONE
            binding.ottWhatchaSelectIv.visibility = View.VISIBLE
            binding.ottWhatchaSelectCancelIv.visibility = View.GONE
            ottList.remove("8")
        }
        binding.ottWhatchaSelectIv.setOnClickListener {
            binding.ottWhatchaIv.visibility = View.VISIBLE
            binding.ottWhatchaSelectIv.visibility = View.GONE
            binding.ottWhatchaSelectCancelIv.visibility = View.VISIBLE
            ottList.add("8")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {

            var dataUri = data?.data
            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, dataUri)
            saveBitmapAsPNGFile(bitmap)
            binding.modifyImageIv.setImageBitmap(bitmap)


        } else if (resultCode == RESULT_OK && requestCode == 100) {


            val imageBitmap = data?.extras?.get("data") as Bitmap
            saveBitmapAsPNGFile(imageBitmap)
            binding.modifyImageIv.setImageBitmap(imageBitmap)
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

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("photo", file.absolutePath.toString())
            Log.d("Photo", file.absolutePath.toString())
            editor.putString("photoname", photoName)
            editor.commit()

        } catch (e: Exception) {
            null
        }
    }

    override fun onPutModifySuccess(response: ModifyResponse) {
        if (response.code == 3015) {
            Toast.makeText(this, "중복된 닉네임 입니다.", Toast.LENGTH_SHORT).show()
        } else {
            val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
            val sharedPreferences2 = getSharedPreferences("join", MODE_PRIVATE)
            val editor1 = sharedPreferences.edit()
            val editor2 = sharedPreferences2.edit()
            editor2.putString("servertoken",response.result!!.token)
            editor1.putInt("useridx",response.result!!.userIdx)
            editor1.commit()
            editor2.commit()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onPutModifyFailure(message: String) {
        Log.d("Modify-API", message.toString())
    }

    override fun onGetModifyProfileSuccess(response: ModifyProfileResponse) {
        var UserInfo = response.result!!
        binding.modifyNicknameInfoEt.setText(UserInfo.nickname)
        var ottListString = UserInfo.ottList.toString()
        var genreListString = UserInfo.genreList.toString()
        ottListString = ottListString.replace("[", "").replace("]", "").replace(" ", "")
        genreListString = genreListString.replace("[", "").replace("]", "").replace(" ", "")
        var nowUserHasOttList: List<String> = ottListString.split(",")
        var nowUserHasGenreList: List<String> = genreListString.split(",")
        ottVisibility(nowUserHasOttList)
        genreVisibility(nowUserHasGenreList)
        binding.modifyBirthInfoEt.text = UserInfo.birth


        Glide.with(this)
            .load(response.result.photo)
            .into(binding.modifyImageIv)


        if(UserInfo.is_male==1){
            binding.modifyGenderInfoEt.text = "남성"
        }
        else{
            binding.modifyGenderInfoEt.text = "여성"
        }

    }

    override fun onGetModifyProfileFailure(message: String) {
    }



}