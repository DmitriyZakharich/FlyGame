package com.example.flygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flygame.gamefield.GameViewModel
import com.example.flygame.settings.BottomSheetSettings
import com.example.flygame.ui.theme.FlyGameTheme
import dagger.hilt.android.AndroidEntryPoint

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
    val answer: (Int) -> Unit = { id -> gameViewModel.cellClickListener(id) }

    val coordinatesFly by gameViewModel.stateCoordinatesFly.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { },
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
                    IconButton(onClick = { }) {
                        Icon(painterResource(
                            id = R.drawable.ic_question),
                            contentDescription = "Инструкция")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { gameViewModel.startGame() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(text = "Старт", fontSize = 25.sp)
            }

        },
        content = { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.Red)) {
                BottomSheetSettings(answer, coordinatesFly)

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