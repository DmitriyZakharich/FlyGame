package com.example.flygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flygame.gamefield.FlatField
import com.example.flygame.settings.BottomSheetSettings
import com.example.flygame.settings.models.SettingsData
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
/**
 * Добавить bottomsheet в нижний бар?
 * */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()

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
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {

                    }
                }
            ) {
//                Text("Show snackbar")
                Icon(imageVector = Icons.Default.Settings, contentDescription = "")
            }
        },
        bottomBar = {
//                    BottomAppBar {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)) {
                            Text(text = "Старт", fontSize = 25.sp)
                        }
//                    }
        },
        content = { innerPadding ->

            Column(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                ){
                    FlatField(settings = SettingsData())
                }
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .fillMaxWidth()
                        .weight(1f)
                ){
                    BottomSheetSettings()
                }


            }

        }
    )



//    Column(modifier = Modifier.fillMaxSize()) {
//        Box(
//            Modifier
//                .fillMaxSize()
//                .weight(1f)) {
//            FlatField(SettingsData())
//
//        }
//
//        Text(text = "Привет,Мир", color = Color.Red, modifier = Modifier
//            .wrapContentSize()
//            .weight(1f))
//
//    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlyGameTheme {
        MainScreen("Android")
    }
}