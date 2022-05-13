package be.howest.nicolas.loontjens.f1stories

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.network.data.Story
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
    println("here")
    println(recyclerView)
    println(data)
    val adapter = recyclerView.adapter as HomeStoryAdapter?
    adapter?.submitList(data)
}