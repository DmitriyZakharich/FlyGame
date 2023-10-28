package com.example.flygame.archived_code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flygame.R
import com.example.flygame.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.fragment_menu), View.OnClickListener {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.buttonToGame.setOnClickListener(this)
        binding.buttonInstructions.setOnClickListener(this)
        binding.buttonSettings.setOnClickListener(this)
        return binding.root
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonToGame -> findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
            R.id.buttonInstructions -> findNavController().navigate(R.id.action_menuFragment_to_instructionsFragment)
            R.id.buttonSettings -> findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
        }
    }
}