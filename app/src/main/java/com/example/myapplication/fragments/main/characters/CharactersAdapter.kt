package com.example.myapplication.fragments.main.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CharacterRawBinding
import com.example.myapplication.model.Characters
import com.example.myapplication.model.Location

class CharactersAdapter(val listener: RawClickListener):RecyclerView.Adapter<CharactersAdapter.CharacterHolder>() {
    private var characterList = emptyList<Characters>()
    class CharacterHolder(val binding: CharacterRawBinding, val listener: RawClickListener):RecyclerView.ViewHolder(binding.root){
    }


    interface RawClickListener{
        fun deleteCharacter(characters: Characters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val binding = CharacterRawBinding.inflate(LayoutInflater.from(parent.context))
        return CharacterHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val currentChar = characterList[position]

        holder.binding.textView.setText(currentChar.char_name)
        holder.binding.characterImage.setImageURI(currentChar.profile_image)

        holder.binding.characterRawLayout.setOnClickListener{
            val action = CharacterFragmentDirections.actionCharacterFragmentToCharacterDetails(currentChar)
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.deleteButton.setOnClickListener{
            listener.deleteCharacter(currentChar)
        }
    }

    fun setData(characters: List<Characters>) {
        this.characterList = characters
        notifyDataSetChanged()

    }
}