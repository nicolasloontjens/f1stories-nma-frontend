package be.howest.nicolas.loontjens.f1stories.comments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.databinding.CommentFragmentBinding
import be.howest.nicolas.loontjens.f1stories.databinding.CommentFragmentBindingImpl
import be.howest.nicolas.loontjens.f1stories.network.data.Comment
import be.howest.nicolas.loontjens.f1stories.stories.HomeFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        binding.listcomments.adapter = CommentsAdapter(
            //edit comment
        CommentListener { comment ->
            viewModel.onCommentClicked(comment)
            editComment(comment)
        },
            //delete comment
        CommentListener { comment ->
            viewModel.onCommentClicked(comment)
            deleteComment(comment)
        })
        return binding.root
    }

    private fun editComment(comment: Comment){
        val uid = getUid()
        if(comment.userid == uid){

        }else{
            Toast.makeText(context, "You can't edit someone else's comment!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteComment(comment: Comment){
        val uid = getUid()
        if(comment.userid == uid){
            viewModel.deleteComment(findNavController())
        }else{
            Toast.makeText(context, "You can't delete someone else's comment!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUid(): Int?{
        var uid: Int? = null
        CoroutineScope(Dispatchers.IO).launch {
            uid = UserRoomDatabase.INSTANCE?.UserDao()?.getUid()
        }
        Thread.sleep(700)
        return uid
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addComment.setOnClickListener{
            viewModel.goToAddComments(findNavController())
        }
    }





}