package com.example.frontend.presentation.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.frontend.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.frontend.presentation.navigation.Screen
import com.example.frontend.presentation.components.Caroussel

@Composable
fun PageBar(modifier: Modifier, navHostController: NavHostController) {

    val darkMode = !isSystemInDarkTheme();
    val scrollState = rememberScrollState()

    val openingHours = listOf(
        "Lundi" to "09:00 - 22:00",
        "Mardi" to "09:00 - 22:00",
        "Mercredi" to "10:00 - 22:00",
        "Jeudi" to "10:00 - 23:00",
        "Vendredi" to "09:00 - 00:00",
        "Samedi" to "10:00 - 00:00",
        "Dimanche" to "Fermé"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .verticalScroll(scrollState).padding(2.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box(){
            Image(
                painter = painterResource(id = R.drawable.sharkpool), // Utilisez le nom sans extension
                contentDescription = "Shark Pool Image",
                contentScale = ContentScale.FillWidth, // Ajuste l'image
                modifier = Modifier.fillMaxWidth()
            )
            IconButton(onClick = { navHostController.navigate(Screen.ListBiere.route) }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }

        Text(
            text = "Shark Pool",
            style = TextStyle(
                fontSize = 24.sp,
                color = if (darkMode) Color.Black else Color.White
            ),
            modifier = Modifier.padding(10.dp).align(Alignment.Start)
        )

        Text(
            text = "Bar karaoké",
            style = TextStyle(
                fontSize = 12.sp,
                color = if (darkMode) Color.Black else Color.White
            ),
            modifier = Modifier.padding(bottom = 10.dp).align(Alignment.Start)
        )

        Column(
            modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            for (item in openingHours) {
                Row(
                    modifier = Modifier.width(250.dp)
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Affichage du jour
                    Text(
                        text = item.first,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = if (darkMode) Color.Black else Color.White
                        )
                    )
                    // Affichage des horaires
                    Text(
                        text = item.second,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = if (darkMode) Color.Black else Color.White
                        )
                    )
                }
            }
            Caroussel()
        }
    }

}