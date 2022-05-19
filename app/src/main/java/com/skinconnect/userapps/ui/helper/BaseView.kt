package com.skinconnect.userapps.ui.helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.skinconnect.userapps.databinding.ActivityHostBinding

interface BaseView {
    fun setupView()
    fun setupViewModel()
    fun setupAction()
}

class BaseActivity : AppCompatActivity() {
    private var viewBinding: ViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHostBinding.inflate(layoutInflater)
        setContentView((viewBinding as ActivityHostBinding).root)
        if (savedInstanceState != null) return
        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}

abstract class BaseFragment : Fragment(), BaseView {
    protected lateinit var viewModel: ViewModel
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!
}