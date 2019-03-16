package com.example.dagger_sample.di

import com.example.dagger_sample.DaggerSampleActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeMainActivity(): DaggerSampleActivity
}
