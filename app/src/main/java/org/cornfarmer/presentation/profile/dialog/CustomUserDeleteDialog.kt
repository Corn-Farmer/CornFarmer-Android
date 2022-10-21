package org.cornfarmer.presentation.profile.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import org.cornfarmer.databinding.CustomDialogUserDeleteBinding

class CustomUserDeleteDialog(context: Context) : Dialog(context) {
    private lateinit var binding: CustomDialogUserDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = CustomDialogUserDeleteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
