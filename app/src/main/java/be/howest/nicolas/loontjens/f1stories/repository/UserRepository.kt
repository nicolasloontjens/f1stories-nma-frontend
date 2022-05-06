package be.howest.nicolas.loontjens.f1stories.repository

import androidx.annotation.WorkerThread
import be.howest.nicolas.loontjens.f1stories.database.UserAuthItem
import be.howest.nicolas.loontjens.f1stories.database.UserDao

class UserRepository(private val userDao: UserDao) {

    @Suppress("ReduntantSuspendModifier")
    @WorkerThread
    suspend fun insert(uauthitem: UserAuthItem){
        userDao.insert(uauthitem)
    }

    fun getToken(): String{
        return userDao.getToken()
    }

    fun getUid(): Int{
        return userDao.getUid()
    }

    fun isLoggedIn(): Boolean{
        return userDao.isLoggedIn()
    }

    fun nuke(){
        return userDao.nukeTable()
    }
}