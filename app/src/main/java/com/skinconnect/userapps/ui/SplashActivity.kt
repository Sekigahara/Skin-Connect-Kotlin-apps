package com.skinconnect.userapps.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.skinconnect.userapps.BuildConfig
import com.skinconnect.userapps.ui.auth.AuthActivity
import com.skinconnect.userapps.ui.auth.AuthViewModel
import com.skinconnect.userapps.ui.helper.BaseView
import com.skinconnect.userapps.ui.helper.ViewModelFactory
import com.skinconnect.userapps.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), BaseView {
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupAction()
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(this)
        val viewModel: AuthViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        lifecycleScope.launch(Dispatchers.Default) {
            delay(1000)

            withContext(Dispatchers.Main) {
                viewModel.getUserToken().observe(this@SplashActivity) {
                    if (BuildConfig.DEBUG) {
                        Log.e("SplashActivity", "USER TOKEN: $it")

                        viewModel.getUserId().observe(this@SplashActivity) { id ->
                            Log.e("SplashActivity", "USER ID: $id")
                        }
                    }

                    val intent =
                        if (it.isNullOrBlank() || it.isNullOrEmpty()) Intent(this@SplashActivity,
                            AuthActivity::class.java) else Intent(this@SplashActivity,
                            MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun setupView() {}
}