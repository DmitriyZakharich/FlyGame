package com.example.flygame.gamefield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flygame.R
import com.example.flygame.settings.SettingsStore
import com.example.flygame.settings.models.SettingsData
import com.example.flygame.swipe_box.VolumetricField

@Composable
fun Table(gameViewModel: GameViewModel) {

    val settingsStore = SettingsStore(LocalContext.current)

    val settingsState = settingsStore.getData().collectAsState(SettingsData())
    val settings = settingsState.value

    val gameStatus by gameViewModel.stateGameStatus.collectAsState()

    if (gameStatus == GameStatus.GIVE_COMMANDS && settings.isHideField) return   //Скрывать поле во время команд

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

    var paddingTop = 5.dp
    if (volumeIndex == -1) {
        paddingTop = 50.dp
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenWidth)
            .padding(start = 5.dp, end = 5.dp, top = paddingTop),
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
                            .padding(0.6.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Yellow,
                                shape = AbsoluteCutCornerShape(3)
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
    val gameStatus by gameViewModel.stateGameStatus.collectAsState()
    var backgroundColor by remember { mutableStateOf(Color.White) }

    if (gameStatus != GameStatus.STOP)
        backgroundColor = Color.White

    Box(
        modifier = modifier
            .background(backgroundColor)
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                if (gameStatus != GameStatus.STOP) {
                    backgroundColor = if (id == coordinatesFly) {
                        Color.Green
                    } else
                        Color.Red
                    gameViewModel.stopGame()
                }
            }
    ) {
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(1)),

            painter = painterResource(id = R.drawable.wood_texture),
            contentDescription = "texture",
        )

        if (id == coordinatesFly && gameStatus == GameStatus.STOP) {
            Image(
                painter = painterResource(id = R.drawable.icon_fly_2_w_trans),
                contentDescription = "icon",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
}

@Preview
@Composable
fun PreviewFlatField() {
    FlatField(
        settings = SettingsData(),
        GameViewModel(SettingsStore(context = LocalContext.current.applicationContext), GameMoves())
    )
}