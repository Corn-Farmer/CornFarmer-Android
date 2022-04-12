package com.corn.corn_farmer.src.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.src.kakao.LoginActivity
import com.corn.corn_farmer.src.loading.CustomLoadingDialog
import com.corn.corn_farmer.src.my_comment.MyCommentFragment
import com.corn.corn_farmer.src.profile.model.DeleteResponse
import com.corn.corn_farmer.src.profile.model.ProfileResponse
import com.corn.corn_farmer.src.profile_modify.ProfileModifyActivity
import com.corn.corn_farmer.src.search.SearchFragment
import com.corn.corn_farmer.src.wishlist.WishlistActivity
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentProfileBinding
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin

class ProfileFragment : Fragment(), ProfileFragmentView, DeleteView {

    private lateinit var binding: FragmentProfileBinding

    lateinit var mOAuthLoginInstance : OAuthLogin
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val naver_client_id = "NfLkiZ5opr9o5hdqRweQ"
        val naver_client_secret = "nkWklfMmjm"
        val naver_client_name = "cornfarmer"

        mContext = requireContext()

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(mContext, naver_client_id, naver_client_secret, naver_client_name)

        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        }, 500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val sharedPreferences2 =
            this.activity?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)

        var userIdx = sharedPreferences2?.getInt("useridx", 0)
        var serverToken = sharedPreferences?.getString("servertoken", "")
        val service = ProfileService(this, userIdx, serverToken)
        service.tryGetProfile()


        val mActivity = activity as MainActivity
        binding.profileSearchIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }



        binding.profileReIv.setOnClickListener {
            val intent = Intent(requireContext(), ProfileModifyActivity::class.java)
            startActivity(intent)
        }

        binding.profileDeleteIv.setOnClickListener {
            showDialog()
        }
        binding.profileLikeIv.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        return binding.root

    }

    private fun showDialog() {
        val dialog = CustomUserDeleteDialog(requireContext())
        dialog.show()


        dialog.findViewById<Button>(R.id.yesBtn)?.setOnClickListener {
            val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val sharedPreferences2 =
                this.activity?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)

            var userIdx = sharedPreferences2?.getInt("useridx", 0)
            var serverToken = sharedPreferences?.getString("servertoken", "")

            val deleteService = DeleteService(this,userIdx!!,serverToken!!)
            deleteService.tryPutDeleteUser()
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.noBtn)?.setOnClickListener {
            dialog.dismiss()
        }

    }

    override fun onGetProfileSuccess(response: ProfileResponse) {

        if(response.code == 4000){
            Toast.makeText(this.activity, "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val ott = response.result.ottList //서버에서 받아옴
        val profileRVAdapter = ProfileRVAdapter(ott)
        binding.profileRc1.layoutManager = GridLayoutManager(requireContext(), 5)
        binding.profileRc1.adapter = profileRVAdapter

        val genre = response.result.genreList //서버에서 받아옴
        val profileGenreRVAdapter = ProfileGenreRVAdapter(genre)
        binding.profileRc2.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.profileRc2.adapter = profileGenreRVAdapter

        var nick = response.result.nickname //닉네임
        binding.profileNicknameInfoTv.text = nick

        var gender = response.result.is_male
        var birth = response.result.birth
        if(gender==1){
            binding.profileGenderInfoTv.text = "남성"
        }
        else{
            binding.profileGenderInfoTv.text = "여성"
        }
        binding.profileBirthInfoTv.text = birth

        val mActivity = activity as MainActivity //메인 액티비티

        binding.profileCommentIv.setOnClickListener {
            mActivity.callFragment(MyCommentFragment(nick))
        }

        Glide.with(this)
            .load(response.result.photo)
            .into(binding.profileImageIv)

    }

    override fun onGetProfileFailure(message: String) {
        Log.d("Profile", "프로필 실패")
    }

    override fun onPutDeleteSuccess(response: DeleteResponse) {



        val getSharedPreferences3 = this.activity?.getSharedPreferences("token", Context.MODE_PRIVATE)

        val navertoken = getSharedPreferences3!!.getString("navertoken", null)
        val kakaotoken = getSharedPreferences3!!.getString("kakaotoken", null)

        if(navertoken == null){
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("카카오토큰 삭제 실패", "연결 끊기 실패", error)
                } else {
                    Log.i("토큰 삭제 성공", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
            Log.d("회원탈퇴","회원탈퇴 성공")
            val getSharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val getSharedPreferences2 = this.activity?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
            val editor1 = getSharedPreferences?.edit()
            val editor2 = getSharedPreferences2?.edit()
            val editor3 = getSharedPreferences3?.edit()
            editor1?.clear()
            editor2?.clear()
            editor3?.putString("kakaotoken",null)

            editor1?.commit()
            editor2?.commit()
            editor3?.commit()

            editor1?.putString("servertoken","")
            editor1?.commit()

            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }else if(kakaotoken == null){

            mOAuthLoginInstance.logoutAndDeleteToken(mContext)

            Log.d("회원탈퇴","회원탈퇴 성공")

            val getSharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
            val getSharedPreferences2 = this.activity?.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
            val editor1 = getSharedPreferences?.edit()
            val editor2 = getSharedPreferences2?.edit()
            val editor3 = getSharedPreferences3?.edit()
            editor1?.clear()
            editor2?.clear()
            editor3?.putString("navertoken",null)

            editor1?.commit()
            editor2?.commit()
            editor3?.commit()

            editor1?.putString("servertoken","")
            editor1?.commit()

            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()

        }
    }

    override fun onPutDeleteFailure(message: String) {
        Log.d("회원탈퇴","회원탈퇴 실패")
    }


}