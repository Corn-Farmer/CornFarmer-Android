package com.corn.corn_farmer.src.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.corn.cornfarmer_android.databinding.CustomDialogUserDeleteBinding

class CustomUserDeleteDialog(context : Context) : Dialog(context){
    private lateinit var binding : CustomDialogUserDeleteBinding
    private val dialog = Dialog(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomDialogUserDeleteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }



}