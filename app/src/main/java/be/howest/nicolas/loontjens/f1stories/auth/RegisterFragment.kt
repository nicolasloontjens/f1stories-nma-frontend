package be.howest.nicolas.loontjens.f1stories.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBindings
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.database.UserApplication
import be.howest.nicolas.loontjens.f1stories.databinding.FragmentStartBinding
import be.howest.nicolas.loontjens.f1stories.databinding.RegisterFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi

class RegisterFragment : Fragment() {

    private var binding: RegisterFragmentBinding? = null

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by activityViewModels{
        CreateUserViewModelFactory(
            (activity?.application as UserApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply{
            RegisterButton.setOnClickListener{
                viewModel.register(editTextUsername.text.toString(),editTextTextPassword.text.toString())
            }
        }
    }



}