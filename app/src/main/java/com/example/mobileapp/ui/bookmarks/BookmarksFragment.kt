package com.example.mobileapp.ui.bookmarks

import androidx.fragment.app.viewModels
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.databinding.FragmentBookmarksBinding

class BookmarksFragment : BaseFragment(R.layout.fragment_bookmarks) {
    private val viewBinding by viewBinding(FragmentBookmarksBinding::bind)
    private val viewModel : BookmarksViewModel by viewModels()
}