package be.howest.nicolas.loontjens.f1stories.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.databinding.HomeViewItemBinding
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import be.howest.nicolas.loontjens.f1stories.stories.HomeStoryAdapter.HomeStoryViewHolder

class HomeStoryAdapter(val clickListener1: StoryListener, val clickListener2: StoryListener, val clickListener3: StoryListener): androidx.recyclerview.widget.ListAdapter<Story, HomeStoryViewHolder>(DiffCallback) {

    class HomeStoryViewHolder(private var binding: HomeViewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener1: StoryListener, clickListener2: StoryListener, clickListener3: StoryListener, story: Story){
            binding.story = story
            binding.clickListener1 = clickListener1
            binding.clickListener2 = clickListener2
            binding.clickListener3 = clickListener3
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
        holder.bind(clickListener1, clickListener2, clickListener3, story)
    }
}

class StoryListener(val clickListener: (story: Story) -> Unit){
    fun onClick(story: Story){
        clickListener(story)
    }
}