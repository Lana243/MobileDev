package com.example.mobileapp

import by.kirich1409.viewbindingdelegate.viewBinding
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mobileapp.databinding.FragmentUserListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class UserListFragment : BaseFragment(R.layout.fragment_user_list) {

    private val viewBinding by viewBinding(FragmentUserListBinding::bind)

    private val viewModel: UserListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToViewState()
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
                viewBinding.usersRecyclerView.isVisible = false
                viewBinding.progressBar.isVisible = true
            }
            is UserListViewModel.ViewState.Data -> {
                viewBinding.progressBar.isVisible = false
                viewBinding.usersRecyclerView.isVisible = true

                (viewBinding.usersRecyclerView.adapter as UserAdapter).apply {
                    userList = viewState.userList
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupRecyclerView(): UserAdapter =
        UserAdapter().also {
            viewBinding.usersRecyclerView.adapter = it
        }


}