package com.example.flygame.development

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ForFlags() {
    val numbers = (0..9).toList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(numbers.size),

    ) {

        numbers.forEach { _ ->
            items(numbers.size) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(text = "Number")
                    Text(text = "$it", maxLines = 1)
                }
            }
        }


    }
}