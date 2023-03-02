package com.teamzzong.hacker.compose

import androidx.annotation.FontRes
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat

val NotoSansRegular: FontFamily @Composable get() = HackerFontFamily(R.font.hacker_font_regular)
val NotoSansMedium: FontFamily @Composable get() = HackerFontFamily(R.font.hacker_font_medium)
val NotoSansBold: FontFamily @Composable get() = HackerFontFamily(R.font.hacker_font_bold)
val KimHwanki: FontFamily @Composable get() = HackerFontFamily(R.font.hacker_font_kimhwanki)

@Composable
private fun HackerFontFamily(@FontRes id: Int): FontFamily {
    ResourcesCompat.getFont(LocalContext.current, id)?.let { return FontFamily(it) }
    return FontFamily.Default
}

@Stable
class HackerTypography internal constructor(
    title1: TextStyle,
    title2: TextStyle,
    title3: TextStyle,
    subTitle1: TextStyle,
    subTitle2: TextStyle,
    subTitle3: TextStyle,
    body1: TextStyle,
    body2: TextStyle
) {
    var title1: TextStyle by mutableStateOf(title1)
        private set
    var title2: TextStyle by mutableStateOf(title2)
        private set
    var title3: TextStyle by mutableStateOf(title3)
        private set
    var subTitle1: TextStyle by mutableStateOf(subTitle1)
        private set
    var subTitle2: TextStyle by mutableStateOf(subTitle2)
        private set
    var subTitle3: TextStyle by mutableStateOf(subTitle3)
        private set
    var body1: TextStyle by mutableStateOf(body1)
        private set
    var body2: TextStyle by mutableStateOf(body2)
        private set

    fun copy(
        title1: TextStyle = this.title1,
        title2: TextStyle = this.title2,
        title3: TextStyle = this.title3,
        subTitle1: TextStyle = this.subTitle1,
        subTitle2: TextStyle = this.subTitle2,
        subTitle3: TextStyle = this.subTitle3,
        body1: TextStyle = this.body1,
        body2: TextStyle = this.body2
    ): HackerTypography = HackerTypography(
        title1 = title1,
        title2 = title2,
        title3 = title3,
        subTitle1 = subTitle1,
        subTitle2 = subTitle2,
        subTitle3 = subTitle3,
        body1 = body1,
        body2 = body2
    )

    fun update(other: HackerTypography) {
        title1 = other.title1
        title2 = other.title2
        title3 = other.title3
        subTitle1 = other.subTitle1
        subTitle2 = other.subTitle2
        subTitle3 = other.subTitle3
        body1 = other.body1
        body2 = other.body2
    }
}

@Composable
fun HackerTypography(): HackerTypography {
    return HackerTypography(
        title1 = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NotoSansBold,
            lineHeight = 24.sp
        ),
        title2 = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NotoSansBold,
            lineHeight = 22.sp
        ),
        title3 = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NotoSansBold,
            lineHeight = 12.sp
        ),
        subTitle1 = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = NotoSansMedium,
            lineHeight = 20.sp
        ),
        subTitle2 = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = NotoSansMedium,
            lineHeight = 16.sp
        ),
        subTitle3 = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = NotoSansRegular,
        ),
        body1 = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = NotoSansMedium,
            lineHeight = 16.sp
        ),
        body2 = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = NotoSansMedium,
            lineHeight = 14.sp
        )
    )
}
