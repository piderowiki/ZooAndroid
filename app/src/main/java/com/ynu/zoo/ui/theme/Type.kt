package com.ynu.zoo.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ynu.zoo.R

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val nunititoSansFamily = FontFamily(
    Font(R.font.lxgwwenkai_light,FontWeight.Light),
    Font(R.font.lxgwwenkai_regular,FontWeight.SemiBold),
    Font(R.font.lxgwwenkai_bold,FontWeight.Bold)
)

val bloomTypography = Typography(
    h1 = TextStyle(
        fontSize = 18.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.Bold
),

    h2 = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.15.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.Bold
),

    subtitle1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.Light
),

     body1 = TextStyle(
        fontSize = 14.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.Light
),

    body2 = TextStyle(
        fontSize = 12.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.Light
),
    button = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 1.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.SemiBold
),

    caption = TextStyle(
        fontSize = 12.sp,
        fontFamily = nunititoSansFamily,
        fontWeight = FontWeight.SemiBold
    ),
)
