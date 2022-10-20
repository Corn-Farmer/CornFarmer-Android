package org.cornfarmer.presentation.profilemodify

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityProfileModifyBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.cornfarmer.data.model.response.ModifyProfileResponse
import org.cornfarmer.data.model.response.ModifyResponse
import org.cornfarmer.data.repository.ModifyProfileService
import org.cornfarmer.data.repository.ModifyService
import org.cornfarmer.data.view.ModifyView
import org.cornfarmer.di.Application
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.util.ext.showToast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat

class ProfileModifyActivity : AppCompatActivity(), ModifyView {

    private lateinit var binding: ActivityProfileModifyBinding

    var ottList = mutableListOf<String>()
    var genreList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_modify)

        val sharedPreferences = getSharedPreferences("join", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        val userIdx: Int = sharedPreferences2.getInt("useridx", 0)

        val serverToken = sharedPreferences.getString("servertoken", "")

        val service = ModifyProfileService(this, userIdx, serverToken)
        service.tryGetModifyProfile()

        binding.ivDelete.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("photo", "noimage")
            editor.apply()

            binding.ivImage.setImageResource(R.drawable.cornfarmerprofile)
        }

        binding.ivCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)
            startActivityForResult(takePictureIntent, 100)
        }

        binding.ivDelete.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 200)
        }

        binding.ivCancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.ivComplete.setOnClickListener { // 완료버튼 눌렀을 때
            val photo = sharedPreferences.getString("photo", "noimage")

            val modifiedNickname: String = binding.etNickname.text.toString()

            val nicknameRequest =
                RequestBody.create(MediaType.parse("text/plain"), modifiedNickname)

            val ottListRequest =
                RequestBody.create(
                    MediaType.parse("text/plain"),
                    ottList.toString().replace("[", "").replace("]", "")
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

            if (photo == "noimage") {
                val requestFile = RequestBody.create(MediaType.parse("image/png"), "noimage")
                val body = MultipartBody.Part.createFormData("photo", "noimage", requestFile)
                val service = ModifyService(this, servertoken.toString(), body, requestMap, userIdx)
                service.tryPutModify()
            } else {
                val file = File(photo.toString())
                val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
                val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                val service = ModifyService(this, servertoken.toString(), body, requestMap, userIdx)
                service.tryPutModify()
            }
        }
    }

    private fun genreVisibility(nowUserHasGenreList: List<String>) {
        if (nowUserHasGenreList.contains("1")) {
            genreList.add("1")
            binding.ivActionColor.visibility = View.VISIBLE
            binding.ivActionDelete.visibility = View.VISIBLE
            binding.ivAction.visibility = View.GONE
        } else {
            binding.ivActionColor.visibility = View.GONE
            binding.ivActionDelete.visibility = View.INVISIBLE
            binding.ivAction.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("2")) {
            genreList.add("2")
            binding.tvAnimeColor.visibility = View.VISIBLE
            binding.ivAnimeDelete.visibility = View.VISIBLE
            binding.tvAnime.visibility = View.GONE
        } else {
            binding.tvAnimeColor.visibility = View.GONE
            binding.ivAnimeDelete.visibility = View.INVISIBLE
            binding.tvAnime.visibility = View.VISIBLE
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
            binding.tvThrillColor.visibility = View.VISIBLE
            binding.ivThrillDelete.visibility = View.VISIBLE
            binding.tvThrill.visibility = View.GONE
        } else {
            binding.tvThrillColor.visibility = View.GONE
            binding.ivThrillDelete.visibility = View.INVISIBLE
            binding.tvThrill.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("13")) {
            genreList.add("13")
            binding.tvRomanceColor.visibility = View.VISIBLE
            binding.ivRomanceDelete.visibility = View.VISIBLE
            binding.tvRomance.visibility = View.GONE
        } else {
            binding.tvRomanceColor.visibility = View.GONE
            binding.ivRomanceDelete.visibility = View.INVISIBLE
            binding.tvRomance.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("14")) {
            genreList.add("14")
            binding.tvSfColor.visibility = View.VISIBLE
            binding.ivSfDelete.visibility = View.VISIBLE
            binding.tvSf.visibility = View.GONE
        } else {
            binding.tvSfColor.visibility = View.GONE
            binding.ivSfDelete.visibility = View.INVISIBLE
            binding.tvSf.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("15")) {
            genreList.add("15")
            binding.ivSportColor.visibility = View.VISIBLE
            binding.ivSportDelete.visibility = View.VISIBLE
            binding.tvSport.visibility = View.GONE
        } else {
            binding.ivSportColor.visibility = View.GONE
            binding.ivSportDelete.visibility = View.INVISIBLE
            binding.tvSport.visibility = View.VISIBLE
        }

        if (nowUserHasGenreList.contains("16")) {
            genreList.add("16")
            binding.tvWarColor.visibility = View.VISIBLE
            binding.ivWarDelete.visibility = View.VISIBLE
            binding.tvWar.visibility = View.GONE
        } else {
            binding.tvWarColor.visibility = View.GONE
            binding.ivWarDelete.visibility = View.INVISIBLE
            binding.tvWar.visibility = View.VISIBLE
        }

        binding.ivAction.setOnClickListener {
            genreList.add("1")
            binding.ivActionColor.visibility = View.VISIBLE
            binding.ivActionDelete.visibility = View.VISIBLE
            binding.ivAction.visibility = View.GONE
        }
        binding.ivActionDelete.setOnClickListener {
            binding.ivActionColor.visibility = View.GONE
            binding.ivActionDelete.visibility = View.INVISIBLE
            binding.ivAction.visibility = View.VISIBLE
            genreList.remove("1")
        }
        binding.tvAnime.setOnClickListener {
            genreList.add("2")
            binding.tvAnimeColor.visibility = View.VISIBLE
            binding.ivAnimeDelete.visibility = View.VISIBLE
            binding.tvAnime.visibility = View.GONE
        }
        binding.ivAnimeDelete.setOnClickListener {
            genreList.remove("2")
            binding.tvAnimeColor.visibility = View.GONE
            binding.ivAnimeDelete.visibility = View.INVISIBLE
            binding.tvAnime.visibility = View.VISIBLE
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
        binding.tvThrill.setOnClickListener {
            genreList.add("12")
            binding.tvThrillColor.visibility = View.VISIBLE
            binding.ivThrillDelete.visibility = View.VISIBLE
            binding.tvThrill.visibility = View.GONE
        }
        binding.ivThrillDelete.setOnClickListener {
            genreList.remove("12")
            binding.tvThrillColor.visibility = View.GONE
            binding.ivThrillDelete.visibility = View.INVISIBLE
            binding.tvThrill.visibility = View.VISIBLE
        }
        binding.tvRomance.setOnClickListener {
            genreList.add("13")
            binding.tvRomanceColor.visibility = View.VISIBLE
            binding.ivRomanceDelete.visibility = View.VISIBLE
            binding.tvRomance.visibility = View.GONE
        }
        binding.ivRomanceDelete.setOnClickListener {
            genreList.remove("13")
            binding.tvRomanceColor.visibility = View.GONE
            binding.ivRomanceDelete.visibility = View.INVISIBLE
            binding.tvRomance.visibility = View.VISIBLE
        }
        binding.tvSf.setOnClickListener {
            genreList.add("14")
            binding.tvSfColor.visibility = View.VISIBLE
            binding.ivSfDelete.visibility = View.VISIBLE
            binding.tvSf.visibility = View.GONE
        }
        binding.ivSfDelete.setOnClickListener {
            genreList.remove("14")
            binding.tvSfColor.visibility = View.GONE
            binding.ivSfDelete.visibility = View.INVISIBLE
            binding.tvSf.visibility = View.VISIBLE
        }
        binding.tvSport.setOnClickListener {
            genreList.add("15")
            binding.ivSportColor.visibility = View.VISIBLE
            binding.ivSportDelete.visibility = View.VISIBLE
            binding.tvSport.visibility = View.GONE
        }
        binding.ivSportDelete.setOnClickListener {
            genreList.remove("15")
            binding.ivSportColor.visibility = View.GONE
            binding.ivSportDelete.visibility = View.INVISIBLE
            binding.tvSport.visibility = View.VISIBLE
        }
        binding.tvWar.setOnClickListener {
            genreList.add("16")
            binding.tvWarColor.visibility = View.VISIBLE
            binding.ivWarDelete.visibility = View.VISIBLE
            binding.tvWar.visibility = View.GONE
        }
        binding.ivWarDelete.setOnClickListener {
            genreList.remove("16")
            binding.tvWarColor.visibility = View.GONE
            binding.ivWarDelete.visibility = View.INVISIBLE
            binding.tvWar.visibility = View.VISIBLE
        }
    }

    private fun ottVisibility(nowUserHasOttList: List<String>) {
        if (nowUserHasOttList.contains("1")) {
            binding.ivApple.visibility = View.VISIBLE
            binding.ivAppleColor.visibility = View.GONE
            binding.ivAppleCancel.visibility = View.VISIBLE
            ottList.add("1")
        } else {
            binding.ivAppleColor.visibility = View.VISIBLE
            binding.ivApple.visibility = View.GONE
            binding.ivAppleCancel.visibility = View.GONE
        }

        if (nowUserHasOttList.contains("2")) {
            binding.ivPrimeVideo.visibility = View.VISIBLE
            binding.ivPrimeVideoColor.visibility = View.GONE
            binding.ivPrimeCancel.visibility = View.VISIBLE
            ottList.add("2")
        } else {
            binding.ivPrimeVideo.visibility = View.GONE
            binding.ivPrimeVideoColor.visibility = View.VISIBLE
            binding.ivPrimeCancel.visibility = View.GONE
        }

        if (nowUserHasOttList.contains("3")) {
            binding.ivDisney.visibility = View.VISIBLE
            binding.ivDisneyColor.visibility = View.GONE
            binding.ivDisneyCancel.visibility = View.VISIBLE
            ottList.add("3")
        } else {
            binding.ivDisney.visibility = View.GONE
            binding.ivDisneyColor.visibility = View.VISIBLE
            binding.ivDisneyCancel.visibility = View.GONE
        }
        if (nowUserHasOttList.contains("4")) {
            binding.ivCoupang.visibility = View.VISIBLE
            binding.ivCoupangColor.visibility = View.GONE
            binding.ivCoupangCancel.visibility = View.VISIBLE
            ottList.add("4")
        } else {
            binding.ivCoupang.visibility = View.GONE
            binding.ivCoupangColor.visibility = View.VISIBLE
            binding.ivCoupangCancel.visibility = View.GONE
        }
        if (nowUserHasOttList.contains("5")) {
            binding.ivWavve.visibility = View.VISIBLE
            binding.ivWavveColor.visibility = View.GONE
            binding.ivWavveCancel.visibility = View.VISIBLE
            ottList.add("5")
        } else {
            binding.ivWavve.visibility = View.GONE
            binding.ivWavveColor.visibility = View.VISIBLE
            binding.ivWavveCancel.visibility = View.GONE
        }
        if (nowUserHasOttList.contains("6")) {
            binding.ivTving.visibility = View.VISIBLE
            binding.ivTvingColor.visibility = View.GONE
            binding.ivTvingCancel.visibility = View.VISIBLE
            ottList.add("6")
        } else {
            binding.ivTving.visibility = View.GONE
            binding.ivTvingColor.visibility = View.VISIBLE
            binding.ivTvingCancel.visibility = View.GONE
        }
        if (nowUserHasOttList.contains("7")) {
            binding.ivNetflix.visibility = View.VISIBLE
            binding.ivNetflixColor.visibility = View.GONE
            binding.ivNexflixCancel.visibility = View.VISIBLE
            ottList.add("7")
        } else {
            binding.ivNetflix.visibility = View.GONE
            binding.ivNetflixColor.visibility = View.VISIBLE
            binding.ivNexflixCancel.visibility = View.GONE
        }
        if (nowUserHasOttList.contains("8")) {
            binding.ivWhatcha.visibility = View.VISIBLE
            binding.ivWhatchaColor.visibility = View.GONE
            binding.ivWhatchaCancel.visibility = View.VISIBLE
            ottList.add("8")
        } else {
            binding.ivWhatcha.visibility = View.GONE
            binding.ivWhatchaColor.visibility = View.VISIBLE
            binding.ivWhatchaCancel.visibility = View.GONE
        }

        binding.ivAppleCancel.setOnClickListener {
            binding.ivApple.visibility = View.GONE
            binding.ivAppleColor.visibility = View.VISIBLE
            binding.ivAppleCancel.visibility = View.GONE
            ottList.remove("1")
        }
        binding.ivAppleColor.setOnClickListener {
            binding.ivApple.visibility = View.VISIBLE
            binding.ivAppleColor.visibility = View.GONE
            binding.ivAppleCancel.visibility = View.VISIBLE
            ottList.add("1")
        }

        binding.ivPrimeCancel.setOnClickListener {
            binding.ivPrimeVideo.visibility = View.GONE
            binding.ivPrimeVideoColor.visibility = View.VISIBLE
            binding.ivPrimeCancel.visibility = View.GONE
            ottList.remove("2")
        }
        binding.ivPrimeVideoColor.setOnClickListener {
            binding.ivPrimeVideo.visibility = View.VISIBLE
            binding.ivPrimeVideoColor.visibility = View.GONE
            binding.ivPrimeCancel.visibility = View.VISIBLE
            ottList.add("2")
        }
        binding.ivDisneyCancel.setOnClickListener {
            binding.ivDisney.visibility = View.GONE
            binding.ivDisneyColor.visibility = View.VISIBLE
            binding.ivDisneyCancel.visibility = View.GONE
            ottList.remove("3")
        }
        binding.ivDisneyColor.setOnClickListener {
            binding.ivDisney.visibility = View.VISIBLE
            binding.ivDisneyColor.visibility = View.GONE
            binding.ivDisneyCancel.visibility = View.VISIBLE
            ottList.add("3")
        }

        binding.ivCoupangCancel.setOnClickListener {
            binding.ivCoupang.visibility = View.GONE
            binding.ivCoupangColor.visibility = View.VISIBLE
            binding.ivCoupangCancel.visibility = View.GONE
            ottList.remove("4")
        }
        binding.ivCoupangColor.setOnClickListener {
            binding.ivCoupang.visibility = View.VISIBLE
            binding.ivCoupangColor.visibility = View.GONE
            binding.ivCoupangCancel.visibility = View.VISIBLE
            ottList.add("4")
        }

        binding.ivWavveCancel.setOnClickListener {
            binding.ivWavve.visibility = View.GONE
            binding.ivWavveColor.visibility = View.VISIBLE
            binding.ivWavveCancel.visibility = View.GONE
            ottList.remove("5")
        }

        binding.ivWavveColor.setOnClickListener {
            ottList.add("5")
            binding.ivWavve.visibility = View.VISIBLE
            binding.ivWavveColor.visibility = View.GONE
            binding.ivWavveCancel.visibility = View.VISIBLE
        }

        binding.ivTvingCancel.setOnClickListener {
            binding.ivTving.visibility = View.GONE
            binding.ivTvingColor.visibility = View.VISIBLE
            binding.ivTvingCancel.visibility = View.GONE
            ottList.remove("6")
        }
        binding.ivTvingColor.setOnClickListener {
            binding.ivTving.visibility = View.VISIBLE
            binding.ivTvingColor.visibility = View.GONE
            binding.ivTvingCancel.visibility = View.VISIBLE
            ottList.add("6")
        }
        binding.ivNexflixCancel.setOnClickListener {
            binding.ivNetflix.visibility = View.GONE
            binding.ivNetflixColor.visibility = View.VISIBLE
            binding.ivNexflixCancel.visibility = View.GONE
            ottList.remove("7")
        }
        binding.ivNetflixColor.setOnClickListener {
            binding.ivNetflix.visibility = View.VISIBLE
            binding.ivNetflixColor.visibility = View.GONE
            binding.ivNexflixCancel.visibility = View.VISIBLE
            ottList.add("7")
        }
        binding.ivWhatchaCancel.setOnClickListener {
            binding.ivWhatcha.visibility = View.GONE
            binding.ivWhatchaColor.visibility = View.VISIBLE
            binding.ivWhatchaCancel.visibility = View.GONE
            ottList.remove("8")
        }
        binding.ivWhatchaColor.setOnClickListener {
            binding.ivWhatcha.visibility = View.VISIBLE
            binding.ivWhatchaColor.visibility = View.GONE
            binding.ivWhatchaCancel.visibility = View.VISIBLE
            ottList.add("8")
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
        val imageFile: OutputStream?
        try {
            file.createNewFile()
            imageFile = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageFile)
            imageFile.close()

            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("photo", file.absolutePath.toString())
            Log.d("Photo", file.absolutePath.toString())
            editor.putString("photoname", photoName)
            editor.apply()
        } catch (e: Exception) {
            null
        }
    }

    override fun onPutModifySuccess(response: ModifyResponse) {
        if (response.code == 3015) {
            showToast("중복된 닉네임 입니다.")
        } else {
            val sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE)
            val sharedPreferences2 = Application.joinSharedPreferences
            val editor1 = sharedPreferences.edit()
            val editor2 = sharedPreferences2.edit()
            editor2.putString("servertoken", response.result!!.token)
            editor1.putInt("useridx", response.result.userIdx)
            editor1.apply()
            editor2.apply()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onPutModifyFailure(message: String) {
        Log.d("Modify-API", message)
    }

    override fun onGetModifyProfileSuccess(response: ModifyProfileResponse) {
        val userInfo = response.result
        binding.etNickname.setText(userInfo.nickname)
        var ottListString = userInfo.ottList.toString()
        var genreListString = userInfo.genreList.toString()
        ottListString = ottListString.replace("[", "").replace("]", "").replace(" ", "")
        genreListString = genreListString.replace("[", "").replace("]", "").replace(" ", "")
        val nowUserHasOttList: List<String> = ottListString.split(",")
        val nowUserHasGenreList: List<String> = genreListString.split(",")
        ottVisibility(nowUserHasOttList)
        genreVisibility(nowUserHasGenreList)
        binding.etBirthday.text = userInfo.birth

        Glide.with(this)
            .load(response.result.photo)
            .into(binding.ivImage)

        if (userInfo.is_male == 1) {
            binding.etSex.text = "남성"
        } else {
            binding.etSex.text = "여성"
        }
    }

    override fun onGetModifyProfileFailure(message: String) {
    }
}
