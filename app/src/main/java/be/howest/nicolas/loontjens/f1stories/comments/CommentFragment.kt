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
import be.howest.nicolas.loontjens.f1stories.databinding.CommentFragmentBinding
import be.howest.nicolas.loontjens.f1stories.databinding.CommentFragmentBindingImpl
import be.howest.nicolas.loontjens.f1stories.stories.HomeFragmentDirections

class CommentFragment : Fragment() {

    companion object {
        fun newInstance() = CommentFragment()
    }

    private lateinit var viewModel: CommentViewModel
    private lateinit var binding: CommentFragmentBinding
    private lateinit var viewModelFactory: CommentViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = CommentViewModelFactory(CommentFragmentArgs.fromBundle(requireArguments()).storyId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommentViewModel::class.java)
        binding = CommentFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listcomments.adapter = CommentsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addComment.setOnClickListener{
            viewModel.goToAddComments(findNavController())
        }
    }





}