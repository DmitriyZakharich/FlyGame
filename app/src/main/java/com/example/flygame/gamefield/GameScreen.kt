package com.example.flygame.gamefield

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flygame.R
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.Coordinates
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.swipe_box.VolumetricField

@Composable
fun Table(gameViewModel: GameViewModel) {

    val settingsStore = SettingsStore(LocalContext.current)

    val settingsState = settingsStore.getData().collectAsState(SettingsData())
    val settings = settingsState.value

    val isGameProcess by gameViewModel.stateGameProcess.collectAsState()
    val waitingResponse by gameViewModel.stateWaitingResponse.collectAsState()

    if (isGameProcess && !waitingResponse) return   //Скрывать поле во время игры, чтобы представлять его в голове

    if (!settings.isVolume)
        FlatField(settings, gameViewModel)
    else
        VolumetricField(settings, gameViewModel)
}

@Composable
fun FlatField(
    settings: SettingsData,
    gameViewModel: GameViewModel,
    volumeIndex: Int = -1
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
                        id = Coordinates(horizontalX = cellCount, verticalY = rowCount, volumeZ = volumeIndex),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .border(
                                width = 1.dp,
                                color = Color.Blue,
                                shape = AbsoluteCutCornerShape(2)
                            ),
                        gameViewModel
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
    gameViewModel: GameViewModel
) {
    val coordinatesFly by gameViewModel.stateCoordinatesFly.collectAsState()
    val isGameProcess by gameViewModel.stateGameProcess.collectAsState()
    val waitingResponse by gameViewModel.stateWaitingResponse.collectAsState()
    var backgroundColor by remember { mutableStateOf(Color.White) }

    if (isGameProcess)
        backgroundColor = Color.White

    IconButton(
        onClick = {

            if (isGameProcess && waitingResponse){
                backgroundColor = if (id == coordinatesFly){
                    Log.d("fffffffffffTAG", "ПОБЕДА")
                    Color.Green
                } else
                    Color.Red

                gameViewModel.stopGame()
            }

        },
        modifier = modifier
            .background(backgroundColor)
    ) {

        if (id == coordinatesFly && !isGameProcess) {
            Image(
                painter = painterResource(id = R.drawable.icon_fly),
                contentDescription = "icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(text = "${id.horizontalX}${id.verticalY}${id.volumeZ}")
    }
}

@Preview
@Composable
fun PreviewFlatField() {
    FlatField(
        settings = SettingsData(),
        GameViewModel(SettingsStore(context = LocalContext.current.applicationContext))
    )
}