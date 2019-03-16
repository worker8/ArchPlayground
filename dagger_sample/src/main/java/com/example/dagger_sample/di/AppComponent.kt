package com.example.dagger_sample.di

import com.example.dagger_sample.DaggerSampleApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class
])
interface AppComponent : AndroidInjector<DaggerSampleApplication> {

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(application: DaggerSampleApplication): Builder

        abstract fun build(): AppComponent
    }
}
