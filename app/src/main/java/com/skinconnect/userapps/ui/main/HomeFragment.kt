package com.skinconnect.userapps.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skinconnect.userapps.databinding.FragmentHomeBinding
import com.skinconnect.userapps.ui.auth.LoginViewModel
import com.skinconnect.userapps.ui.checkup.CheckupActivity
import com.skinconnect.userapps.ui.discover.DiscoverActivity
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory

class HomeFragment : BaseFragment() {

    private lateinit var _tvDate : String
    private lateinit var _fabDoctorsFav: FloatingActionButton
    private lateinit var _tvDailyGoal:String
    private lateinit var _btnDiscover: Button
    private lateinit var _btnCheckup: Button

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
     /*   val binding =  binding as FragmentHomeBinding
        val dayToday = _tvDate
        binding.apply {
            tvDate.text =getString(R.string.dayToday,dayToday.withDateFormat())
        }

      */
    }

    override fun setupView() {
        val binding =  binding as FragmentHomeBinding
        _tvDate = binding.tvDate.toString()
        _fabDoctorsFav = binding.fabDoctorsFav
        _tvDailyGoal = binding.tvDailyGoal.toString()
        _btnDiscover = binding.btnDiscover
        _btnCheckup = binding.btnCheckup
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: LoginViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        val binding =  binding as FragmentHomeBinding
        binding.apply {
            //dailygoal = waiting for schedule math

            btnCheckup.setOnClickListener {
                val intent = Intent(context,CheckupActivity::class.java)
                startActivity(intent)
            }
            btnDiscover.setOnClickListener {
                val intent = Intent(context,DiscoverActivity::class.java)
                startActivity(intent)
            }
        }
    }
}