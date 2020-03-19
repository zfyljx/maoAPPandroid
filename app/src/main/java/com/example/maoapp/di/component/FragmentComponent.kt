package com.example.maoapp.di.component

import android.app.Activity
import com.example.maoapp.di.module.FragmentModule
import com.example.maoapp.di.scope.FragmentScope
import dagger.Component

@FragmentScope
@Component(dependencies = [ApiComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    val activity: Activity
}