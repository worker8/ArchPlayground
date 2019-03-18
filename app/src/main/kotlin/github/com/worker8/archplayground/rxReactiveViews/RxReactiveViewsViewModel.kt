package github.com.worker8.archplayground.rxReactiveViews

import androidx.lifecycle.*
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import github.com.worker8.archplayground.common.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.properties.Delegates

class RxReactiveViewsViewModel(val viewInput: RxReactiveViewsViewModel.ViewInput) : ViewModel(), LifecycleObserver {
    private val disposables = CompositeDisposable()
    var redditApi: RedditApi by Delegates.observable(RedditApi()) { property, oldValue, newValue ->
        screenState.postValue(listOf())
    }

    val screenState: MutableLiveData<List<RedditLinkObject>> by lazy {
        MutableLiveData<List<RedditLinkObject>>(listOf())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        viewInput.apply {
            Observable.merge(
                refreshClickObservable.doOnNext { redditApi = RedditApi(redditApi.subreddit) },
                randomClickObservable.doOnNext { redditApi = RedditApi(RedditApi.getRandomSubreddit()) },
                loadMoreClickObservable
            )
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap { redditApi.getMorePosts() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ (result, fuelError) ->
                    result?.value?.let { _list ->
                        val newList = screenState.value?.toMutableList() ?: mutableListOf()
                        newList.addAll(_list.getRedditImageLinks())
                        screenState.postValue(newList)
                    }
                }, {
                    it.printStackTrace()
                })
                .addTo(disposables)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    interface ViewInput {
        val randomClickObservable: Observable<Unit>
        val refreshClickObservable: Observable<Unit>
        val loadMoreClickObservable: Observable<Unit>
    }
}

@Suppress("UNCHECKED_CAST")
class RxReactiveViewsViewModelFactory(val viewInput: RxReactiveViewsViewModel.ViewInput) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RxReactiveViewsViewModel(viewInput) as T
    }
}


class RxReactiveViewsScreenState()
