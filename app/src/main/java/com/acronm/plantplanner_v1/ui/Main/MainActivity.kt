package com.acronm.plantplanner_v1.ui.Main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.acronm.plantplanner_v1.GardenPlants
import com.acronm.plantplanner_v1.R
import com.acronm.plantplanner_v1.network.api.PlantApiService
import com.acronm.plantplanner_v1.ui.ImageCard
import com.acronm.plantplanner_v1.ui.myplants.MyPlants
import com.acronm.plantplanner_v1.ui.theme.PlantPlanner_v1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantApiService.ConfigManager.getApiKey(this)

            PlantPlanner_v1Theme {
                CardLayout()
                }
            }
        }
    }

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardLayout() {
    val navController = rememberNavController()

    PlantPlanner_v1Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                                         IconButton(onClick = { /*TODO*/ }) {
                                             Icon(
                                                 imageVector = Icons.Filled.Menu,
                                                 contentDescription = "Menu",
                                             )
                                         }
                        },
                        title = { Text(text = "Plant Planner") },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            ) { paddingValues ->
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(1) {
                            ImageCard(
                                title = "Indoor Plants",
                                description = "Plant Description",
                                imageResourceId = R.drawable.monstera,
                                modifier = Modifier.padding(16.dp),
                                navController = navController,
                                destinationActivity = MyPlants::class.java
                            )
                        }
                    items(1) {
                        ImageCard(
                            title = "Your Garden",
                            description = "Plant Description",
                            imageResourceId = R.drawable.garden,
                            modifier = Modifier.padding(16.dp),
                            navController = navController,
                            destinationActivity = GardenPlants::class.java
                        )
                        }
                    }
                }
            }
        }
    }