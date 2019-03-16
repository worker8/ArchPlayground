package com.example.dagger_sample

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_dagger_sample.*
import javax.inject.Inject

class DaggerSampleActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repo: DaggerSampleRepo
    val adapter = DaggerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger_sample)
        setupViews()

        daggerAddButton.setOnClickListener {
            repo.saveStringToPref(daggerInput.text.toString())
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
}
