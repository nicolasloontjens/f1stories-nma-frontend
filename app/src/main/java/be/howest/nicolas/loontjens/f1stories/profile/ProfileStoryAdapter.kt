package be.howest.nicolas.loontjens.f1stories.profile

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.databinding.ProfileViewItemBinding
import be.howest.nicolas.loontjens.f1stories.network.data.Profile
import be.howest.nicolas.loontjens.f1stories.network.data.ProfileStory
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import be.howest.nicolas.loontjens.f1stories.stories.StoryListener

class ProfileStoryAdapter(val clickListenerDelete: ProfileStoryListener, val clickListenerEdit: ProfileStoryListener, val clickListenerComments: ProfileStoryListener, val clickListenerShare: ProfileStoryListener ): ListAdapter<ProfileStory, ProfileStoryAdapter.ProfileStoryViewHolder>(DiffCallback) {

    class ProfileStoryViewHolder(private var binding: ProfileViewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(clickListenerDelete: ProfileStoryListener, clickListenerEdit: ProfileStoryListener, clickListenerComments: ProfileStoryListener, clickListenerShare: ProfileStoryListener, story: ProfileStory){
            binding.story = story
            binding.clickListenerComments = clickListenerComments
            binding.clickListenerDelete = clickListenerDelete
            binding.clickListenerEdit = clickListenerEdit
            binding.clickListenerShare = clickListenerEdit
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProfileStory>(){
        override fun areItemsTheSame(oldItem: ProfileStory, newItem: ProfileStory): Boolean {
            return oldItem.storyid == newItem.storyid
        }

        override fun areContentsTheSame(oldItem: ProfileStory, newItem: ProfileStory): Boolean {
            return oldItem.content == newItem.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileStoryViewHolder {
        return ProfileStoryViewHolder(ProfileViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProfileStoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(clickListenerDelete, clickListenerEdit, clickListenerComments, clickListenerShare, story)
    }
}
class ProfileStoryListener(val clickListener: (story: ProfileStory) -> Unit){
    fun onClick(story: ProfileStory){
        clickListener(story)
    }
}