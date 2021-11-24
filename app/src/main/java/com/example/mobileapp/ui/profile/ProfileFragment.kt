package com.example.mobileapp.ui.profile

import androidx.fragment.app.viewModels
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val viewBinding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel : ProfileViewModel by viewModels()
}