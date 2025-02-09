package com.example.frontend.presentation.feature.bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.util.Locale


data class Schedule(
    val day: String,
    val hours: String
)

@Composable
fun HorraireOuverture(
    modifier: Modifier = Modifier,
    darkMode: Boolean
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Liste des horaires
    val schedules = remember {
        listOf(
            Schedule("Lundi", "9h00 - 18h00"),
            Schedule("Mardi", "9h00 - 18h00"),
            Schedule("Mercredi", "9h00 - 18h00"),
            Schedule("Jeudi", "9h00 - 18h00"),
            Schedule("Vendredi", "9h00 - 18h00"),
            Schedule("Samedi", "10h00 - 17h00"),
            Schedule("Dimanche", "Fermé")
        )
    }

    // Obtenir le jour actuel en français
    val currentDay = remember {
        LocalDate.now()
            .dayOfWeek
            .getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH)
            .replaceFirstChar { it.uppercase() }
    }

    // Trouver l'horaire du jour actuel
    val currentSchedule = schedules.find { it.day == currentDay }

    Box(modifier = modifier) {
        Column {
            // Ligne cliquable qui affiche le jour actuel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentDay,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (darkMode) Color.Black else Color.White
                    )
                )

                Spacer(modifier = Modifier.width(70.dp))

                Text(
                    text = currentSchedule?.hours ?: "",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (darkMode) Color.Black else Color.White
                    )
                )

                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Réduire" else "Développer",
                    tint = if (darkMode) Color.Black else Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Liste déroulante des horaires
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    schedules.forEach { schedule ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = schedule.day,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = if (darkMode) Color.Black else Color.White
                                )
                            )
                            Text(
                                text = schedule.hours,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = if (darkMode) Color.Black else Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}