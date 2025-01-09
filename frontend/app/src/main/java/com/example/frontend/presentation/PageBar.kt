package com.example.frontend.presentation

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

@Composable
fun PageBar(modifier: Modifier, navHostController: NavHostController) {

    val scrollState = rememberScrollState()

    val openingHours = listOf(
        "Lundi" to "09:00 - 18:00",
        "Mardi" to "09:00 - 18:00",
        "Mercredi" to "09:00 - 18:00",
        "Jeudi" to "09:00 - 18:00",
        "Vendredi" to "09:00 - 18:00",
        "Samedi" to "10:00 - 16:00",
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
        Image(
            painter = painterResource(id = R.drawable.sharkpool), // Utilisez le nom sans extension
            contentDescription = "Shark Pool Image",
            contentScale = ContentScale.FillWidth, // Ajuste l'image
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Shark Pool",
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(10.dp).align(Alignment.Start)
        )

        Text(
            text = "Bar karaoké",
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Black
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
                            color = Color.Gray
                        )
                    )
                    // Affichage des horaires
                    Text(
                        text = item.second,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
        }

    }
}