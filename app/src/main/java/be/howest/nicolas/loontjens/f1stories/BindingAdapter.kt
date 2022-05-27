package be.howest.nicolas.loontjens.f1stories

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.comments.CommentsAdapter
import be.howest.nicolas.loontjens.f1stories.network.data.Comment
import be.howest.nicolas.loontjens.f1stories.network.data.ProfileStory
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import be.howest.nicolas.loontjens.f1stories.profile.ProfileStoryAdapter
import be.howest.nicolas.loontjens.f1stories.stories.HomeStoryAdapter
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let{
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            error(R.drawable.favicon)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Story>?){
    val adapter = recyclerView.adapter as HomeStoryAdapter?
    adapter?.submitList(data)
}

@BindingAdapter("listDataC")
fun bindRecyclerViewC(recyclerView: RecyclerView, data:List<Comment>?){
    val adapter = recyclerView.adapter as CommentsAdapter?
    adapter?.submitList(data)
}

@BindingAdapter("listDataP")
fun bindRecyclerViewP(recyclerView: RecyclerView, data:List<ProfileStory>?){
    val adapter = recyclerView.adapter as ProfileStoryAdapter?
    adapter?.submitList(data)
}
