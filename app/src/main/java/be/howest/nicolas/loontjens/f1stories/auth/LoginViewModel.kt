package be.howest.nicolas.loontjens.f1stories.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import be.howest.nicolas.loontjens.f1stories.database.UserAuthItem
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.UserAuth
import be.howest.nicolas.loontjens.f1stories.repository.UserRepository
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(username: String, password: String): Boolean{
        var success = false;
        Thread{
            repository.nuke()
        }.start()
        try{
            viewModelScope.launch{
                val res = F1StoriesApi.retrofitService.login(UserAuth(username, password))
                val jwt = JWT(res.token)
                val uid = jwt.getClaim("uid").asInt()
                uid?.let { UserAuthItem(0, it,res.token, true) }?.let { repository.insert(it) }
                Thread{
                    success = repository.isLoggedIn()
                }.start()
            }
        }catch(e: Exception) {

        }
        Thread.sleep(1000)
        println(success)
        return success
    }
}

class CreateLoginUserRepository(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}