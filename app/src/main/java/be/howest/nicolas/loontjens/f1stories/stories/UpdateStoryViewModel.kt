package be.howest.nicolas.loontjens.f1stories.stories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.AddCommentBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UpdateStoryViewModel(storyId: Int) : ViewModel() {

    var id = 0

    init{
        id = storyId
    }

    fun updateStory(content: String){
        val token = getToken()
        viewModelScope.launch {
            try{
                if(token != null){
                    F1StoriesApi.retrofitService.updateStory(id, AddCommentBody(content), token)
                }
            }catch(ex: Exception){

            }
        }
    }

    fun goToProfile(controller: NavController){
        val userid = getUid()
        val action: NavDirections = UpdateStoryFragmentDirections.actionUpdateStoryFragmentToProfileFragment(
            userid!!
        )
        controller.navigate(action)
    }

    fun getUid(): Int?{
        var uid: Int? = null
        CoroutineScope(Dispatchers.IO).launch {
            uid = UserRoomDatabase.INSTANCE?.UserDao()?.getUid()
        }
        Thread.sleep(700)
        return uid
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

class UpdateStoryViewModelFactory(private val storyId: Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateStoryViewModel::class.java)){
            return UpdateStoryViewModel(storyId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}