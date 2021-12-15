package com.example.mobileapp.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mobileapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {
    private val viewBinding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel : ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToEvents()
        subscribeToViewState()
        viewBinding.logoutButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.info.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        viewBinding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun subscribeToViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::renderViewState)
            }
        }
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow().collect { event ->
                    when (event) {
                        is ProfileViewModel.Event.LogoutError -> {
                            Toast
                                .makeText(
                                    requireContext(),
                                    R.string.common_general_error_text,
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderViewState(viewState : ProfileViewModel.ViewState) {
        when (viewState) {
            is ProfileViewModel.ViewState.Loading -> {
                with(viewBinding) {
                    listOf(firstName, lastName, groupName, avatarImageView, selectedUsername)
                }.forEach {
                    it.isVisible = false
                }
                viewBinding.progressBar.isVisible = true

            }
            is ProfileViewModel.ViewState.Data -> {
                viewBinding.progressBar.isVisible = false
                with(viewBinding) {
                    listOf(firstName, lastName, groupName, avatarImageView, selectedUsername)
                }.forEach {
                    it.isVisible = true
                }

                Glide.with(viewBinding.avatarImageView)
                    .load(viewState.user.avatarUrl)
                    .circleCrop()
                    .into(viewBinding.avatarImageView)
                viewBinding.selectedFirstName.text = viewState.user.firstName
                viewBinding.selectedLastName.text = viewState.user.lastName
                viewBinding.selectedGroup.text = viewState.user.groupName
                viewBinding.selectedUsername.text = viewState.user.username
                if (viewState.user.groupName == null) {
                    viewBinding.groupName.isVisible = false
                }
            }
        }
    }

}