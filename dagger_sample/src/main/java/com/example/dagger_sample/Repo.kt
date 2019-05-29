package com.example.dagger_sample

import com.worker8.redditapi.RedditApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repo() : RepoInterface {
    val redditApi = RedditApi()

    override fun getPosts() =
        redditApi.getMorePosts()

    fun getMainThread(): Scheduler = AndroidSchedulers.mainThread()
    fun getBackgroundThread(): Scheduler = Schedulers.io()
}
