package com.ssafy.ttasoom

import android.content.Context
import com.ssafy.di.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {
    @Provides
    fun provideNavigator(
        @ActivityContext context: Context
    ): Navigator {
        val mainActivity = context as? MainActivity
            ?: throw IllegalStateException("Navigator는 MainActivity에서만 사용하세요")
        return mainActivity
    }
}
