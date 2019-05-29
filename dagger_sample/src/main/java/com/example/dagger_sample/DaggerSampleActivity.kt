package com.example.dagger_sample

import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_dagger_sample.*
import javax.inject.Inject
import javax.inject.Named

class DaggerSampleActivity : DaggerAppCompatActivity() {

    @Inject
    @field:Named("repo1")
    lateinit var repo: DaggerSampleRepoInterface
    val adapter = DaggerAdapter()
    val redditRepo = Repo()

    val disposableBag = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger_sample)
        setupViews()
        //DaggerAppComponent.builder().build()
        disposableBag.add(
            redditRepo
                .getPosts()
                .subscribeOn(redditRepo.getBackgroundThread())
                .observeOn(redditRepo.getMainThread())
                .subscribe(
                    { (result, fuelError) ->
                    result?.let {
                        it.value.valueList.forEach { redditLinkObject ->
                            Log.d("ddw", "title: ${redditLinkObject.value.title}")
                        }
                    }
                }, {
                    it.printStackTrace()
                })
        )


        daggerAddButton.setOnClickListener {
            repo.saveStringToPref(daggerInput.text.toString())
            refreshRecyclerView()
        }

        daggerRemoveButton.setOnClickListener {
            repo.removeAllPref()
            refreshRecyclerView()
        }
    }

    private fun setupViews() {
        daggerRecyclerView.adapter = adapter
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        adapter.submitList(repo.getSavedString())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableBag.dispose()
    }
}
