package com.example.flygame.development

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MyTest() {
    var text by rememberSaveable { mutableStateOf("Prova") }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Hello")
        Spacer(modifier = Modifier.weight(1f))
        TextField(
            modifier = Modifier
                .widthIn(1.dp),
            value = "text",
            onValueChange = {
                text = it
            },
            textStyle = TextStyle.Default.copy(
                textAlign = TextAlign.End
            )
        )
    }
}

@Preview
@Composable
fun MyWood() {
//    IconButton(
//        onClick = {},
//        modifier = modifier
//            .background(backgroundColor).clip(RoundedCornerShape(5.dp))
//    ) {
//        Image(
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(RoundedCornerShape(1)),
//
//            painter = painterResource(id = R.drawable.wood_texture),
//            contentDescription = "texture",
//        )
//
//        if (id == coordinatesFly && !isGameProcess) {
//            Image(
//                painter = painterResource(id = R.drawable.icon_fly),
//                contentDescription = "icon",
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//    }
}