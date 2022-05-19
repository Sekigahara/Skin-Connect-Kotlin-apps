package com.skinconnect.userapps.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.skinconnect.userapps.ui.helper.BaseActivity
import com.skinconnect.userapps.ui.helper.BaseView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), BaseView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupAction()
    }

    override fun setupViewModel() {}

    override fun setupAction() {
        lifecycleScope.launch(Dispatchers.Default) {
             delay(1000)

            withContext(Dispatchers.Main) {
                val intent = Intent(this@SplashActivity, BaseActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun setupView() {}
}