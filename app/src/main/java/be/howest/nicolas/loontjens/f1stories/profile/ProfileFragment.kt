package be.howest.nicolas.loontjens.f1stories.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.howest.nicolas.loontjens.f1stories.databinding.ProfileFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.data.Profile

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var viewModelFactory: ProfileViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = ProfileViewModelFactory(ProfileFragmentArgs.fromBundle(requireArguments()).uid)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
        binding = ProfileFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.profileStories.adapter = ProfileStoryAdapter(
            //delete
        ProfileStoryListener
        { story ->
            viewModel.onStoryClicked(story)
            viewModel.deleteStory(story, findNavController())
        },
            //edit
        ProfileStoryListener
         { story ->
             viewModel.onStoryClicked(story)
         },
            //go to comments
        ProfileStoryListener
        { story ->
            viewModel.onStoryClicked(story)
        },
            //share
        ProfileStoryListener
         { story ->
             viewModel.onStoryClicked(story)
         })
        binding.profileStories.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserProfile(binding)
    }

}