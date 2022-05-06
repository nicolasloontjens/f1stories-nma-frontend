package be.howest.nicolas.loontjens.f1stories.database

import android.app.Application
import be.howest.nicolas.loontjens.f1stories.repository.UserRepository

class UserApplication: Application() {
    val database: UserRoomDatabase by lazy {UserRoomDatabase.getDatabase(this)}
    val repository: UserRepository by lazy { UserRepository(database.UserDao())}
}