package com.example.mobileapp.ui.emailconfirmation

import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mobileapp.R
import com.example.mobileapp.ui.base.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.data.network.response.error.CreateProfileErrorResponse
import com.example.mobileapp.databinding.FragmentEmailConfirmationBinding
import com.example.mobileapp.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class EmailConfirmationFragment : BaseFragment(R.layout.fragment_email_confirmation) {

    private val viewBinding by viewBinding(FragmentEmailConfirmationBinding::bind)

    private val viewModel : EmailConfirmationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sendCode()

        subscribeToVerificationState()

        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                viewBinding.verifyButton.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
            }
        }

        viewBinding.sendNewCodeButton?.applyInsetter {
            type(navigationBars = true) { margin() }
        }

        viewBinding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewBinding.verifyButton.setOnClickListener {
            viewModel.verifyCode(viewBinding.verificationCodeEditText?.getCode())
        }

        viewBinding.sendNewCodeButton?.setOnClickListener {
            viewModel.sendCode()
        }
    }

    private fun subscribeToVerificationState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.verificationStateFlow().collect(::renderVerificationState)
            }
        }
    }

    private fun renderVerificationState(verificationState : EmailConfirmationViewModel.VerificationState) {
        when (verificationState) {
            is EmailConfirmationViewModel.VerificationState.Pending -> {
                viewBinding.sendNewCodeButton?.text = resources.getString(
                    R.string.email_confirmation_resend)
                viewBinding.sendNewCodeButton?.isEnabled = true
            }
            is EmailConfirmationViewModel.VerificationState.Verified -> {
                findNavController().navigate(R.id.signInFragment)
            }
            is EmailConfirmationViewModel.VerificationState.TimerTicking -> {
                if (verificationState.timeRemainSeconds == 9L) {
                    val toast = Toast.makeText( context, "Ваш секретный код: ${verificationState.code}",
                        Toast.LENGTH_LONG)
                    toast.show()
                }
                viewBinding.sendNewCodeButton?.text = resources.getString(
                    R.string.email_confirmation_timer, verificationState.timeRemainSeconds)
                viewBinding.sendNewCodeButton?.isEnabled = false
            }
            is EmailConfirmationViewModel.VerificationState.VerificationError -> {
                Toast.makeText(context, verificationState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}