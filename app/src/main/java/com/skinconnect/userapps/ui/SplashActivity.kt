package com.skinconnect.userapps.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.skinconnect.userapps.ui.auth.AuthActivity
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
                    val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun setupView() {}
}