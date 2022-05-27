package be.howest.nicolas.loontjens.f1stories.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.comments.CommentsAdapter.CommentViewHolder
import be.howest.nicolas.loontjens.f1stories.databinding.CommentViewItemBinding
import be.howest.nicolas.loontjens.f1stories.network.data.Comment

class CommentsAdapter(val clickListenerEdit: CommentListener, val clickListenerDelete: CommentListener) : ListAdapter<Comment, CommentViewHolder>(DiffCallback) {
    class CommentViewHolder(private var binding: CommentViewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment, clickListenerEdit: CommentListener, clickListenerDelete: CommentListener){
            binding.comment = comment
            binding.clickListenerEdit = clickListenerEdit
            binding.clickListenerDelete = clickListenerDelete
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder{
        return CommentViewHolder(CommentViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int){
        val comment = getItem(position)
        holder.bind(comment, clickListenerEdit, clickListenerDelete)
    }

    companion object DiffCallback:DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.commentid == newItem.commentid
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.content == newItem.content
        }

    }
}

class CommentListener(val clickListener: (comment: Comment) -> Unit){
    fun onClick(comment: Comment){
        clickListener(comment)
    }
}
