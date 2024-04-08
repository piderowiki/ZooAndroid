package com.ynu.zoo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.ynu.zoo.LocalWelcomeAssets
import com.ynu.zoo.R

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val BloomLightColorPalette = lightColors(
    primary = pink100,
    secondary = pink900,
    background = white,
    surface = white850,
    onPrimary = gray,
    onSecondary = white,
    onBackground = gray,
    onSurface = gray
)
private val BloomDarkColorPalette = darkColors(
    primary = green900,
    secondary = green300,
    background = gray,
    surface = white150,
    onPrimary = white,
    onSecondary = gray,
    onBackground = white,
    onSurface = white850
)


@Composable
fun BloomTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

open class WelcomeAssets private constructor(
    var background:Int,
    var illos:Int,
    var logo:Int
){
    object LightWelcomeAssets : WelcomeAssets(
        background = R.drawable.ic_light_welcome_bg,
        illos = R.drawable.ic_light_welcome_illos,
        logo = R.drawable.ic_light_logo
    )

    object DarkWelcomeAssets : WelcomeAssets(
        background = R.drawable.ic_dark_welcome_bg,
        illos = R.drawable.ic_dark_welcome_illos,
        logo = R.drawable.ic_dark_logo
    )
}

@Composable
fun BloomNewTheme(darkTheme: Boolean = isSystemInDarkTheme(),content: @Composable () -> Unit){
    val enemyColor  = Color(0xFF13227a)
    val friendColor = Color(0xFFd81e06)

    CompositionLocalProvider(
        LocalWelcomeAssets provides if (darkTheme) WelcomeAssets.DarkWelcomeAssets else WelcomeAssets.LightWelcomeAssets
    ) {
        MaterialTheme(
            colors = if (darkTheme) {
                BloomDarkColorPalette
            } else {
                BloomLightColorPalette
            },
            typography = bloomTypography,
            shapes = Shapes,
            content = content
        )
    }
}