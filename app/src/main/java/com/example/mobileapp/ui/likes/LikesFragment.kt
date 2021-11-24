package com.example.mobileapp.ui.likes

import androidx.fragment.app.viewModels
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.FragmentLikesBinding

class LikesFragment : BaseFragment(R.layout.fragment_likes) {
    private val viewBinding by viewBinding(FragmentLikesBinding::bind)
    private val viewModel : LikesViewModel by viewModels()
}