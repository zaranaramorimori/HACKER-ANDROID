package com.teamzzong.hacker.presentation.setting

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.teamzzong.hacker.R
import com.teamzzong.hacker.compose.HackerTheme
import com.teamzzong.hacker.compose.KimHwanki

@Composable
fun UserExpireScreen(
    onBack: () -> Unit = {},
    onPressExpire: () -> Unit = {}
) {
    HackerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = HackerTheme.colors.white,
                    elevation = 0.dp
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Go to Back",
                        modifier = Modifier.clickable { onBack() }
                    )
                }
            }
        ) { padding ->
            val infiniteTransition = rememberInfiniteTransition()
            val boxAnimation by infiniteTransition.animateValue(
                initialValue = 0.dp,
                targetValue = 80.dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                ExpireTitle(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 24.dp)
                        .wrapContentHeight()
                )
                ExpireChungnyunAnimation(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomCenter)
                )
                Image(
                    painter = painterResource(R.drawable.ic_expire_return),
                    contentDescription = "expire",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 80.dp)
                        .clickable { onBack() }
                )
                Text(
                    text = "그래도 탈퇴하기",
                    fontFamily = KimHwanki,
                    fontSize = 18.sp,
                    textDecoration = TextDecoration.Underline,
                    color = HackerTheme.colors.blue,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 52.dp)
                        .clickable { onPressExpire() }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(boxAnimation)
                        .align(Alignment.BottomCenter)
                        .background(HackerTheme.colors.blue)
                )
            }
        }
    }
}

@Composable
private fun ExpireTitle(modifier: Modifier = Modifier) {
    Text(
        text = "정말\n탈퇴하시겠어요?",
        modifier = modifier,
        style = HackerTheme.typography.title1,
        lineHeight = 40.sp
    )
}

@Composable
private fun ExpireChungnyunAnimation(modifier: Modifier) {
    val expireAnimationSpec by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.expire_user))
    val expireAnimationProgress by animateLottieCompositionAsState(
        expireAnimationSpec,
        iterations = LottieConstants.IterateForever,
    )
    Column(
        modifier = modifier
    ) {
        LottieAnimation(
            composition = expireAnimationSpec,
            progress = expireAnimationProgress,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun PreviewScreen() {
    UserExpireScreen()
}

