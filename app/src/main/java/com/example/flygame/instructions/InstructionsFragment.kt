package com.example.flygame.instructions

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.flygame.R


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {

    private var instructionsData: LiveData<ArrayList<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[InstructionsViewModel::class.java]
        instructionsData = viewModel.getInstructionsData()

    }

    override fun onResume() {
        super.onResume()
        val textureView1 = requireView().findViewById<TextView>(R.id.textview_instruction1)
        val textureView2 = requireView().findViewById<TextView>(R.id.textview_instruction2)
        instructionsData?.observe(this,
            {
                textureView1.text = it[0]
                textureView2.text = it[1]
            })
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InstructionsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
