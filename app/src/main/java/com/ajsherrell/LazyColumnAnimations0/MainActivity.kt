package com.ajsherrell.LazyColumnAnimations0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    private val animals = mapOf(
        "Lion" to Color.Yellow,
        "Tiger" to Color(0xFFFFA500), // Orange
        "Elephant" to Color.Gray,
        "Giraffe" to Color(0xFFA52A2A), // Brown
        "Zebra" to Color.LightGray,
        "Kangaroo" to Color(0xFFA52A2A), // Brown
        "Panda" to Color.LightGray,
        "Bear" to Color(0xFFA52A2A), // Brown
        "Monkey" to Color(0xFFA52A2A), // Brown
        "Gorilla" to Color.Magenta,
        "Cheetah" to Color.Yellow,
        "Leopard" to Color.Magenta,
        "Rhinoceros" to Color.Gray,
        "Hippopotamus" to Color.Gray,
        "Crocodile" to Color.Green,
        "Alligator" to Color.Green,
        "Ostrich" to Color(0xFFA52A2A), // Brown
        "Eagle" to Color(0xFFA52A2A), // Brown
        "Penguin" to Color.LightGray,
        "Dolphin" to Color.Gray,
        "Whale" to Color.Blue,
        "Shark" to Color.Gray,
        "Octopus" to Color(0xFF800080), // Purple
        "Jellyfish" to Color(0xFFFFC0CB), // Pink
        "Starfish" to Color(0xFFFFA500) // Orange
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
                            val color = animals[animal] ?: Color.LightGray
                            if (animal != null) {
                                AnimalDetail(animal = animal, initialColor = color)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalList(animals: Map<String, Color>, navController: NavController) {
    LazyColumn {
        items(animals.keys.toList()) { animal ->
            val color = animals[animal] ?: Color.LightGray
            AnimalRow(animal = animal, color = color, navController = navController)
        }
    }
}

@Composable
fun AnimalRow(animal: String, color: Color, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("animal_detail/$animal")
            }
            .padding(4.dp)
            .background(color = color)
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
fun AnimalDetail(animal: String, initialColor: Color) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta) // Add more colors if needed
    var currentColor by remember { mutableStateOf(initialColor) }
    val animatedColor by animateColorAsState(
        targetValue = currentColor,
        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
    ) {
        Text(
            text = "Detail page for $animal",
            modifier = Modifier.align(Alignment.Center)
        )
        Button(
            onClick = {
                // Update the color to a random one from the list when the button is clicked
                currentColor = colors.random()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(PaddingValues(bottom = 16.dp))
        ) {
            Text(text = "Color")
        }
    }
}
