package be.howest.nicolas.loontjens.f1stories.comments

import androidx.lifecycle.*
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApiService
import be.howest.nicolas.loontjens.f1stories.network.data.Comment
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CommentViewModel(storyId: Int) : ViewModel() {
    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    init{
        getCommentsFromAPI(storyId)
    }

    private fun getCommentsFromAPI(storyId: Int){
        viewModelScope.launch {
            _comments.value = F1StoriesApi.retrofitService.getComments(storyId)
        }
    }
}

class CommentViewModelFactory(private val storyId: Int ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CommentViewModel::class.java)){
            return CommentViewModel(storyId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}