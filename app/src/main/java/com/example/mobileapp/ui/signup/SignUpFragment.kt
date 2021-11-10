package com.example.mobileapp.ui.signup

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.ui.base.BaseFragment
import com.example.mobileapp.R
import com.example.mobileapp.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel : SignUpViewModel by viewModels()



}