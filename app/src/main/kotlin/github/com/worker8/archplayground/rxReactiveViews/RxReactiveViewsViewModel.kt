package github.com.worker8.archplayground.rxReactiveViews

import androidx.lifecycle.*
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import github.com.worker8.archplayground.common.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.properties.Delegates

class RxReactiveViewsViewModel : ViewModel(), LifecycleObserver {
    private val disposables = CompositeDisposable()
    var redditApi: RedditApi by Delegates.observable(RedditApi()) { property, oldValue, newValue ->
        screenState.postValue(listOf())
    }

    val screenState: MutableLiveData<List<RedditLinkObject>> by lazy {
        MutableLiveData<List<RedditLinkObject>>(listOf())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    fun refreshClick() {
        redditApi = RedditApi(redditApi.subreddit)
        loadMorePosts()
    }

    fun randomSubredditClick() {
        redditApi = RedditApi(RedditApi.getRandomSubreddit())
        loadMorePosts()
    }

    fun onLoadMoreClick() {
        loadMorePosts()
    }

    fun loadMorePosts() {
        redditApi.getMorePosts()
            .subscribeOn(Schedulers.io())
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

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

@Suppress("UNCHECKED_CAST")
class RxReactiveViewsViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RxReactiveViewsViewModel() as T
    }
}

class RxReactiveViewsScreenState()
