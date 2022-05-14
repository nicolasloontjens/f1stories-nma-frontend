package be.howest.nicolas.loontjens.f1stories.addrace

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.howest.nicolas.loontjens.f1stories.R

class AddRaceFragment : Fragment() {

    companion object {
        fun newInstance() = AddRaceFragment()
    }

    private lateinit var viewModel: AddRaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_race_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddRaceViewModel::class.java)
        // TODO: Use the ViewModel
    }

}