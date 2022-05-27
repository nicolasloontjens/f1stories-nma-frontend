package be.howest.nicolas.loontjens.f1stories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import be.howest.nicolas.loontjens.f1stories.addrace.ScanCodeActivity
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        bottomNavigationView = findViewById(R.id.bottomnavview)
        findViewById<ActionMenuItemView?>(R.id.homebottomnav).visibility = View.INVISIBLE
        findViewById<ActionMenuItemView?>(R.id.createstorybottomnav).visibility = View.INVISIBLE
        findViewById<ActionMenuItemView?>(R.id.addracebottomnav).visibility = View.INVISIBLE
        findViewById<ActionMenuItemView?>(R.id.profilebottomnav).visibility = View.INVISIBLE

        bottomNavigationView.background = null

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.homebottomnav -> {
                    navController.navigate(R.id.homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.addracebottomnav -> {
                    val intent = Intent (this, ScanCodeActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.createstorybottomnav -> {
                    navController.navigate(R.id.addPostFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profilebottomnav -> {
                    val uid = getUid()
                    val bundle = Bundle()
                    if (uid != null) {
                        bundle.putInt("uid", uid)
                    }
                    navController.navigate(R.id.profileFragment, bundle)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun getUid(): Int?{
        var uid: Int? = null
        CoroutineScope(Dispatchers.IO).launch {
            uid = UserRoomDatabase.INSTANCE?.UserDao()?.getUid()
        }
        Thread.sleep(700)
        return uid
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}