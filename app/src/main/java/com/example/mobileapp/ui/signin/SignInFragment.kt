package com.example.mobileapp.ui.signin

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.ui.base.BaseFragment
import com.example.mobileapp.R
import com.example.mobileapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel : SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackButtonPressed()
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.backButton.applyInsetter {
            type(statusBars = true) { margin() }
        }

        viewBinding.signInButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }

        runAnimation()

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                viewBinding.signInButton.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
                viewBinding.passwordTextInputLayout.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
                viewBinding.emailTextInputLayout.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
            }
        }

        viewBinding.backButton.setOnClickListener {
            onBackButtonPressed()
        }

        viewBinding.signInButton.setOnClickListener {
            viewModel.signIn(
                email = viewBinding.emailEditText.text?.toString() ?: "",
                password = viewBinding.passwordEditText.text?.toString() ?: ""
            )
        }

        subscribeToFromFields()

    }

    private fun runAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.center_rotation)
        viewBinding.mcsLogoImageView.startAnimation(animation)
    }

    private fun subscribeToFromFields() {
        decideSignInButtonEnabledState(
            email = viewBinding.emailEditText.text?.toString(),
            password = viewBinding.passwordEditText.text?.toString()
        )
        viewBinding.emailEditText.doAfterTextChanged { email ->
            decideSignInButtonEnabledState(
                email = email?.toString(),
                password = viewBinding.passwordEditText.text?.toString()
            )
        }
        viewBinding.passwordEditText.doAfterTextChanged { password ->
            decideSignInButtonEnabledState(
                email = viewBinding.emailEditText.text?.toString(),
                password = password?.toString()
            )
        }
    }

    private fun decideSignInButtonEnabledState(email: String?, password: String?) {
        viewBinding.signInButton.isEnabled = (!email.isNullOrBlank() && !password.isNullOrBlank())
    }

    private fun onBackButtonPressed() {
        val email = viewBinding.emailEditText.text?.toString()
        val password = viewBinding.passwordEditText.text?.toString()
        if (email.isNullOrBlank() && password.isNullOrBlank()) {
            findNavController().popBackStack()
            return
        }

        AlertDialog.Builder(requireContext()).setTitle(R.string.sign_in_back_alert_dialog_text)
            .setNegativeButton(R.string.sign_in_back_alert_dialog_cancel_button_text) { dialog, _ ->
                dialog?.dismiss()
            }
            .setPositiveButton(R.string.sign_in_back_alert_dialog_ok_button_text) { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }
}