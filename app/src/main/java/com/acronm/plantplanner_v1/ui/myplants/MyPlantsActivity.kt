package com.acronm.plantplanner_v1.ui.myplants

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.acronm.plantplanner_v1.database.AppDatabase
import com.acronm.plantplanner_v1.ui.theme.PlantPlanner_v1Theme


class MyPlants : ComponentActivity() {

    private lateinit var viewModel: MyPlantsViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MyPlantsViewModel::class.java]

        setContent {
            PlantPlanner_v1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(

                        floatingActionButton = { CreateFloatingActionButton(viewModel = viewModel) },
                        topBar = {
                            CenterAlignedTopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = {
                                        finish()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back",
                                        )
                                    }
                                },
                                title = { Text(text = "Indoor Plants") },
                                )
                        }
                    ) { paddingValues ->
                        val plants by viewModel.plantsLiveData.observeAsState()

                        println("plants: $plants")

                        LazyColumn(modifier = Modifier.padding(paddingValues)) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "My Plants",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            items(plants.orEmpty()) { plant ->
                                Row() {
                                    Text(text = plant.name)
                                    IconButton(onClick = {
                                        viewModel.deletePlant(plant)
                                    }) {
                                        Icon(
                                            imageVector = Outlined.Delete,
                                            contentDescription = "Back",
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
    @Composable
    private fun CreateFloatingActionButton(viewModel: MyPlantsViewModel) {
        val expandedState = remember { mutableStateOf(false) }
        ExtendedFloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            ),
            onClick = {
                expandedState.value = !expandedState.value
            },
        ) {
            Text(text = "Add Plant")
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "New plant",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
        if (expandedState.value) {
            PlantForm(
                onSavePlant = {
                    expandedState.value = false
                },
                onDismiss = {
                    expandedState.value = false
                },
                viewModel = viewModel
            )
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantForm(onSavePlant: (AppDatabase.Plant) -> Unit, onDismiss: () -> Unit, viewModel: MyPlantsViewModel) {
    val id = remember { mutableStateOf(0) }
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val wateringInterval = remember { mutableStateOf("") }
    val sunlight = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }

    val tag = "PlantForm"

    Dialog(
        onDismissRequest = {
            onDismiss()
        }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Add New Plant", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = if (name.value.isNotEmpty()) {
                        {
                            IconButton(onClick = {
                                name.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                )
                            }
                        }
                    } else null)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = if (description.value.isNotEmpty()) {
                        {
                            IconButton(onClick = {
                                description.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                )
                            }
                        }
                    } else null)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = wateringInterval.value,
                    onValueChange = { wateringInterval.value = it },
                    label = { Text(text = "Watering Interval") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    trailingIcon = if (wateringInterval.value.isNotEmpty()) {
                        {
                            IconButton(onClick = {
                                wateringInterval.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                )
                            }
                        }
                    } else null)


                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = sunlight.value,
                    onValueChange = { sunlight.value = it },
                    label = { Text(text = "Sunlight") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = if (sunlight.value.isNotEmpty()) {
                        {
                            IconButton(onClick = {
                                sunlight.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                )
                            }
                        }
                    } else null)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = image.value,
                    onValueChange = { image.value = it },
                    label = { Text(text = "Image URL") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = if (image.value.isNotEmpty()) {
                        {
                            IconButton(onClick = {
                                image.value = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                )
                            }
                        }
                    } else null)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val plant = AppDatabase.Plant(
                            id = id.value,
                            name = name.value,
                            description = description.value,
                            wateringInterval = wateringInterval.value.toIntOrNull() ?: 0,
                            sunlight = sunlight.value,
                            image = image.value,
                            createdTimeStamp = System.currentTimeMillis(),
                            modifiedTimestamp = System.currentTimeMillis()
                        )

                        Log.d(tag, "plant: $plant")

                        viewModel.insertPlant(plant)
                        onSavePlant(plant)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.value.isNotBlank() && description.value.isNotBlank() && wateringInterval.value.isNotBlank() && sunlight.value.isNotBlank() && image.value.isNotBlank()
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}




