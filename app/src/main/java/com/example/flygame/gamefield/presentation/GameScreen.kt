package com.example.flygame.gamefield.presentation

import android.util.Log
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.flygame.R
import com.example.flygame.gamefield.domain.Announcer
import com.example.flygame.gamefield.domain.MoveManager
import com.example.flygame.gamefield.models.Coordinates
import com.example.flygame.gamefield.models.DirectionMove
import com.example.flygame.gamefield.models.GameStatus
import com.example.flygame.gamefield.viewmodel.GameViewModel
import com.example.flygame.gamefield.viewmodel.IGameViewModel
import com.example.flygame.settings.repository.SettingsStore
import com.example.flygame.settings.models.SettingsData

@Composable
fun Table(gameViewModel: IGameViewModel) {

    val settingsStore = SettingsStore(LocalContext.current)

    val settingsState = settingsStore.getData().collectAsState(SettingsData())
    val settings = settingsState.value

    val gameStatus by gameViewModel.stateGameStatus.collectAsState()
    val arrowCommand = gameViewModel.stateCommandArrow.collectAsState()

    ArrowCommand(arrowCommand, settings.isHideField)

    if (gameStatus == GameStatus.GIVE_COMMANDS && settings.isHideField) return   //Скрывать поле во время команд

    if (!settings.isVolume)
        FlatField(settings, gameViewModel)
    else
        VolumetricField(settings, gameViewModel)
}

@Composable
fun FlatField(
    settings: SettingsData,
    gameViewModel: IGameViewModel,
    volumeIndex: Int = -1
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val paddingTop = if (settings.isVolume)  0.dp else 55.dp

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = paddingTop, bottom = 5.dp)
            .fillMaxWidth()
            .height(screenWidth)
            .paint(
                painterResource(id = R.drawable.wood_texture),
                contentScale = ContentScale.Crop
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
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
    gameViewModel: IGameViewModel
) {
    val coordinatesFly by gameViewModel.stateCoordinatesFly.collectAsState()
    val gameStatus by gameViewModel.stateGameStatus.collectAsState()
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

    if (gameStatus != GameStatus.STOP)
        backgroundColor = Color.Transparent

    Box(
        modifier = modifier
            .background(backgroundColor)
            .clip(RoundedCornerShape(5.dp))
            .clickable {
                if (gameStatus == GameStatus.WAITING_RESPONSE) {
                    backgroundColor = if (id == coordinatesFly) Color.Green else Color.Red
                    gameViewModel.endGame()
                }
            }
    ) {
//        Image(
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(RoundedCornerShape(1)),
//
//            painter = painterResource(id = R.drawable.wood_texture),
//            contentDescription = "texture",
//        )

        if (id == coordinatesFly && gameStatus == GameStatus.STOP) {
            Image(
                painter = painterResource(id = R.drawable.icon_fly_2_w_trans),
                contentDescription = "icon",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
        Log.d("TAGvvvvvvv", "MyCell: ")
    }
}

@Composable
fun ArrowCommand(arrowCommand: State<DirectionMove>, hideField: Boolean) {
    if (arrowCommand.value == DirectionMove.NULL) return

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val stateImage = remember { mutableIntStateOf(R.drawable.arrow_up) }

    val stateAlpha = remember { mutableFloatStateOf(1f)}
    stateAlpha.floatValue = if (hideField) 1f else 0.55f

    stateImage.intValue = when (arrowCommand.value) {
        DirectionMove.UP -> R.drawable.arrow_up
        DirectionMove.DOWN -> R.drawable.arrow_down
        DirectionMove.LEFT -> R.drawable.arrow_left
        DirectionMove.RIGHT -> R.drawable.arrow_right
        DirectionMove.FORWARD -> R.drawable.arrow_forward
        DirectionMove.BACK -> R.drawable.arrow_back
        DirectionMove.NULL -> R.drawable.arrow_up
    }

    Image(
        painter = painterResource(id = stateImage.intValue),
        contentDescription = "icon",
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 65.dp)
            .fillMaxWidth()
            .height(screenWidth - 20.dp)
            .zIndex(1f)
            .alpha(stateAlpha.floatValue)
    )
}

@Preview
@Composable
fun PreviewFlatField() {
    FlatField(
        settings = SettingsData(),
        GameViewModel(SettingsStore(context = LocalContext.current.applicationContext), MoveManager(), Announcer())
    )
}