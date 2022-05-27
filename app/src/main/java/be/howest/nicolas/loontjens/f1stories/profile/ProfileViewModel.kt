package be.howest.nicolas.loontjens.f1stories.profile

import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.viewbinding.ViewBinding
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.databinding.ProfileFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.Profile
import be.howest.nicolas.loontjens.f1stories.network.data.ProfileStory
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class ProfileViewModel(uid: Int) : ViewModel() {
    private val _stories = MutableLiveData<List<ProfileStory>>()
    val stories: LiveData<List<ProfileStory>>
        get() = _stories
    private val _selectedStory = MutableLiveData<ProfileStory>()
    val selectedStory: LiveData<ProfileStory>
        get() = _selectedStory

    var profile: Profile? = null
    var userid: Int = 0

    init{
        userid = uid
        getProfile()
    }

    private fun getProfile(){
        viewModelScope.launch {
            val res = F1StoriesApi.retrofitService.getProfile(userid)
            _stories.value = res.stories
            profile = res
        }
    }

    fun onStoryClicked(story: ProfileStory){
        _selectedStory.value = story
    }

    fun deleteStory(story:ProfileStory, controller: NavController){
        val token = getToken()
        try{
            viewModelScope.launch {
                if (token != null) {
                    F1StoriesApi.retrofitService.deleteStory(story.storyid, token)
                }
            }
        }catch(err: Exception){

        }
        /*
        _stories.value?.forEachIndexed { index, elem ->
            if(elem.storyid == story.storyid){
                _stories.value!!.drop(index)
                adapter.notifyItemRemoved(index)
            }
        }*/
        val action: NavDirections = ProfileFragmentDirections.actionProfileFragmentSelf(userid)
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

    fun getUserProfile(binding: ProfileFragmentBinding){
        var prof: Profile? = null
        //cant use suspend functions in the onviewcreated so thats why there's a coroutinescope here
        CoroutineScope(Dispatchers.IO).launch {
            prof = F1StoriesApi.retrofitService.getProfile(userid)
        }
        Thread.sleep(1000)
        println(prof)
        binding.apply {
            profileUsername.text = prof!!.username
            profileStoriesCount.text = prof!!.stories.count().toString()
            profileUserscore.text = prof!!.userscore.toString()
            profileRacesVisisted.text = prof!!.racesvisited.toString()
        }
    }
}

class ProfileViewModelFactory(private val uid: Int):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(uid) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}