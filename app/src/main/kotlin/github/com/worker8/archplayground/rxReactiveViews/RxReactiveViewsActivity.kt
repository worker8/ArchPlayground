package github.com.worker8.archplayground.rxReactiveViews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding3.view.clicks
import github.com.worker8.archplayground.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_reactive_views.*

class RxReactiveViewsActivity : AppCompatActivity() {
    val disposables = CompositeDisposable()
    val adapter = RxReactiveViewsAdapter()
    val viewInput: RxReactiveViewsViewModel.ViewInput = object : RxReactiveViewsViewModel.ViewInput {
        override val randomClickObservable by lazy {
            rxViewChangeSubredditButton.clicks()
        }
        override val refreshClickObservable by lazy {
            rxViewRefreshButton.clicks()
        }
        override val loadMoreClickObservable by lazy {
            rxViewLoadMoreButton.clicks()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reactive_views)
        rxViewRecyclerView.adapter = adapter

        val viewModel = ViewModelProviders.of(this, RxReactiveViewsViewModelFactory(viewInput)).get(RxReactiveViewsViewModel::class.java)
        lifecycle.addObserver(viewModel)

        viewModel.screenState.observe(this, Observer { list ->
            adapter.submitList(list)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
