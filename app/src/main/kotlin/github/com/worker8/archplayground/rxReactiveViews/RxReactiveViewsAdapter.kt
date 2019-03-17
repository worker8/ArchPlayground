package github.com.worker8.archplayground.rxReactiveViews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import github.com.worker8.archplayground.R
import kotlinx.android.synthetic.main.row_reactive_views.view.*

class RxReactiveViewsAdapter : ListAdapter<RedditLinkObject, RecyclerView.ViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RxReactiveViewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val redditLinkObject = getItem(position)
        (holder as RxReactiveViewsViewHolder).bind(redditLinkObject.value.title)
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
        fun bind(text: String) {
            itemView.apply {
                rxViewRowTextView.text = text
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
