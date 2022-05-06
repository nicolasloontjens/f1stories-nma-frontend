package be.howest.nicolas.loontjens.f1stories.database
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UserAuth")
data class UserAuthItem(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val uid: Int=0,
    val token: String = "",
    val loggedin: Boolean=false
):Parcelable
