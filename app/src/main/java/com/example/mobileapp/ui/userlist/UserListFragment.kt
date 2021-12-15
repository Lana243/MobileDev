package com.example.mobileapp.ui.userlist

import by.kirich1409.viewbindingdelegate.viewBinding
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mobileapp.R
import com.example.mobileapp.databinding.FragmentUserListBinding
import com.example.mobileapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : BaseFragment(R.layout.fragment_user_list) {

    private val viewBinding by viewBinding(FragmentUserListBinding::bind)

    private val viewModel: UserListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeToViewState()

        viewBinding.pullToRefreshLayout.applyInsetter {
            type(statusBars = true) { margin() }
        }

        viewBinding.refreshButton.setOnClickListener {
            viewModel.loadUsers()
        }

        viewBinding.pullToRefreshLayout.setOnRefreshListener {
            viewBinding.pullToRefreshLayout.isRefreshing = true
            viewModel.loadUsers()
            viewBinding.pullToRefreshLayout.isRefreshing = false
        }
    }

    private fun subscribeToViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::renderViewState)
            }
        }
    }


    private fun renderViewState(viewState: UserListViewModel.ViewState) {
        when (viewState) {
            is UserListViewModel.ViewState.Loading -> {
                viewBinding.pullToRefreshLayout.isRefreshing = true
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.errorLayout.isVisible = false
            }
            is UserListViewModel.ViewState.Data -> {
                viewBinding.pullToRefreshLayout.isRefreshing = false
                viewBinding.usersRecyclerView.isVisible = true
                viewBinding.errorLayout.isVisible = false

                (viewBinding.usersRecyclerView.adapter as UserAdapter).apply {
                    userList = viewState.userList
                    notifyDataSetChanged()
                }
            }
            is UserListViewModel.ViewState.Error -> {
                viewBinding.pullToRefreshLayout.isRefreshing = false
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.errorLayout.isVisible = true

                viewBinding.errorText.text = resources.getString(R.string.userlist_error_message)
            }
            is UserListViewModel.ViewState.EmptyList -> {
                viewBinding.pullToRefreshLayout.isRefreshing = false
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.errorLayout.isVisible = true

                viewBinding.errorText.text = resources.getString(R.string.userlist_empty)
            }
        }
    }

    private fun setupRecyclerView(): UserAdapter =
        UserAdapter().also {
            viewBinding.usersRecyclerView.adapter = it
        }


}