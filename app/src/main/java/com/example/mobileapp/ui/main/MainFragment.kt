package com.example.mobileapp.ui.main

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.R
import com.example.mobileapp.databinding.FragmentMainBinding
import com.example.mobileapp.ui.base.BaseFragment

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

    private val viewModel : MainFragmentViewModel by viewModels()
}