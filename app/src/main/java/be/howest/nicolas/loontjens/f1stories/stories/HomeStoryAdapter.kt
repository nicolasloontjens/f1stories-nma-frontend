package be.howest.nicolas.loontjens.f1stories.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.databinding.HomeViewItemBinding
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import be.howest.nicolas.loontjens.f1stories.stories.HomeStoryAdapter.HomeStoryViewHolder

class HomeStoryAdapter(): androidx.recyclerview.widget.ListAdapter<Story, HomeStoryViewHolder>(DiffCallback) {

    class HomeStoryViewHolder(private var binding: HomeViewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(story: Story){
            binding.story = story
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Story>(){
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.storyid == newItem.storyid
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.content == newItem.content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeStoryViewHolder {
        return HomeStoryViewHolder(HomeViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HomeStoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }
}