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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flygame.R
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.Coordinates
import com.example.flygame.settings.models.SettingsData

@Composable
fun GameScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
//        Table(SettingsStore())
    }

}

@Composable
fun Table(
    settingsStore: SettingsStore,
    answer: (id: Int) -> Unit = {},
    coordinatesFly: Coordinates
) {
    val settingsState = settingsStore.getData().collectAsState(SettingsData())
    val settings = settingsState.value

    if (!settings.isVolume)
        FlatField(settings, answer, coordinatesFly)
    else
        VolumetricField(settings)
}

@Composable
fun FlatField(
    settings: SettingsData,
    answer: (id: Int) -> Unit = {},
    coordinatesFly: Coordinates
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp



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
                        id = Coordinates(horizontalX = cellCount, verticalY = rowCount),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = Color.Blue,
                                shape = AbsoluteCutCornerShape(2)
                            ),
                        answer,
                        coordinatesFly
                    )
                }
            }
        }
    }
}

@Composable
fun MyCell(
    id: Coordinates,
    modifier: Modifier,
    answer: (id: Int) -> Unit = {},
    coordinatesFly: Coordinates
) {
    IconButton(
        onClick = {
            if (id == coordinatesFly)
                Log.d("fffffffffffTAG", "ПОБЕДА")
        },  //verticalY, horizontalX
        modifier = modifier
    ) {

//        val coordinatesFly = state.collectAsState()
//        val state2 by remember { state }
//        val coordinatesState = state2

        Log.d("fffffffffffTAG", "MyCell coordinatesFly = $coordinatesFly")
        Log.d("fffffffffffTAG", "MyCell id = $id")

        if (id == coordinatesFly) {
            Log.d("fffffffffffTAG", "Муха тут")

            Image(
                painter = painterResource(id = R.drawable.icon_fly),
                contentDescription = "icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = "${id.horizontalX}${id.verticalY}${id.volumeZ}")
    }
}

@Composable
fun VolumetricField(settings: SettingsData) {

}

@Preview
@Composable
fun PreviewFlatField() {
    FlatField(
        settings = SettingsData(),
        {},
        Coordinates()
    )
}