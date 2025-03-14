package com.example.frontend.presentation.feature.bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.domain.model.ScheduleDayModel
import java.time.LocalDate
import java.util.Locale


data class Schedule(
    val day: String,
    val hours: String
)

@Composable
fun HorraireOuverture(
    modifier: Modifier = Modifier,
    darkMode: Boolean,
    planning: List<ScheduleDayModel>
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Obtenir le jour actuel en français
    val currentDay = remember {
        LocalDate.now()
            .dayOfWeek
            .getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH)
            .replaceFirstChar { it.uppercase() }
    }

    // Trouver l'horaire du jour actuel
    val currentSchedule = planning.find { it.day == currentDay }

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
                    text = currentDay.translateDayToFrench(),
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
                )

                Spacer(modifier = Modifier.width(70.dp))

                Text(
                    text = if (currentSchedule != null && currentSchedule.opening != null && currentSchedule.closing != null)
                        "${currentSchedule.opening.formatTime()}-${currentSchedule.closing.formatTime()}"
                    else
                        "Fermé",
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
                )

                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Réduire" else "Développer",
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
                    planning.forEach { schedule ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = schedule.day.translateDayToFrench(),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                )
                            )
                            Text(
                                text = if (schedule.opening != null && schedule.closing != null)
                                    "${schedule.opening.formatTime()}-${schedule.closing.formatTime()}"
                                else
                                    "Fermé",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun String?.formatTime(): String {
    // Si la valeur est null, retournez une chaîne vide
    if (this == null) return ""

    return if (this.contains(":")) {
        val parts = this.split(":")
        if (parts.size >= 2) {
            "${parts[0]}:${parts[1]}"
        } else {
            this
        }
    } else {
        this
    }
}

fun String.translateDayToFrench(): String {
    return when (this.lowercase().trim()) {
        "monday" -> "Lundi"
        "tuesday" -> "Mardi"
        "wednesday" -> "Mercredi"
        "thursday" -> "Jeudi"
        "friday" -> "Vendredi"
        "saturday" -> "Samedi"
        "sunday" -> "Dimanche"
        else -> this // Retourne le jour original si non reconnu
    }
}