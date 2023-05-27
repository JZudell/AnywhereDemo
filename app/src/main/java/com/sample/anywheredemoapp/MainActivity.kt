package com.sample.anywheredemoapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sample.anywheredemoapp.databinding.ActivityMainBinding
import com.sample.anywheredemoapp.databinding.CharacterRecyclerItemBinding
import com.sample.anywheredemoapp.models.ScreenPlayCharacter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    /*

         The two app versions (Simpsons and Wire) are found in the BuildVariants.
         Picture URLS do not work, None of the provided URLS lead to a picture on the web


     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        initRecycler()
        initViewModel()

    }

    private fun initRecycler(){
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = CharacterAdapter()
        }
        binding.searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}


            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (binding.recycler.adapter as CharacterAdapter).filterItems(searchString = p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        binding.clearButton.setOnClickListener {
            binding.searchBar.text.clear()
        }
        // old semantics
//        val recycler = binding.recycler
//        recycler.layoutManager = LinearLayoutManager(this@MainActivity)
//        recycler.adapter = CharacterAdapter()

    }

    private fun initViewModel(){
        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getCharacters().observe(this) { characters ->
            binding.progressBar.visibility = View.GONE
            (binding.recycler.adapter as CharacterAdapter).setSchedule(characters)
        }
        viewModel.makeApiCall()

    }

    internal inner class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

        private val items = ArrayList<ScreenPlayCharacter>()
        private val filteredItems = ArrayList<ScreenPlayCharacter>()

        @SuppressLint("NotifyDataSetChanged")
        fun setSchedule(characters: List<ScreenPlayCharacter> = items) {
            this.items.clear()
            this.filteredItems.clear()
            this.items.addAll(characters)
            this.filteredItems.addAll(characters)
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        fun filterItems(searchString: String){
            this.filteredItems.clear()
            this.filteredItems.addAll(if (searchString.isBlank())items else items.filter { it.Text.lowercase().contains(searchString.lowercase()) })
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = CharacterRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding.root)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            DataBindingUtil.getBinding<CharacterRecyclerItemBinding>(holder.itemView)?.let {
                it.text.text = filteredItems[position].getName()
                it.root.setOnClickListener {
                    openDetails(filteredItems[position])
                }
            }

        }

        override fun getItemCount(): Int {
            return filteredItems.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    }

    private fun openDetails(character: ScreenPlayCharacter) {
        when (binding.rootContainer.tag) {
            "phone" -> {

                val detailsFragment = DetailsFragment.newInstance(character.Icon?.URL,character.getName(),character.getDescription())
                supportFragmentManager.beginTransaction().add(R.id.rootContainer,detailsFragment).addToBackStack(null).commit()

            }
            "tabletLand" -> {
                binding.detailsContainer?.visibility = View.INVISIBLE
                val pictureUrl = character.Icon?.URL
                binding.characterImage?.load(pictureUrl){
                    /*

                         No combination of Icon.url or FirstURL resulted in a working url for a picture.

                     */
                    placeholder(R.drawable.no_image)
                    error(R.drawable.no_image)
                }
                binding.characterName?.text = character.getName()
                binding.characterDescription?.text = character.getDescription()
            }
            "tabletPort" -> {
                binding.detailsContainer?.visibility = View.INVISIBLE
                val pictureUrl = character.Icon?.URL
                binding.characterImage?.load(pictureUrl){
                    /*

                         No combination of Icon.url or FirstURL resulted in a working url for a picture.

                     */
                    placeholder(R.drawable.no_image)
                    error(R.drawable.no_image)

                }
                binding.characterName?.text = character.getName()
                binding.characterDescription?.text = character.getDescription()

            }
        }

    }

}