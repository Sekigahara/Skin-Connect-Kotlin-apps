package com.skinconnect.userapps.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.skinconnect.userapps.R
import com.skinconnect.userapps.customview.EditText
import com.skinconnect.userapps.data.remote.response.RegisterDetails
import com.skinconnect.userapps.databinding.FragmentSignUpBinding
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.FormValidator
import com.skinconnect.userapps.ui.helper.NoFilterArrayAdapter
import com.skinconnect.userapps.ui.helper.ViewHelper

class FirstPageRegisterFragment : BaseFragment() {
    private lateinit var ageEditText: EditText
    private lateinit var genderAutoCompleteTextView: AutoCompleteTextView
    private lateinit var weightEditText: EditText
    private lateinit var loginTextView: TextView
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentSignUpBinding
        ageEditText = binding.cvAge
        genderAutoCompleteTextView = binding.autoCompleteTextViewGender
        val adapter = NoFilterArrayAdapter(requireContext(),
            R.layout.list_item_dropdown,
            arrayOf("Male", "Female"))
        genderAutoCompleteTextView.setAdapter(adapter)
        weightEditText = binding.cvWeight
        loginTextView = binding.login
        nextButton = binding.btnNext
        setNextPageButtonEnable()
    }

    override fun setupAction() {
        val navigationToLogin =
            Navigation.createNavigateOnClickListener(R.id.action_fragmentRegisterFirstPage_to_loginFragment)

        loginTextView.setOnClickListener(navigationToLogin)

        val textWatcher = ViewHelper.addTextChangeListener { setNextPageButtonEnable() }

        ageEditText.addTextChangedListener(textWatcher)
        weightEditText.addTextChangedListener(textWatcher)
        genderAutoCompleteTextView.addTextChangedListener(textWatcher)

        nextButton.setOnClickListener { view ->
            val age = "${ageEditText.text}"
            val gender = "${genderAutoCompleteTextView.text}"
            val weight = "${weightEditText.text}"
            val request = RegisterDetails(gender, age, weight)

            val toSecondRegisterPageFragment =
                FirstPageRegisterFragmentDirections.actionFragmentRegisterFirstPageToFragmentRegisterSecondPage(
                    request,
                )

            view.findNavController().navigate(toSecondRegisterPageFragment)
        }
    }

    private fun setNextPageButtonEnable() {
        val age = ageEditText.text
        val gender = genderAutoCompleteTextView.text
        val weight = weightEditText.text

        nextButton.isEnabled =
            FormValidator.validateAge("$age") && FormValidator.validateWeight(
                "$weight") && FormValidator.validateGender("$gender")
    }

    override fun setupViewModel() {}
}