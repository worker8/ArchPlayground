package com.example.dagger_sample

import com.example.dagger_sample.di.DaggerAppComponent
import dagger.android.DaggerApplication

class DaggerSampleApplication : DaggerApplication() {
    override fun applicationInjector() =
        DaggerAppComponent.builder().application(this).build()
}
