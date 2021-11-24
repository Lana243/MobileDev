package com.example.mobileapp.ui.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.FragmentNewsBinding
import dev.chrisbanes.insetter.applyInsetter

class NewsFragment : BaseFragment(R.layout.fragment_news) {
    val viewBinding by viewBinding(FragmentNewsBinding::bind)

    val viewModel : NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.toolbar.applyInsetter {
            type(statusBars = true) { margin() }
        }
    }
}