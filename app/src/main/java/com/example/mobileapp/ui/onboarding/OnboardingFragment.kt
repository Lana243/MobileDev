package com.example.mobileapp.ui.onboarding

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mobileapp.ui.base.BaseFragment
import com.example.mobileapp.R
import com.example.mobileapp.databinding.FragmentOnboardingBinding
import com.example.mobileapp.onboardingTextAdapterDelegate
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dev.chrisbanes.insetter.applyInsetter

class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {

    val viewBinding by viewBinding(FragmentOnboardingBinding::bind)

    private var player: ExoPlayer? = null
    private var soundOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            addMediaItem(MediaItem.fromUri("asset:///onboarding_new.mp4"))
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        player?.volume = 0f
        viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_button_off)
        soundOn = false

        viewBinding.volumeControlButton.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.signUpButton.applyInsetter {
            type(navigationBars = true) { margin() }
        }
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                viewBinding.signInButton.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
                viewBinding.onboardingTextTabLayout.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
                viewBinding.viewPager.applyInsetter {
                    type(navigationBars = true) { margin() }
                }
            }
        }

        viewBinding.playerView.player = player
        viewBinding.viewPager.setTextPages()
        viewBinding.viewPager.attachDots(viewBinding.onboardingTextTabLayout)
        viewBinding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_signInFragment)
        }
        viewBinding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardingFragment_to_signUpFragment)
        }
        viewBinding.volumeControlButton.setOnClickListener {
            player?.volume = 1f - (player?.volume ?: 1f)
            if (soundOn) {
                viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_button_off)
            } else {
                viewBinding.volumeControlButton.setImageResource(R.drawable.ic_volume_button)
            }
            soundOn = soundOn xor true
        }

        viewBinding.viewPager.setPageTransformer(OnboardingPageTransformer())
    }

    override fun onResume() {
        super.onResume()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    private fun ViewPager2.setTextPages() {
        adapter =
            ListDelegationAdapter(onboardingTextAdapterDelegate()).apply {
                items =
                    listOf(
                        getString(R.string.onboarding_view_pager_text_1),
                        getString(R.string.onboarding_view_pager_text_2),
                        getString(R.string.onboarding_view_pager_text_3)
                    )
            }
    }

    private fun ViewPager2.attachDots(tabLayout: TabLayout) {
        TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
    }


}