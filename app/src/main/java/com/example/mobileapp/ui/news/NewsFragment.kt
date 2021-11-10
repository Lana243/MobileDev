package com.example.mobileapp.ui.news

import androidx.fragment.app.viewModels
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment(R.layout.fragment_news) {
    val viewBinding by viewBinding(FragmentNewsBinding::bind)

    val viewModel : NewsViewModel by viewModels()
}