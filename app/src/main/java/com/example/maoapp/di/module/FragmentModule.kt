package com.example.maoapp.di.module

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.maoapp.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(val fragment:Fragment) {
    @Provides
    @FragmentScope
    fun provideActivity(): Activity = fragment.activity!!

}