package com.example.corn_farmer.src.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.corn_farmer.src.profile.model.ProfileResponse
import com.example.cornfarmer_android.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(),ProfileFragmentView {

    lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        val service = ProfileService(this)
        service.tryGetProfile()

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        var gender = sharedPreferences?.getString("isMale",null)
        if(gender == "true"){
            binding.profileGenderInfoTv.text = "남자"
        }
        else{
            binding.profileGenderInfoTv.text = "여자"
        }

        val birth = sharedPreferences?.getString("birthday",null)
        binding.profileBirthInfoTv.text = birth


        //수정할 때 닉네임이랑 사진 다시 하기

        val nickname = sharedPreferences?.getString("nickname",null)
        binding.profileNicknameInfoTv.text = nickname

        val photo = sharedPreferences?.getString("photo",null)
        binding.profileImageIv.setImageURI(Uri.parse(photo))

        return binding.root

    }

    override fun onGetProfileSuccess(response: ProfileResponse) {
        Log.d("Profile",response.toString())
        val ott = response.result.ottList
        val profileRVAdapter = ProfileRVAdapter(ott)
        binding.profileRc1.layoutManager = GridLayoutManager(requireContext(),5)
        binding.profileRc1.adapter = profileRVAdapter

        val genre = response.result.genreList
        val profileGenreRVAdapter = ProfileGenreRVAdapter(genre)
        binding.profileRc2.layoutManager = GridLayoutManager(requireContext(),3)
        binding.profileRc2.adapter = profileGenreRVAdapter

        var nick = response.result.nickname

    }

    override fun onGetProfileFailure(message: String) {
        Log.d("Profile","프로필 실패")
    }
}