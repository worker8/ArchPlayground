package com.example.dagger_sample

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.worker8.redditapi.model.t3_link.response.RedditLinkListingObject
import io.reactivex.Observable

interface RepoInterface {
    fun getPosts(): Observable<Result<RedditLinkListingObject, FuelError>>
}
