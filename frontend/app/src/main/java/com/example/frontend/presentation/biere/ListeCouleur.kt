package com.example.frontend.presentation.biere

import androidx.compose.ui.graphics.Color

fun mapBeerColor(color: String): Color {
    return when (color.lowercase()) {
        "blanche" -> Color(0xFFF7F5E6)
        "jaune paille" -> Color(0xFFFFFACD)
        "dorée" -> Color(0xFFFFD700)
        "ambrée" -> Color(0xFFFFBF00)
        "rousse" -> Color(0xFFD2691E)
        "brune" -> Color(0xFF5C4033)
        "noire" -> Color(0xFF0E0E0E)
        "cuivrée" -> Color(0xFFB87333)
        "rubis" -> Color(0xFF9B111E)
        "ébène" -> Color(0xFF3B3B3B)
        else -> Color.Green // Par défaut, une couleur neutre
    }
}

fun mapFontOverBeer(color: String): Color {
    return when (color.lowercase()) {
        "blanche" -> Color(0xFF0E0E0E)
        "jaune paille" -> Color(0xFF0E0E0E)
        "dorée" -> Color(0xFF0E0E0E)
        "ambrée" -> Color(0xFF4F4F4F)
        "rousse" -> Color(0xFF4F4F4F)
        "brune" -> Color(0xFFFFFFFF)
        "noire" -> Color(0xFFFFFFFF)
        "cuivrée" -> Color(0xFFFFFFFF)
        "rubis" -> Color(0xFFFFFFFF)
        "ébène" -> Color(0xFFFFFFFF)
        else -> Color.Gray // Par défaut, une couleur neutre
    }
}




