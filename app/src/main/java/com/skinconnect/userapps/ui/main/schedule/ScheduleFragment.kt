package com.skinconnect.userapps.ui.main.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skinconnect.userapps.data.entity.Schedule
import com.skinconnect.userapps.data.entity.response.ScheduleResponse
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.FragmentScheduleBinding
import com.skinconnect.userapps.ui.auth.AuthViewModel
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory

class ScheduleFragment : BaseFragment() {
    private lateinit var _username: String
    private lateinit var _tvDate: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding as FragmentScheduleBinding
        binding.rvSchedule.apply { layoutManager = LinearLayoutManager(requireContext()) }
        setupViewModel()
        setupView()
        setupAction()
    }

    override fun setupViewModel() {
        var factory = ViewModelFactory.getScheduleInstance(requireContext())
        val viewModel: ScheduleViewModel by viewModels { factory }
        this.viewModel = viewModel
        factory = ViewModelFactory.getAuthInstance(requireContext())
        val authViewModel: AuthViewModel by viewModels { factory }
        authViewModel.getUserToken().observe(requireActivity()) { token = it }

        authViewModel.getUserId().observe(requireActivity()) {
            userId = it
            if (token.isNotBlank()) viewModel.getSchedule(userId, token)
        }

        viewModel.getScheduleResult.observe(requireActivity()) { observeGetSchedule(it) }
    }

    private fun observeGetSchedule(result: Result?) {
        if (result == null) return
        val binding = binding as FragmentScheduleBinding

        when (result) {
            Result.Loading -> binding.progressBar.visibility = View.VISIBLE
            is Result.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, "Failure : " + result.error, Toast.LENGTH_SHORT).show()
            }
            is Result.Success<*> -> {
                binding.progressBar.visibility = View.GONE
                val scheduleDetailList = (result.data as ScheduleResponse).schedule
                val scheduleList = mutableListOf<Schedule>()

                scheduleDetailList?.forEach { scheduleData ->
                    scheduleData?.items?.forEach { scheduleItem ->
                        val schedule = Schedule(scheduleItem.time, scheduleItem.title,
                            scheduleItem.content)

                        scheduleList.add(schedule)
                    }
                }

                val listScheduleAdapter = ScheduleAdapter(scheduleList)
                binding.rvSchedule.adapter = listScheduleAdapter
            }
        }
    }

    override fun setupView() {
        val binding = binding as FragmentScheduleBinding
        _username = binding.tvGreetingUser.toString()
        _tvDate = binding.tvDate.toString()
    }

    override fun setupAction() {}
}