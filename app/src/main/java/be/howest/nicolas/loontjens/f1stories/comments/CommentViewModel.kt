package be.howest.nicolas.loontjens.f1stories.comments

import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApiService
import be.howest.nicolas.loontjens.f1stories.network.data.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.lang.IllegalArgumentException

class CommentViewModel(storyId: Int) : ViewModel() {
    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments
    private val _selectedComment = MutableLiveData<Comment>()
    val selectedComment: LiveData<Comment>
        get() = _selectedComment

    var storyid = 0

    init{
        storyid = storyId
        getCommentsFromAPI(storyId)
    }

    private fun getCommentsFromAPI(storyId: Int){
        viewModelScope.launch {
            _comments.value = F1StoriesApi.retrofitService.getComments(storyId)
        }
    }

    fun goToAddComments(navController: NavController){
        val action: NavDirections = CommentFragmentDirections.actionCommentFragmentToAddComment2(storyid)
        navController.navigate(action)
    }

    fun onCommentClicked(comment: Comment){
        _selectedComment.value = comment
    }

    fun deleteComment(controller: NavController){
        val token = getToken()
        viewModelScope.launch {
            if (token != null) {
                F1StoriesApi.retrofitService.deleteComment(_selectedComment.value!!.commentid, token)
            }
        }
        val action: NavDirections = CommentFragmentDirections.actionCommentFragmentSelf(storyid)
        controller.navigate(action)
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

class CommentViewModelFactory(private val storyId: Int ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CommentViewModel::class.java)){
            return CommentViewModel(storyId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
