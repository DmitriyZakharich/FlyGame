package com.example.flygame.statistic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flygame.R


class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StatisticFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }




}