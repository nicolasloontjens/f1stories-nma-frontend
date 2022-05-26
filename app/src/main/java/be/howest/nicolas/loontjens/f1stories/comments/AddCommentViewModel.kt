package be.howest.nicolas.loontjens.f1stories.comments

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.AddCommentBody
import be.howest.nicolas.loontjens.f1stories.stories.HomeFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class AddCommentViewModel(storyId: Int) : ViewModel() {

    var storyid = 0
    init{
        storyid = storyId
    }

    fun sendCommentToAPI(content: String){
        val token = getToken()
        viewModelScope.launch {
            try{
                if (token != null) {
                    F1StoriesApi.retrofitService.addComment(storyid, AddCommentBody(content), token)
                }
            }catch(ex: Exception){

            }
        }

    }

    fun goToComments(navController: NavController){
        val action: NavDirections = AddCommentDirections.actionAddComment2ToCommentFragment(storyid)
        navController.navigate(action)
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

class AddCommentViewModelFactory(private val storyId: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddCommentViewModel::class.java)){
            return AddCommentViewModel(storyId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}