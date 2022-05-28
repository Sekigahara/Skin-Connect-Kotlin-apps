package com.skinconnect.userapps.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.local.UserPreferences
import com.skinconnect.userapps.databinding.FragmentHomeBinding
import com.skinconnect.userapps.databinding.FragmentScheduleBinding
import com.skinconnect.userapps.ui.auth.LoginViewModel
import com.skinconnect.userapps.ui.checkup.CheckupActivity
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ScheduleFragment : BaseFragment() {

    private lateinit var _username : String
    private lateinit var _tvDate : String
    private lateinit var _backButton : ImageButton
    private lateinit var preference: UserPreferences

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding as FragmentScheduleBinding
        preference = UserPreferences.getInstance(requireContext().dataStore)

        binding.rvSchedule.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentScheduleBinding
        _username = binding.tvGreetingUser.toString()
        _tvDate = binding.tvDate.toString()
        _backButton = binding.buttonBackSchedule
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: LoginViewModel by viewModels { factory }
        this.viewModel = viewModel
    }

    override fun setupAction() {
        val binding = binding as FragmentScheduleBinding

        binding.apply {
            _backButton.setOnClickListener {
                val intent = Intent(context, HomeFragment::class.java)
                startActivity(intent)
            }
        }
    }
}