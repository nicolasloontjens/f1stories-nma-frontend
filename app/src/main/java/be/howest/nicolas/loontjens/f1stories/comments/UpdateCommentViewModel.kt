package be.howest.nicolas.loontjens.f1stories.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.databinding.UpdateCommentFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.AddCommentBody
import be.howest.nicolas.loontjens.f1stories.network.data.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UpdateCommentViewModel(comment: Comment) : ViewModel() {
    var thecomment: Comment? = null

    init{
        thecomment = comment
    }

    fun showComment(binding: UpdateCommentFragmentBinding){
        binding.updateCommentContent.setText(thecomment!!.content)
    }

    fun updateComment(controller: NavController, content: String){
        val token = getToken()
        viewModelScope.launch {
            if (token != null) {
                F1StoriesApi.retrofitService.updateComment(thecomment?.commentid!!,
                    AddCommentBody(content), token)
            }
        }
        val action: NavDirections = UpdateCommentFragmentDirections.actionUpdateCommentFragmentToCommentFragment(
            thecomment!!.storyid)
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

class UpdateCommentViewModelFactory(private val comment: Comment): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateCommentViewModel::class.java)){
            return UpdateCommentViewModel(comment) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}