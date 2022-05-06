package be.howest.nicolas.loontjens.f1stories.database

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userAuthItem: UserAuthItem)

    @Delete
    suspend fun delete(userAuthItem: UserAuthItem)

    @Query("select token from userauth where id = 1")
    fun getToken(): String
}