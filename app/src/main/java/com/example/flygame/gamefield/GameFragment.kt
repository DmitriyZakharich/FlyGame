package com.example.flygame.gamefield

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.flygame.R
import com.example.flygame.databinding.FragmentGameBinding
import com.example.flygame.databinding.FragmentSettingsBinding


class GameFragment : Fragment(R.layout.fragment_game) {

    private var viewModel: GameViewModel? = null
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createGameField()
        initViewModel()
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel?.getLiveDataCoordinates()?.observe(requireActivity(),
            { println(it) })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
    }

    private fun createGameField() {
        val creator = TableCreator(requireContext())
        binding.gameFieldContainer.addView(creator.getGameField())
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}