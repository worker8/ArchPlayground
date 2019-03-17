package github.com.worker8.archplayground.rxReactiveViews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import github.com.worker8.archplayground.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_reactive_views.*

class RxReactiveViewsActivity : AppCompatActivity() {
    val disposables = CompositeDisposable()
    val adapter = RxReactiveViewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reactive_views)
        rxViewRecyclerView.adapter = adapter

        val viewModel = ViewModelProviders.of(this, RxReactiveViewsViewModelFactory()).get(RxReactiveViewsViewModel::class.java)
        lifecycle.addObserver(viewModel)

        viewModel.screenState.observe(this, Observer { list ->
            adapter.submitList(list)
        })

        rxViewChangeSubredditButton.setOnClickListener {
            viewModel.randomSubredditClick()
        }
        rxViewRefreshButton.setOnClickListener {
            viewModel.refreshClick()
        }
        rxViewLoadMoreButton.setOnClickListener {
            viewModel.onLoadMoreClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
