package com.example.corn_farmer.src.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.kakao.LoginActivity
import com.example.corn_farmer.src.loading.CustomLoadingDialog
import com.example.corn_farmer.src.my_comment.MyCommentFragment
import com.example.corn_farmer.src.profile.model.DeleteResponse
import com.example.corn_farmer.src.profile.model.ProfileResponse
import com.example.corn_farmer.src.profile_modify.ProfileModifyActivity
import com.example.corn_farmer.src.search.SearchFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(),ProfileFragmentView {

    lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        },500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val sharedPreferences2 =
            this.activity?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)

        var userIdx = sharedPreferences2?.getInt("useridx", 0)
        var serverToken = sharedPreferences?.getString("servertoken", null)
        val service = ProfileService(this, userIdx, serverToken)
        service.tryGetProfile()

        var gender = sharedPreferences?.getString("isMale", null)
        var gender2 = sharedPreferences?.getString("isFemale", null)
        if (gender == "true") {
            binding.profileGenderInfoTv.text = "남자"
            Log.d("man", "남자")
        } else if (gender2 == "false") {
            binding.profileGenderInfoTv.text = "여자"
            Log.d("woman", "여자")
        }
        val mActivity = activity as MainActivity
        binding.profileSearchIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        val birth = sharedPreferences?.getString("birthday", null)
        binding.profileBirthInfoTv.text = birth


        //수정할 때 닉네임이랑 사진 다시 하기


//        val photo = sharedPreferences?.getString("photo", null)
//        binding.profileImageIv.setImageURI(Uri.parse(photo))
//        Log.d("photo", photo.toString())


        binding.profileReIv.setOnClickListener {
            val intent = Intent(requireContext(), ProfileModifyActivity::class.java)
            startActivity(intent)
        }

        binding.profileDeleteIv.setOnClickListener {
            showDialog()
        }

        return binding.root

    }
    private fun showDialog() {
        val dialog = CustomUserDeleteDialog(requireContext())
        dialog.show()


            dialog.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
        }

        dialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onGetProfileSuccess(response: ProfileResponse) {
        Log.d("Profile", response.toString())
        val ott = response.result.ottList
        val profileRVAdapter = ProfileRVAdapter(ott)
        binding.profileRc1.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.profileRc1.adapter = profileRVAdapter

        val genre = response.result.genreList
        val profileGenreRVAdapter = ProfileGenreRVAdapter(genre)
        binding.profileRc2.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.profileRc2.adapter = profileGenreRVAdapter

        var nick = response.result.nickname
        binding.profileNicknameInfoTv.text = nick


        binding.profileCommentIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, MyCommentFragment(nick))
                .commitAllowingStateLoss()
        }

        Glide.with(this)
            .load(response.result.photo)
            .into(binding.profileImageIv)

        val sharedPreferences3 = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val editor = sharedPreferences3?.edit()
        editor?.putString("nickname",nick)
        editor?.commit()


    }

    override fun onGetProfileFailure(message: String) {
        Log.d("Profile", "프로필 실패")
    }
}