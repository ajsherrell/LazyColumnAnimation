package com.ajsherrell.LazyColumnAnimations0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajsherrell.LazyColumnAnimations0.ui.theme.LazyColumnAnimations0Theme

class MainActivity : ComponentActivity() {

    private val animals = listOf(
        "Lion", "Tiger", "Elephant", "Giraffe", "Zebra",
        "Kangaroo", "Panda", "Bear", "Monkey", "Gorilla",
        "Cheetah", "Leopard", "Rhinoceros", "Hippopotamus", "Crocodile",
        "Alligator", "Ostrich", "Eagle", "Penguin", "Dolphin",
        "Whale", "Shark", "Octopus", "Jellyfish", "Starfish"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            LazyColumnAnimations0Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "animal_list") {

                        composable("animal_list") {
                            AnimalList(animals = animals, navController = navController)
                        }

                        composable("animal_detail/{animal}") { backStackEntry ->
                            val animal = backStackEntry.arguments?.getString("animal")
                            if (animal != null) {
                                AnimalDetail(animal = animal)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalList(animals: List<String>, navController: NavController) {
    LazyColumn {
        items(animals) { animal ->
            AnimalRow(animal = animal, navController = navController)
        }
    }
}

@Composable
fun AnimalRow(animal: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("animal_detail/$animal")
            }
            .padding(4.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = animal,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun AnimalDetail(animal: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Text(
            text = "Detail page for $animal",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
