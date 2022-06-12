package com.skinconnect.userapps.ui.main.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.entity.ProfileTodoItem
import com.skinconnect.userapps.data.entity.response.ProfileResponse
import com.skinconnect.userapps.data.entity.response.ScheduleDataResponse
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.FragmentProfileBinding
import com.skinconnect.userapps.ui.auth.AuthActivity
import com.skinconnect.userapps.ui.auth.AuthViewModel
import com.skinconnect.userapps.ui.auth.ProfileViewModel
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment() {
    private lateinit var logoutButton: FloatingActionButton
    private lateinit var doctorId: String
    private var isFinishedGettingProfile = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupView()
        setupAction()
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: ProfileViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.getUserToken()
            .observe(requireActivity()) { if (!isFinishedGettingProfile) viewModel.getProfile(it) }

        viewModel.result.observe(requireActivity()) { observeGetProfile(it) }
    }

    private fun observeGetProfile(result: Result?) {
        if (result == null) return
        val binding = binding as FragmentProfileBinding

        when (result) {
            Result.Loading -> binding.profileProgressBar.visibility = View.VISIBLE
            is Result.Error -> {
                binding.profileProgressBar.visibility = View.GONE
                val errorPrefix = resources.getString(R.string.something_went_wrong)
                val text = "$errorPrefix. ${result.error}"
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            }
            is Result.Success<*> -> {
                binding.profileProgressBar.visibility = View.GONE
                val data = (result.data as ProfileResponse).profile
                doctorId = data.handledBy?.doctorId.orEmpty()
                val greetingText = "Hello ${data.username}"
                binding.greetingUserTextView.text = greetingText
                if (doctorId.isBlank()) return
                setViewDoctorProfileButtonVisible()
                if (data.scheduleData == null) return
                setupRecyclerView(data.scheduleData, data.id)
            }
        }
    }

    private fun setViewDoctorProfileButtonVisible() {
        val binding = binding as FragmentProfileBinding
        binding.viewDoctorProfileButton.visibility = View.VISIBLE

        binding.tvTodo.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            val convertedMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                104F,
                resources.displayMetrics).toInt()

            setMargins(marginStart, convertedMargin, marginEnd, bottomMargin)
        }
    }

    private fun setupRecyclerView(scheduleData: List<ScheduleDataResponse?>?, userId: String) {
        val data: MutableList<ProfileTodoItem> = mutableListOf()

        scheduleData?.forEach { scheduleDatum ->
            scheduleDatum?.items?.forEach { scheduleItem ->
                val item = ProfileTodoItem(scheduleDatum.id.orEmpty(),
                    scheduleItem?.id.orEmpty(),
                    scheduleItem?.title.orEmpty(), userId)

                data.add(item)
            }
        }

        val recyclerView = (binding as FragmentProfileBinding).rvTodo
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val profileAdapter = ProfileAdapter()
        recyclerView.adapter = profileAdapter
        profileAdapter.submitList(data)
    }

    @SuppressLint("SimpleDateFormat")
    override fun setupView() {
        val binding = binding as FragmentProfileBinding
        logoutButton = binding.fabLogout
        val dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")
        val dateText = "Last update: ${dateFormat.format(Calendar.getInstance().time)}"
        binding.dateReceive.text = dateText
    }

    override fun setupAction() {
        logoutButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle(resources.getString(R.string.logout))
                .setMessage(resources.getString(R.string.are_you_sure))
                .setNegativeButton(resources.getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                    val viewModel = viewModel as AuthViewModel
                    viewModel.saveUserToken("")
                    viewModel.saveUserId("")
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }.show()
        }
    }
}