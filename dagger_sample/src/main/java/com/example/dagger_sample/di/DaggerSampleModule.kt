package com.example.dagger_sample.di

import com.example.dagger_sample.DaggerSampleRepo
import com.example.dagger_sample.DaggerSampleRepo2
import com.example.dagger_sample.DaggerSampleRepoInterface
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class HomeModule() {
    @Binds
    @Named("repo1")
    abstract fun provideRepo(repo: DaggerSampleRepo): DaggerSampleRepoInterface

    @Binds
    @Named("repo2")
    abstract fun provideRepo2(repo: DaggerSampleRepo2): DaggerSampleRepoInterface
}
