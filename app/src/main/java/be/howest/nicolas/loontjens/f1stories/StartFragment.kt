package be.howest.nicolas.loontjens.f1stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.database.UserApplication
import be.howest.nicolas.loontjens.f1stories.databinding.FragmentStartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class StartFragment : Fragment() {

    private var binding: FragmentStartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var loggedin = false;
        Thread{
            val repo = (activity?.application as UserApplication).repository
            loggedin = repo.isLoggedIn()
        }.start()

        binding?.apply{
            Register.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registerFragment)
            }
            Login.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }
        }
        Thread.sleep(200)
        if(loggedin){
            findNavController().navigate(R.id.action_startFragment_to_homeFragment)
        }
    }
}