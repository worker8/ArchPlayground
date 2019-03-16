package com.example.dagger_sample.di

import android.content.Context
import com.example.dagger_sample.DaggerSampleApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule() {
    @Binds
    abstract fun provideContext(application: DaggerSampleApplication): Context
}
