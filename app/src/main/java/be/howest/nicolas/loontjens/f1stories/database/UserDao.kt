package be.howest.nicolas.loontjens.f1stories.database

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userAuthItem: UserAuthItem)

    @Delete
    suspend fun delete(userAuthItem: UserAuthItem)

    @Query("select token from userauth limit 1")
    fun getToken(): String

    @Query("select uid from userauth limit 1")
    fun getUid(): Int

    @Query("select loggedin from userauth limit 1")
    fun isLoggedIn(): Boolean

    @Query("DELETE FROM userauth")
    fun nukeTable()
}