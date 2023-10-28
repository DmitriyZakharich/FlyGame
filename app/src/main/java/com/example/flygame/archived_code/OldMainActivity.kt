package com.example.flygame.archived_code

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flygame.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OldMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}