package com.skinconnect.userapps.ui.checkup

import android.os.Bundle
import com.skinconnect.userapps.databinding.ActivityCheckupBinding
import com.skinconnect.userapps.ui.helper.BaseActivity

class CheckupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityCheckupBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
    }

    override fun setupView() { supportActionBar?.hide() }
}