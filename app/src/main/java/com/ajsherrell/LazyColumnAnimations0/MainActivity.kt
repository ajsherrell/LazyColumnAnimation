package com.ajsherrell.LazyColumnAnimations0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ajsherrell.LazyColumnAnimations0.ui.theme.LazyColumnAnimations0Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private val animals = mapOf(
        "Lion" to "The lion is a species in the family Felidae and a member of the genus Panthera.",
        "Tiger" to "The tiger is the largest living cat species and a member of the genus Panthera.",
        "Elephant" to "Elephants are mammals of the family Elephantidae and the largest existing land animals.",
        "Giraffe" to "The giraffe is an African artiodactyl mammal, the tallest living terrestrial animal and the largest ruminant.",
        "Zebra" to "Zebras are several species of African equids united by their distinctive black-and-white striped coats.",
        "Kangaroo" to "The kangaroo is a marsupial from the family Macropodidae.",
        "Panda" to "The giant panda, also known as the panda bear, is a bear species native to south central China.",
        "Bear" to "Bears are carnivoran mammals of the family Ursidae.",
        "Monkey" to "Monkeys are a group of primates, falling into two major groups: Old World monkeys and New World monkeys.",
        "Gorilla" to "Gorillas are ground-dwelling, predominantly herbivorous apes that inhabit the forest of central Sub-Saharan Africa.",
        "Cheetah" to "The cheetah is a large cat native to Africa and central Iran.",
        "Leopard" to "The leopard is one of the five extant species in the genus Panthera, a member of the Felidae.",
        "Rhinoceros" to "A rhinoceros, commonly abbreviated to rhino, is a member of any of the five extant species of odd-toed ungulates in the family Rhinocerotidae.",
        "Hippopotamus" to "The hippopotamus, also called the hippo, common hippopotamus or river hippopotamus, is a large, mostly herbivorous, semiaquatic mammal and ungulate native to sub-Saharan Africa.",
        "Crocodile" to "Crocodiles are large semiaquatic reptiles that live throughout the tropics in Africa, Asia, the Americas and Australia.",
        "Alligator" to "The alligator is a crocodilian in the genus Alligator of the family Alligatoridae.",
        "Ostrich" to "The ostrich or common ostrich is a species of large flightless bird native to certain large areas of Africa.",
        "Eagle" to "Eagle is the common name for many large birds of prey of the family Accipitridae.",
        "Penguin" to "Penguins are a group of aquatic flightless birds.",
        "Dolphin" to "Dolphins are a widely distributed and diverse group of aquatic mammals.",
        "Whale" to "Whales are a widely distributed and diverse group of fully aquatic placental marine mammals.",
        "Shark" to "Sharks are a group of elasmobranch fish characterized by a cartilaginous skeleton.",
        "Octopus" to "The octopus is a soft-bodied, eight-limbed mollusc of the order Octopoda.",
        "Jellyfish" to "Jellyfish and sea jellies are the informal common names given to the medusa-phase of certain gelatinous members of the subphylum Medusozoa, a major part of the phylum Cnidaria.",
        "Starfish" to "Starfish or sea stars are star-shaped echinoderms belonging to the class Asteroidea."
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
                            AnimalListScaleIn(animals = animals, navController = navController)
                        }

                        composable("animal_detail/{animal}") { backStackEntry ->
                            val animal = backStackEntry.arguments?.getString("animal")
                            val color = animals[animal] ?: Color.LightGray
                            val description = animals[animal] ?: ""
                            if (animal != null) {
                                AnimalDetailContent(animal = animal, description = description)
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

/*
* Here we keep a new AnimalList composable for each animation example.
* */

@Composable
fun AnimalListCrossFade(animals: Map<String, Color>, navController: NavController) {
    LazyColumn {
        items(animals.keys.toList()) { animal ->
            val color = Color.LightGray
            var isVisible by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = animal) {
                isVisible = true
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 2000))
            ) {
                AnimalRow(animal = animal, color = color, navController = navController)
            }
        }
    }
}

@Composable
fun AnimalListSlideIn(animals: Map<String, Color>, navController: NavController) {
    LazyColumn {
        items(animals.keys.toList()) { animal ->
            val color = Color.LightGray
            var isVisible by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = animal) {
                isVisible = true
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(durationMillis = 2000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 2000))
            ) {
                AnimalRow(animal = animal, color = color, navController = navController)
            }
        }
    }
}

@Composable
fun AnimalListScaleIn(animals: Map<String, String>, navController: NavController) {
    LazyColumn {
        items(animals.keys.toList()) { animal ->
            val color = Color.LightGray
            var isVisible by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = animal) {
                isVisible = true
            }

            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn(initialScale = 0.3f, animationSpec = tween(durationMillis = 2000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 2000))
            ) {
                AnimalRow(animal = animal, color = color, navController = navController)
            }
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

    Column {
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
                Text(text = "Change Color")
            }
        }
    }
}

@Composable
fun AnimalDetailScale(animal: String, initialColor: Color) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta) // Add more colors if needed
    var currentColor by remember { mutableStateOf(initialColor) }
    val animatedColor by animateColorAsState(
        targetValue = currentColor,
        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
        label = ""
    )

    // Create a state variable for the scale factor
    var scale by remember { mutableFloatStateOf(1f) }

    // Use LaunchedEffect to periodically change the scale factor
    LaunchedEffect(key1 = true) {
        while (true) {
            scale = if (scale == 1f) 1.2f else 1f
            delay(1000) // delay for 1 second
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
            .scale(scale) // Apply the scale modifier
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
                .padding(PaddingValues(bottom = 60.dp))
        ) {
            Text(text = "Change Color")
        }
    }
}

@Composable
fun AnimalDetailPosition(animal: String, initialColor: Color) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta) // Add more colors if needed
    var currentColor by remember { mutableStateOf(initialColor) }
    val animatedColor by animateColorAsState(
        targetValue = currentColor,
        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
        label = ""
    )

    // Create state variables for the x and y offsets
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    // Use LaunchedEffect to periodically change the offsets
    LaunchedEffect(key1 = true) {
        while (true) {
            offsetX = if (offsetX == 0f) 50f else 0f
            offsetY = if (offsetY == 0f) 50f else 0f
            delay(1000) // delay for 1 second
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
            .offset(x = offsetX.dp, y = offsetY.dp) // Apply the offset modifier
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
                .padding(PaddingValues(bottom = 60.dp))
        ) {
            Text(text = "Change Color")
        }
    }
}

@Composable
fun AnimalDetailFade(animal: String, initialColor: Color) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta) // Add more colors if needed
    var currentColor by remember { mutableStateOf(initialColor) }
    val animatedColor by animateColorAsState(
        targetValue = currentColor,
        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
        label = ""
    )

    // Create a state variable for the alpha value
    var alpha by remember { mutableFloatStateOf(1f) }

    // Use animateFloatAsState to animate the alpha value with a tween
    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
        label = ""
    )

    // Use LaunchedEffect to periodically change the alpha value
    LaunchedEffect(key1 = true) {
        while (true) {
            alpha = if (alpha == 1f) 0f else 1f
            delay(1000) // delay for 1 second
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedColor)
            .alpha(animatedAlpha) // Apply the animatedAlpha modifier
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
                .padding(PaddingValues(bottom = 60.dp))
        ) {
            Text(text = "Change Color")
        }
    }
}

@Composable
fun AnimalDetailSlideIn(animal: String, initialColor: Color) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta) // Add more colors if needed
    var currentColor by remember { mutableStateOf(initialColor) }
    val animatedColor by animateColorAsState(
        targetValue = currentColor,
        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
        label = ""
    )

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(durationMillis = 2000)),
        exit = slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(durationMillis = 2000))
    ) {
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
                    .padding(PaddingValues(bottom = 60.dp))
            ) {
                Text(text = "Change Color")
            }
        }
    }
}

@Composable
fun AnimalDetailContent(animal: String, description: String) {
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta) // Add more colors if needed
//    var currentColor by remember { mutableStateOf(initialColor) }
//    val animatedColor by animateColorAsState(
//        targetValue = currentColor,
//        animationSpec = tween(durationMillis = 2000), // 2000 milliseconds = 2 seconds
//        label = ""
//    )

    var scale by remember { mutableFloatStateOf(0.1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 2000),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        scale = 1f
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(1f)
        ) {
            Text(
                text = animal,
                fontSize = 30.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(animatedScale) // Add this line to animate the scale of the Text
            )
//        Button(
//            onClick = {
//                // Update the color to a random one from the list when the button is clicked
//                currentColor = colors.random()
//            },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(PaddingValues(bottom = 60.dp))
//                .scale(animatedScale) // Add this line to animate the scale of the Button
//        ) {
//            Text(text = "Change Color")
//        }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .weight(2f)
        ) {
            Text(
                text = description,
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(animatedScale) // Add this line to animate the scale of the Text
                    .padding(16.dp)
            )
        }
    }
}
