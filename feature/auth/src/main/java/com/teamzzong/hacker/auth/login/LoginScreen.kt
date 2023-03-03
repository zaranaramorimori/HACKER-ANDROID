package com.teamzzong.hacker.auth.login

import android.widget.Toast
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.teamzzong.hacker.auth.R
import com.teamzzong.hacker.compose.HackerTheme
import com.teamzzong.hacker.compose.KimHwanki
import com.teamzzong.hacker.domain.repository.social.KakaoAuthInteractor
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    kakaoAuthInteractor: KakaoAuthInteractor,
    navigateHome: () -> Unit,
    navigateRegister: () -> Unit
) {
    HackerTheme {
        val density = LocalDensity.current
        val scope = rememberCoroutineScope()
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (logo, title, kakaoLogin) = createRefs()
            val logoAnimationStarted by viewModel.splashAnimationStarted.collectAsState()
            var logoAnimationFinished by remember { mutableStateOf(false) }
            val logoTransition = updateTransition(logoAnimationStarted)
            val logoSize by logoTransition.animateDp(
                transitionSpec = { tween(durationMillis = 1000) }
            ) { if (it) 200.dp else 0.dp }
            var offsetAnimationFinished by remember { mutableStateOf(false) }
            val offsetY by animateDpAsState(
                targetValue = if (logoAnimationFinished) (-90).dp else 0.dp,
                animationSpec = tween(800, 500),
                finishedListener = { offsetAnimationFinished = true },
            )
            val alphaOffset by animateFloatAsState(
                targetValue = if (offsetAnimationFinished) 1f else 0f,
                animationSpec = tween(800, 200)
            )


            Image(painter = painterResource(R.drawable.ic_hacker_logo),
                contentDescription = "LOGO",
                modifier = Modifier
                    .constrainAs(logo) {
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            start = parent.start,
                            end = parent.end
                        )
                    }
                    .size(logoSize)
                    .onSizeChanged {
                        with(density) { logoAnimationFinished = it.width.toDp() >= 200.dp }
                    }
                    .offset(y = if (!viewModel.isAutoLoginEnabled) offsetY else 0.dp)
            )

            viewModel.changeSplashStartState(true)

            if (logoAnimationFinished && viewModel.isAutoLoginEnabled) {
                navigateHome()
            }

            if (offsetAnimationFinished) {
                Column(modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(logo.bottom, margin = -(70).dp)
                        start.linkTo(logo.start)
                        end.linkTo(logo.end)
                    }
                    .alpha(alphaOffset), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "hair commit",
                        style = TextStyle(
                            color = HackerTheme.colors.black,
                            fontSize = 22.sp,
                            fontFamily = KimHwanki,
                            lineHeight = 22.sp
                        ),
                    )
                    Text(
                        text = "HACKER",
                        style = TextStyle(
                            color = HackerTheme.colors.black,
                            fontSize = 70.sp,
                            fontFamily = KimHwanki,
                            lineHeight = 70.sp
                        ),
                    )
                }

                Image(painter = painterResource(id = R.drawable.ic_kakao_login),
                    contentDescription = "Kakao Login",
                    modifier = Modifier
                        .constrainAs(kakaoLogin) {
                            bottom.linkTo(parent.bottom, 110.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable {
                            if (kakaoAuthInteractor.isKakaoTalkLoginAvailable) {
                                scope.launch {
                                    kakaoAuthInteractor
                                        .loginByKakaoTalk()
                                        .onSuccess { viewModel.executeInHouseLogin(it.token) }
                                        .onFailure { Timber.e(it) }
                                }

                            } else {
                                scope.launch {
                                    kakaoAuthInteractor
                                        .loginByKakaoAccount()
                                        .onSuccess { viewModel.executeInHouseLogin(it.token) }
                                        .onFailure { Timber.e(it) }
                                }
                            }
                        }
                        .alpha(alphaOffset))
            }

            when (viewModel.inHouseLoginLoginUiState.value) {
                is InHouseLoginUiState.SignInSuccess -> navigateHome()
                is InHouseLoginUiState.SignUpSuccess -> navigateRegister()
                is InHouseLoginUiState.Failure -> {
                    Toast.makeText(
                        LocalContext.current, "로그인/회원가입에 실패했습니다.", Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }
}

private val mockKakaoAuthInteractor = object : KakaoAuthInteractor {
    override val isKakaoTalkLoginAvailable: Boolean
        get() = false

    override suspend fun loginByKakaoTalk(): Result<KakaoAuthInteractor.Token> =
        runCatching {
            KakaoAuthInteractor.Token("")
        }

    override suspend fun loginByKakaoAccount(): Result<KakaoAuthInteractor.Token> =
        runCatching {
            KakaoAuthInteractor.Token("")
        }

    override fun logout() = Unit
    override fun expire() = Unit
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(hiltViewModel(), mockKakaoAuthInteractor, {}) {}
}