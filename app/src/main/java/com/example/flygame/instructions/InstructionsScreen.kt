package com.example.flygame.instructions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun InstructionsScreen(typeInstruction: TypeInstruction, showInstructionsDialog: MutableState<Boolean>, closeDialog: () -> Unit) {

    val viewModel: InstructionsViewModel = viewModel()
    viewModel.loadInstruction(typeInstruction)
    val instructionData by viewModel.stateInstructionData.collectAsState()
    val instructionsSize by viewModel.stateInstructionsSize.collectAsState()

    var currentStepProgressBar by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = {
            showInstructionsDialog.value = false
            closeDialog()
            viewModel.restart()
        }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Red.copy(alpha = 0.8F)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {

                    Image(
                        painter = painterResource(id = instructionData.image),
                        contentDescription = "Exit app",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillWidth
                    )
                }

                Text(
                    text = instructionData.instruction,
                    modifier = Modifier.padding(8.dp)
                )

                StepsProgressBar(
                    modifier = Modifier.fillMaxWidth(),
                    numberOfSteps = instructionsSize,
                    currentStep = currentStepProgressBar
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = {
                            showInstructionsDialog.value = false
                            closeDialog()
                            viewModel.restart()
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Exit")
                    }

                    if (currentStepProgressBar < instructionsSize - 1)
                        Button(
                            onClick = {
                                viewModel.getNextInstruction()
                                currentStepProgressBar++
                            },
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1F)
                        ) {
                            Text(text = "Next")
                        }
                }
            }
        }
    }
}