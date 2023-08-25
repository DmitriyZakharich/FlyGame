package com.example.flygame.gamefield

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flygame.R
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.SettingsData

@Composable
fun GameScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
//        Table(SettingsStore())



    }

}

@Composable
fun Table(settingsStore: SettingsStore) {
    val settingsState = settingsStore.getData().collectAsState(SettingsData())
    val settings = settingsState.value

    if (!settings.isVolume)
        FlatField(settings)
    else
        VolumetricField(settings)
}

@Composable
fun FlatField(settings: SettingsData) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Log.d("11111111111111TAG", "screenWidth: $screenWidth")
    Log.d("11111111111111TAG", "screenHeight: $screenHeight")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenWidth),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (rowCount in 0 until settings.tableSize) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (cellCount in 0 until settings.tableSize) {
                    MyCell(
                        Pair(cellCount, rowCount),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = Color.Blue,
                                shape = AbsoluteCutCornerShape(2)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun MyCell(id: Pair<Int, Int>, modifier: Modifier) {
    IconButton(
        onClick = {},
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_fly),
            contentDescription = "icon",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun VolumetricField(settings: SettingsData) {

}

@Preview
@Composable
fun PreviewFlatField() {
    FlatField(settings = SettingsData())
}