package com.example.corn_farmer.src.loading

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.CustomLoadingDialogBinding

class CustomLoadingDialog(context: Context):Dialog(context) {

    lateinit var turnAround : Animation
    lateinit var binding : CustomLoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomLoadingDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        turnAround = AnimationUtils.loadAnimation(context,R.anim.turn_around)
        binding.loadingImg.startAnimation(turnAround)


        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}