package com.example.frontend.presentation.biere

import androidx.compose.ui.graphics.Color

fun mapBeerColor(color: String): Color {
    return when (color.lowercase()) {
        "biere_blanche" -> Color(0xFFF7F5E6)
        "biere_blonde" -> Color(0xFFFFFACD)
        "biere_dorée" -> Color(0xFFFFD700)
        "biere_ambre" -> Color(0xFFFFBF00)
        "biere_rousse" -> Color(0xFFD2691E)
        "biere_brune" -> Color(0xFF5C4033)
        "biere_noire" -> Color(0xFF0E0E0E)
        "biere_cuivrée" -> Color(0xFFB87333)
        "biere_rouge" -> Color(0xFF9B111E)
        "biere_ébène" -> Color(0xFF3B3B3B)
        else -> Color.Green // Par défaut, une couleur neutre
    }
}
fun mapNameBeer(dbType: String): String {
    return when (dbType.lowercase()) {
        "biere_blanche" -> "Blanche"
        "biere_blonde" -> "Blonde"
        "biere_dorée" -> "Dorée"
        "biere_ambre" -> "Ambrée"
        "biere_rousse" -> "Rousse"
        "biere_brune" -> "Brune"
        "biere_noire" -> "Noire"
        "biere_cuivrée" -> "Cuivrée"
        "biere_rouge" -> "Rubis"
        "biere_ébène" -> "Ébène"
        else -> dbType.replaceFirst("biere_", "").capitalize()
    }
}

fun mapFontOverBeer(color: String): Color {
    return when (color.lowercase()) {
        "biere_blanche" -> Color(0xFF0E0E0E)
        "biere_blonde" -> Color(0xFF0E0E0E)
        "biere_dorée" -> Color(0xFF0E0E0E)
        "biere_ambre" -> Color(0xFF4F4F4F)
        "biere_rouge" -> Color(0xFF4F4F4F)
        "biere_brune" -> Color(0xFFFFFFFF)
        "biere_noire" -> Color(0xFFFFFFFF)
        "biere_cuivrée" -> Color(0xFFFFFFFF)
        "biere_rubis" -> Color(0xFFFFFFFF)
        "biere_ébène" -> Color(0xFFFFFFFF)
        else -> Color.Gray // Par défaut, une couleur neutre
    }
}




