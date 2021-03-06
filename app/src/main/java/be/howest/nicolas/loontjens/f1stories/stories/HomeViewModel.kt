package be.howest.nicolas.loontjens.f1stories.stories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.AddInteraction
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {
    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>>
        get() = _stories

    private val _selectedStory = MutableLiveData<Story>()
    val selectedStory: LiveData<Story>
        get() = _selectedStory
    init{
        getStoriesFromApi()
    }

    private fun getStoriesFromApi(){
        viewModelScope.launch{
            try{
                var list: List<Story> = F1StoriesApi.retrofitService.getStories()
                list = list.sortedBy { story -> story.storyid }
                for(elem in list){
                    val datelist = elem.date.split("T")
                    elem.date = datelist[0]
                }
                _stories.value = list

            }catch(e:Exception){

            }
        }
    }

    fun onStoryClicked(story: Story){
        _selectedStory.value = story
    }

    fun likeStory(story: Story): Boolean{
        val userToken: String? = getToken()
        var res = true
        viewModelScope.launch {
            try{
                if (userToken != null) {
                    F1StoriesApi.retrofitService.likeStory(story.storyid, AddInteraction(1), userToken)
                }
            }catch(e: Exception){
                res = false
                if (userToken != null) {
                    F1StoriesApi.retrofitService.likeStory(story.storyid, AddInteraction(0), userToken)
                }
            }
        }
        Thread.sleep(1000)
        return res
    }

    fun getToken(): String? {
        var token: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            token = UserRoomDatabase.INSTANCE?.UserDao()?.getToken()
        }
        Thread.sleep(700)
        return token
    }

}