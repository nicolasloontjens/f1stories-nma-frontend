package be.howest.nicolas.loontjens.f1stories.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.UserAuth
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    init{

    }

    fun register(username: String, password: String){

        viewModelScope.launch{
            val res = F1StoriesApi.retrofitService.register(UserAuth(username, password))
            println(res)
            val jwt = JWT(res.token)
            println(jwt.getClaim("uid").asString())
        }
    }
}