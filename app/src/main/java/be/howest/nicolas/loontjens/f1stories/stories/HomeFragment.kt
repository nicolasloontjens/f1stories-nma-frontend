package be.howest.nicolas.loontjens.f1stories.stories

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.databinding.HomeFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.data.Story
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var viewModel: HomeViewModel

    private lateinit var thebinding: HomeFragmentBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

         val binding = HomeFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.storiesList.adapter = HomeStoryAdapter(StoryListener { story ->
            viewModel.onStoryClicked(story)
            viewModel.likeStory(story)
            //i cant modify the score value, so i refresh the page
            findNavController().navigate(R.id.homeFragment)
        },
        StoryListener { story ->
            viewModel.onStoryClicked(story)
            //navigate to comment
            println("comments clicked")
            val action: NavDirections = HomeFragmentDirections.actionHomeFragmentToCommentFragment(story.storyid)
            findNavController().navigate(action)
        },
        StoryListener { story ->
            viewModel.onStoryClicked(story)
            //launch an intent to share to different apps
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "I'm loving these posts on the F1 Stories app, check it out!")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share to:"))
        })
        binding.storiesList.layoutManager = LinearLayoutManager(this.context)
        thebinding = binding;
        return binding.root
        //return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        thebinding.apply {
            burger.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_overviewFragment2)
            }
        }

    }
}