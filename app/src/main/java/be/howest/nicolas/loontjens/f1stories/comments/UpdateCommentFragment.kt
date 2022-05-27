package be.howest.nicolas.loontjens.f1stories.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.databinding.UpdateCommentFragmentBinding

class UpdateCommentFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateCommentFragment()
    }
    private lateinit var viewModelFactory: UpdateCommentViewModelFactory
    private lateinit var viewModel: UpdateCommentViewModel
    private lateinit var binding: UpdateCommentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = UpdateCommentViewModelFactory(UpdateCommentFragmentArgs.fromBundle(requireArguments()).comment)
        viewModel = ViewModelProvider(this,viewModelFactory).get(UpdateCommentViewModel::class.java)
        binding = UpdateCommentFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.showComment(binding)
        binding.updateCommentButton.setOnClickListener{
            viewModel.updateComment(findNavController())
        }
    }
}