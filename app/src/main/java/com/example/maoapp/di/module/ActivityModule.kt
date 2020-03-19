package com.example.maoapp.di.module

import android.app.Activity
import com.example.maoapp.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: Activity) {
    @Provides
    @ActivityScope
    fun provideActivity(): Activity {
        return activity
    }
}