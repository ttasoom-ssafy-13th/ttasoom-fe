package com.ssafy.feature.community

import androidx.lifecycle.ViewModel
import com.ssafy.domain.boiler.usecase.GetBoilerListUseCase
import javax.inject.Inject

class CommunityViewModel @Inject constructor(
    private val communityUseCase : GetBoilerListUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}