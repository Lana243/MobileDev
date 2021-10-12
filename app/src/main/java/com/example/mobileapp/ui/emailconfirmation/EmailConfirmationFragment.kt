package com.example.mobileapp.ui.emailconfirmation

import androidx.fragment.app.viewModels
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.FragmentEmailConfirmationBinding

class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {
    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)
    private val viewModel : EmailConfirmationViewModel by viewModels()
}