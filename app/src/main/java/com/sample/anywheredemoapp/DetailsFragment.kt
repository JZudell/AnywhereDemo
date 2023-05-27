package com.sample.anywheredemoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.sample.anywheredemoapp.databinding.CharacterDetailsViewBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: CharacterDetailsViewBinding


    companion object {
        private const val EXTRA_URL = "extra_url"
        private const val EXTRA_NAME = "extra_name"
        private const val EXTRA_DESCRIPTION = "extra_description"
        fun newInstance(url: String? = null, name: String?, description: String?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_URL, url)
                putString(EXTRA_NAME, name)
                putString(EXTRA_DESCRIPTION, description)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = CharacterDetailsViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        populateView()
    }

    private fun populateView(){
        val name = arguments?.getString(EXTRA_NAME)
        name?.let { binding.characterName.text = it }

        val description = arguments?.getString(EXTRA_DESCRIPTION)
        description?.let { binding.characterDescription.text = it }

        val url = arguments?.getString(EXTRA_URL)
        binding.characterImage.load(url){

            /*

                         No combination of Icon.url or FirstURL resulted in a working url for a picture.

                     */

            context?.let {

                placeholder(ContextCompat.getDrawable(it,R.drawable.no_image))
                error(ContextCompat.getDrawable(it,R.drawable.no_image))
            }
        }

    }


}