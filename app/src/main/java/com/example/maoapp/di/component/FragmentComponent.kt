package com.example.maoapp.di.component

import android.app.Activity
import com.example.maoapp.di.module.FragmentModule
import com.example.maoapp.di.scope.FragmentScope
import com.example.maoapp.ui.gallery.GalleryFragment
import com.example.maoapp.ui.home.HomeFragment
import com.example.maoapp.ui.order.MineOrderFragment
import com.example.maoapp.ui.slideshow.SlideshowFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApiComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    val activity: Activity

    fun inject(homeFragment: HomeFragment)
    fun inject(mineFragment: GalleryFragment)
    fun inject(sellFragment:SlideshowFragment)
    fun inject(mineOrderFragment: MineOrderFragment)
}