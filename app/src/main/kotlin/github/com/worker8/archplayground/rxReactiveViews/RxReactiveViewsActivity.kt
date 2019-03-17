package github.com.worker8.archplayground.rxReactiveViews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.worker8.redditapi.RedditApi
import github.com.worker8.archplayground.R
import github.com.worker8.archplayground.common.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_reactive_views.*

class RxReactiveViewsActivity : AppCompatActivity() {
    val disposables = CompositeDisposable()
    val adapter = RxReactiveViewsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reactive_views)
        rxViewRecyclerView.adapter = adapter

        RedditApi().getMorePosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (result, fuelError) ->
                fuelError?.printStackTrace()
                result?.value?.let { _list ->
                    adapter.submitList(_list.valueList)
                    _list.valueList.forEach {
                        val temp = it.value
                        Log.d("ddw", "${temp.title}")
                    }
                }
            }, {
                it.printStackTrace()
            })
            .addTo(disposables)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
