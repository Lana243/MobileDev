package com.example.mobileapp.interactor

import android.content.res.Resources
import com.example.mobileapp.R
import com.example.mobileapp.domain.User
import com.example.mobileapp.repository.ProfileRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend fun getProfile() : NetworkResponse<User, Unit> {
        val response = profileRepository.getProfile()
        when (response) {
            is NetworkResponse.Success -> {
                response.body.avatarUrl = response.body.avatarUrl ?: "https://s.yimg.com/ny/api/res/1.2/5rmN2CQI90DCby7uM2UefQ--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD04MDA-/https://s.yimg.com/uu/api/res/1.2/W1dJaYmRHV_PHKDIDmm__Q--~B/aD0xNDAwO3c9MjEwMDthcHBpZD15dGFjaHlvbg--/http://media.zenfs.com/en-US/homerun/time_72/ad41bb42a3e7be9eb1e32a9f2097b80f"
            }
        }
        return response
    }
}