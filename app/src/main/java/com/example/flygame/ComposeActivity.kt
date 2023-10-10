package com.example.flygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flygame.about_app.AboutAppScreen
import com.example.flygame.about_app.MyNavigationDrawer
import com.example.flygame.gamefield.GameStatus
import com.example.flygame.gamefield.GameViewModel
import com.example.flygame.instructions.AppSettingsState
import com.example.flygame.instructions.AppSettingsViewModel
import com.example.flygame.instructions.InstructionsScreen
import com.example.flygame.instructions.TypeInstruction
import com.example.flygame.settings.BottomSheetSettings
import com.example.flygame.ui.theme.FlyGameTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlyGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    val gameViewModel: GameViewModel = viewModel()
    val gameStatus by gameViewModel.stateGameStatus.collectAsState()
    val textButton = remember { mutableStateOf("Старт") }

    textButton.value =
        when(gameStatus) {
            GameStatus.GIVE_COMMANDS -> "Игра"
            GameStatus.WAITING_RESPONSE -> "Ожидание ответа"
            GameStatus.STOP -> "Старт"
        }


    val appSettingsViewModel: AppSettingsViewModel = viewModel()
    val firstIntroduction by appSettingsViewModel.data.collectAsState()
    val showInstructionsDialog = remember { mutableStateOf(Pair(TypeInstruction.GENERAL, false)) }
//    val showInstructionsDialog = remember { mutableStateOf(false) }

    if (showInstructionsDialog.value.second || firstIntroduction.showInstructions){
        InstructionsScreen(TypeInstruction.GENERAL, showInstructionsDialog){
            appSettingsViewModel.setAppSettings(AppSettingsState.Instructions(false))
        }
    }

    val stateAboutApp = remember { mutableStateOf(false) }
    if (stateAboutApp.value) {
        AboutAppScreen(stateAboutApp)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    MyNavigationDrawer(drawerState, scope)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {

                            stateAboutApp.value = true

                            scope.launch {
                            drawerState.open()
                        }},
                        content = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = ""
                            )
                        }
                    )
                },
                title = { /*TODO*/ },
                actions = {
                    IconButton(onClick = {
                        showInstructionsDialog.value = Pair(TypeInstruction.GENERAL, true)
                    }) {
                        Icon(painterResource(
                            id = R.drawable.ic_question),
                            contentDescription = "Инструкция")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    gameViewModel.startGame()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                enabled = gameStatus == GameStatus.STOP
            ) {
                Text(text = textButton.value, fontSize = 25.sp)
            }

        },
        content = { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .paint(
                        painterResource(id = R.drawable.wood_background_4),
                        contentScale = ContentScale.FillBounds
                    )) {

                BottomSheetSettings(gameViewModel)

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlyGameTheme {
        MainScreen("Android")
    }
}