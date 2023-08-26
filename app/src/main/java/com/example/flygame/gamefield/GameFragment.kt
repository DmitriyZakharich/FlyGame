package com.example.flygame.gamefield

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.flygame.databinding.FragmentGameBinding


class GameFragment : Fragment(), View.OnClickListener {

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
        initViewModel()
        createGameField()
        subscribeLiveData()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
    }

    private fun createGameField() {
        val creator = TableCreator(requireContext(), viewModel!!)
        binding.gameFieldContainer.addView(creator.getGameField())
    }

    private fun subscribeLiveData() {
//        viewModel?.stateCoordinatesFly?.observe(requireActivity()
//        ) { println(it) }
//
//        viewModel?.liveDataGameProcess?.observe(requireActivity()
//        ) {
//            if (it) {
//                binding.buttonStartGame.visibility = View.INVISIBLE
//                binding.progressBar.visibility = View.VISIBLE
//            } else {
//                binding.buttonStartGame.visibility = View.VISIBLE
//                binding.progressBar.visibility = View.INVISIBLE
//            }
//        }
    }

    private fun initViews() {
        binding.buttonStartGame.setOnClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {
        Log.d("tagTag123321", "onClick: ")
        viewModel?.startGame()
    }
}