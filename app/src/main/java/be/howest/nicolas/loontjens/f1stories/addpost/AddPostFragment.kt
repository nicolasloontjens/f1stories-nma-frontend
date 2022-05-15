package be.howest.nicolas.loontjens.f1stories.addpost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.FileObserver
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import be.howest.nicolas.loontjens.f1stories.R
import be.howest.nicolas.loontjens.f1stories.database.UserRoomDatabase
import be.howest.nicolas.loontjens.f1stories.databinding.AddPostFragmentBinding
import be.howest.nicolas.loontjens.f1stories.network.F1StoriesApi
import be.howest.nicolas.loontjens.f1stories.network.data.Race
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddPostFragment : Fragment() {

    companion object {
        fun newInstance() = AddPostFragment()
    }

    val REQUEST_CODE = 100
    private lateinit var viewModel: AddPostViewModel
    private lateinit var binding: AddPostFragmentBinding
    private lateinit var imageUrl: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AddPostViewModel::class.java]
        binding = AddPostFragmentBinding.inflate(inflater, container, false)
        binding.imagepicker.setOnClickListener{
            addImage()
        }
        binding.submit.setOnClickListener{
            sendPost()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val contxt = this.requireContext()
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = binding.spinner
        var races : List<Race>? = null
        var adapter: ArrayAdapter<Race>? = null
        CoroutineScope(Dispatchers.IO).launch {
            val job = CoroutineScope(Dispatchers.IO).async {
                races = F1StoriesApi.retrofitService.getRaces()
            }
            job.await()
            races = races!!.toMutableList()
            adapter = ArrayAdapter<Race>(contxt,android.R.layout.simple_spinner_dropdown_item,
                races as MutableList<Race>
            )
        }
        //make it wait on the coroutine since this function cant be suspended
        Thread.sleep(500)
        spinner.adapter = adapter
    }

    fun addImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageUrl = data?.data!!
        }
    }

    fun sendPost(){
        val storyContent: String = binding.inputPostContent.text.toString()
        val raceId: Int = binding.spinner.selectedItemId.toInt() + 1
        val country: String = context?.resources?.configuration?.locale!!.displayCountry
        val userToken: String? = getToken()

        /*
        this code should retrieve the file and put it in the form, but i keep getting a file not found error
        val imgFile : File = File(imageUrl.path)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile)
        val body : MultipartBody.Part = MultipartBody.Part.createFormData("image1",imgFile.name,requestFile)*/
        val content : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), storyContent)
        val thecountry : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), country)
        CoroutineScope(Dispatchers.IO).launch {
            if (userToken != null) {
                //F1StoriesApi.retrofitService.addStory(content,thecountry,raceId,body,userToken)
                F1StoriesApi.retrofitService.addStory(content,thecountry,raceId,userToken)
            }
        }
        findNavController().navigate(R.id.homeFragment)
    }

    fun getToken(): String? {
        var token: String? = ""
        CoroutineScope(Dispatchers.IO).launch {
            token = UserRoomDatabase.INSTANCE?.UserDao()?.getToken()
        }
        Thread.sleep(700)
        return token
    }
}