package com.example.flygame.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flygame.R
import com.example.flygame.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings), AdapterView.OnItemSelectedListener,
    View.OnClickListener {


    private lateinit var binding: FragmentSettingsBinding
    private var viewModel: SettingsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        subscriptionLiveData()
    }

    private fun subscriptionLiveData() {
        val liveDataIsVolumeTable = viewModel?.liveDataIsVolumeTable?.observe(
            viewLifecycleOwner, { binding.switchVolumeTable.isChecked = it })

        val liveDataTableSize = viewModel?.liveDataTableSize?.observe(
            viewLifecycleOwner, { binding.spinnerTableSize.setSelection(it) })
    }

    private fun initViews() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.table_size,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTableSize.adapter = adapter
        binding.spinnerTableSize.onItemSelectedListener = this


        binding.spinnerNumberOfSteps.onItemSelectedListener = this
        binding.spinnerSpeed.onItemSelectedListener = this

        binding.switchVolumeTable.setOnClickListener(this)
        binding.switchVoice.setOnClickListener(this)
        binding.switchFlipField.setOnClickListener(this)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel?.spinnerItemSelected(parent?.id!!, position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.switchVolumeTable.id -> viewModel?.clickListener(
                binding.switchVolumeTable.id,
                (v as SwitchCompat).isChecked
            )
        }
    }

}


