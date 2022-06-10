package com.skinconnect.userapps.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.FragmentProfileBinding
import com.skinconnect.userapps.ui.auth.AuthActivity
import com.skinconnect.userapps.ui.auth.AuthViewModel
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory

class ProfileFragment : BaseFragment() {
    private lateinit var logoutButton: FloatingActionButton

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        viewBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        // TODO: If consult with doctor, add margin top of to do list text to 112dp
        val binding = binding as FragmentProfileBinding
        logoutButton = binding.fabLogout
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: AuthViewModel by viewModels { factory }
        this.viewModel = viewModel
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