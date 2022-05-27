package be.howest.nicolas.loontjens.f1stories.overview

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.addrace.ScanCodeActivity
import be.howest.nicolas.loontjens.f1stories.database.UserApplication
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.databinding.OverviewFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OverviewFragment : Fragment() {

    private var binding: OverviewFragmentBinding? = null;

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private val viewModel: OverviewViewModel by activityViewModels{
        CreateLogoutUserRepository(
            (activity?.application as UserApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OverviewFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            gotologout.setOnClickListener{
                viewModel.logout()
                findNavController().navigate(R.id.startFragment)
            }
            gotorace.setOnClickListener{
                val intent = Intent (activity, ScanCodeActivity::class.java)
                activity?.startActivity(intent)
            }
            gotopost.setOnClickListener{
                findNavController().navigate(R.id.action_overviewFragment2_to_addPostFragment)
            }
            gotoprofile.setOnClickListener{
                val uid: Int? = getUid()
                if(uid != null){
                    val action: NavDirections = OverviewFragmentDirections.actionOverviewFragment2ToProfileFragment(uid)
                    findNavController().navigate(action)
                }
            }
        }
    }

    fun getUid(): Int? {
        var uid: Int? = 0
        CoroutineScope(Dispatchers.IO).launch {
            uid = UserRoomDatabase.INSTANCE?.UserDao()?.getUid()
        }
        Thread.sleep(900)
        return uid
    }
}