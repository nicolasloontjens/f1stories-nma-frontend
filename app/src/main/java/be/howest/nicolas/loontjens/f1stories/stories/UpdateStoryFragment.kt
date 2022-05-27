package be.howest.nicolas.loontjens.f1stories.stories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.databinding.UpdateStoryFragmentBinding

class UpdateStoryFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateStoryFragment()
    }

    private lateinit var viewModel: UpdateStoryViewModel
    private lateinit var viewModelFactory: UpdateStoryViewModelFactory
    private lateinit var binding: UpdateStoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = UpdateStoryViewModelFactory(UpdateStoryFragmentArgs.fromBundle(requireArguments()).storyId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UpdateStoryViewModel::class.java)
        binding = UpdateStoryFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            updateStoryButton.setOnClickListener {
                viewModel.updateStory(updateStoryContent.text.toString())
                viewModel.goToProfile(findNavController())
            }
        }
    }



}