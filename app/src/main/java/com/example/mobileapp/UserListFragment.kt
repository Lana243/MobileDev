package com.example.mobileapp

import by.kirich1409.viewbindingdelegate.viewBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mobileapp.databinding.FragmentUserListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.mobileapp.UserListViewModel

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
                viewModel.viewState.collect {  viewState ->
                    renderViewState(viewState)
                }
            }
        }
    }


    private fun renderViewState(viewState: UserListViewModel.ViewState) {
        when (viewState) {
            is UserListViewModel.ViewState.Loading -> {
                viewBinding
                // viewBinding.usersRecyclerView.isVisible = false
                // viewBinding.progressBar.isVisible = true
            }
            is UserListViewModel.ViewState.Data -> {
                // viewBinding.progressBar.isVisible = false
                // viewBinding.usersRecycleView.isVisible = true

                (viewBinding.usersRecycleView.adapter as UserAdapter).apply {
                    userList = viewState.userList
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupRecyclerView(): UserAdapter {
        // val recyclerView = findViewById<RecyclerView>(R.id.usersRecycleView)
        // val adapter = UserAdapter()
        // recyclerView.adapter = adapter
        // return adapter
    }
}