package org.cornfarmer.presentation.loading

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import org.cornfarmer.R
import org.cornfarmer.databinding.CustomDialogLoadingBinding

class CustomLoadingDialog(context: Context) : Dialog(context) {

    lateinit var turnAround: Animation
    lateinit var binding: CustomDialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomDialogLoadingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        turnAround = AnimationUtils.loadAnimation(context, R.anim.turn_around)
        binding.ivLoading.startAnimation(turnAround)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
