package be.howest.nicolas.loontjens.f1stories.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.howest.nicolas.loontjens.f1stories.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class OverviewViewModel(private val repository: UserRepository) : ViewModel() {
    fun logout(){
        //viewmodelscope runs on main thread, this runs on separate thread making sure that the ui does not freeze
        CoroutineScope(Dispatchers.IO).launch {
            repository.nuke()
        }
    }
}

class CreateLogoutUserRepository(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OverviewViewModel::class.java)){
            return OverviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}