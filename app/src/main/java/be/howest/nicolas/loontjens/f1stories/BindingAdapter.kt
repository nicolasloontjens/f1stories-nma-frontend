package be.howest.nicolas.loontjens.f1stories

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import be.howest.nicolas.loontjens.f1stories.stories.HomeStoryAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Story>?){
    println("here")
    println(recyclerView)
    println(data)
    val adapter = recyclerView.adapter as HomeStoryAdapter?
    adapter?.submitList(data)
}