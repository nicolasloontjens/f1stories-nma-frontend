package be.howest.nicolas.loontjens.f1stories.comments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.databinding.AddCommentFragmentBinding

class AddComment : Fragment() {

    companion object {
        fun newInstance() = AddComment()
    }

    private lateinit var viewModel: AddCommentViewModel
    private lateinit var binding: AddCommentFragmentBinding
    private lateinit var viewModelFactory: AddCommentViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = AddCommentViewModelFactory(AddCommentArgs.fromBundle(requireArguments()).storyId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddCommentViewModel::class.java)
        binding = AddCommentFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            postCommentButton.setOnClickListener {
                viewModel.sendCommentToAPI(addCommentContent.text.toString())
                Thread.sleep(500)
                viewModel.goToComments(findNavController())
            }
        }
    }



}