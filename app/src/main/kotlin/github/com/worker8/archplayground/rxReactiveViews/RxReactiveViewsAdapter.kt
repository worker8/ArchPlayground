package github.com.worker8.archplayground.rxReactiveViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.worker8.redditapi.model.t3_link.data.RedditLinkData
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import github.com.worker8.archplayground.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.row_reactive_views.view.*

class RxReactiveViewsAdapter : ListAdapter<RedditLinkObject, RecyclerView.ViewHolder>(comparator) {
    private val subject = PublishSubject.create<String>()

    val titleObservable = subject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RxReactiveViewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val redditLinkData = getItem(position).value
        (holder as RxReactiveViewsViewHolder).bind(redditLinkData)
        holder.titleObservable.subscribe(subject)
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<RedditLinkObject>() {
            override fun areItemsTheSame(oldItem: RedditLinkObject, newItem: RedditLinkObject) =
                oldItem.value.id == newItem.value.id

            override fun areContentsTheSame(oldItem: RedditLinkObject, newItem: RedditLinkObject) =
                oldItem == newItem
        }
    }

    class RxReactiveViewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleObservable by lazy {
            itemView.clicks().map { itemView.rxViewRowTextView.text.toString() }
        }

        fun bind(redditLinkData: RedditLinkData) {
            itemView.apply {
                rxViewRowTextView.text = redditLinkData.title
                Glide.with(context)
                    .load(redditLinkData.url)
                    .into(rxViewRowImageView)
            }
        }

        companion object {
            fun create(parent: ViewGroup) =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_reactive_views, parent, false)
                    .let { RxReactiveViewsViewHolder(it) }
        }
    }
}
