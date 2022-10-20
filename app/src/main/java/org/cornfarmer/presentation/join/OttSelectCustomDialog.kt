package org.cornfarmer.presentation.join

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.corn.cornfarmer_android.databinding.OttSelectCustomDialogBinding

class OttSelectCustomDialog(context: Context) : Dialog(context) {
    lateinit var binding: OttSelectCustomDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = OttSelectCustomDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
