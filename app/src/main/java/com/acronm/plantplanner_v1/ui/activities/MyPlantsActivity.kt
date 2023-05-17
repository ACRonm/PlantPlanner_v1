package com.acronm.plantplanner_v1.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.acronm.plantplanner_v1.database.AppDatabase
import com.acronm.plantplanner_v1.database.Plants
import com.acronm.plantplanner_v1.ui.theme.PlantPlanner_v1Theme
class IndoorPlants : ComponentActivity() {
    private lateinit var database: AppDatabase // Assuming your database class is named AppDatabase

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantPlanner_v1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        floatingActionButton = {
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
                                PlantForm(onSavePlant = {
                                    expandedState.value = false
                                    // TODO: Save the plant data
                                })
                            }
                        },
                        topBar = {
                            CenterAlignedTopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = {
                                        //back to main activity
                                        finish()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back",
                                        )
                                    }
                                },
                                title = { Text(text = "Indoor Plants") },
                                colors = TopAppBarDefaults.smallTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    titleContentColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    ) { paddingValues ->
                        LazyColumn(modifier = Modifier.padding(paddingValues)) {

                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantForm(onSavePlant: (Plants) -> Unit) {
    val id = remember { mutableStateOf(0) }
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val wateringInterval = remember { mutableStateOf("") }
    val sunlight = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Add New Plant", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = wateringInterval.value,
                    onValueChange = { wateringInterval.value = it },
                    label = { Text(text = "Watering Interval") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )


                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = sunlight.value,
                    onValueChange = { sunlight.value = it },
                    label = { Text(text = "Sunlight") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = image.value,
                    onValueChange = { image.value = it },
                    label = { Text(text = "Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val plant = Plants(
                            id = id.value,
                            name = name.value,
                            description = description.value,
                            wateringInterval = wateringInterval.value.toIntOrNull() ?: 0,
                            sunlight = sunlight.value,
                            image = image.value,
                            createdTimeStamp = System.currentTimeMillis(),
                            modifiedTimestamp = System.currentTimeMillis()
                        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuUi() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text("Label") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        expanded = false
                        selectedOptionText = selectionOption
                        println("Selected option: $selectionOption")
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview
@Composable

fun PreviewPlantForm() {
    PlantPlanner_v1Theme {
        PlantForm(onSavePlant = {})
    }
}