package be.howest.nicolas.loontjens.f1stories.stories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.databinding.HomeFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.viewModelScope.launch {
            val races = F1StoriesApi.retrofitService.getRaces()
            println(races)

            val stories = F1StoriesApi.retrofitService.getStories()
            println(stories)
        }

        val binding = HomeFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.storiesList.adapter = HomeStoryAdapter()
        binding.storiesList.layoutManager = LinearLayoutManager(this.context)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

}