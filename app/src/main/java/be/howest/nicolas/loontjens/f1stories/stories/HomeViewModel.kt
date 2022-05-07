package be.howest.nicolas.loontjens.f1stories.stories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {
    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>>
        get() = _stories

    init{
        getStoriesFromApi()
    }

    private fun getStoriesFromApi(){
        viewModelScope.launch{
            try{
                _stories.value = F1StoriesApi.retrofitService.getStories()

            }catch(e:Exception){

            }
        }
    }
}