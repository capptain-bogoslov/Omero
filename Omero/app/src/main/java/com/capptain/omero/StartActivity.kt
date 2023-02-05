package com.capptain.omero


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capptain.omero.ui.theme.OmeroTheme
import kotlinx.coroutines.delay

enum class OmikronAnimation {
    Omikron0, //Nothing visible
    Omikron1, //Appear Omikron
    Omikron2, //Omikron at start
    Omikron3 //Rotation of Omikron
}

enum class MeroAnimation {
    Mero0, //Nothing visible
    Mero1, //Appear mero
    Mero2 //Mero to the right
}

class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OmeroTheme {
// A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Splash()
                }
            }
        }
    }
}


@Composable
fun Splash() {

    val context = LocalContext.current
    var omikronStart by remember { mutableStateOf(OmikronAnimation.Omikron0) }
    var meroStart by remember { mutableStateOf( MeroAnimation.Mero0) }
    val transition = updateTransition(targetState = omikronStart, label = "OmikronTransition")
    val transition2 = updateTransition(targetState = meroStart, label = "MeroTransition")

//Alpha transition
    val alphaOmikron by transition.animateFloat(
        label = "omikron_alpha_animation",
        transitionSpec = {
            tween(durationMillis = 2000)
        }
    ) { state ->
        when (state) {
            OmikronAnimation.Omikron0 -> 0.0f
            OmikronAnimation.Omikron1, OmikronAnimation.Omikron2, OmikronAnimation.Omikron3 -> 1.0f
        }
    }
    val alphaMero by transition2.animateFloat(
        label = "mero_alpha_animation",
        transitionSpec = { tween(durationMillis = 2000, delayMillis = 1000) }

    ) { state ->
        when (state) {
            MeroAnimation.Mero0 -> 0.0f
            MeroAnimation.Mero1, MeroAnimation.Mero2 -> 1.0f
        }
    }

//Offset transition
    val omikronToLeft by transition.animateOffset(
        label = "omikron_offset_animation",
        transitionSpec = {
            tween(durationMillis = 2000)
        }
    ) { state ->
        when (state) {
            OmikronAnimation.Omikron0, OmikronAnimation.Omikron1 -> Offset.Zero
            OmikronAnimation.Omikron2, OmikronAnimation.Omikron3 -> Offset(x = -80f, y= -45f)
        }
    }

    val meroToRight by transition2.animateDp(
        label = "mero_offset_animation",
        transitionSpec = {
            tween(durationMillis = 2000)
        }
    ) { state ->
        when(state) {
            MeroAnimation.Mero0, MeroAnimation.Mero1 -> 0.dp
            MeroAnimation.Mero2 -> 30.dp
        }
    }

//Omikron size
    val omikronSize by transition.animateInt(
        label = "omikron_size_animation",
        transitionSpec = {
            tween(durationMillis = 2000)
        }
    ) { state ->
        when (state) {
            OmikronAnimation.Omikron0, OmikronAnimation.Omikron1 -> 300
            OmikronAnimation.Omikron2, OmikronAnimation.Omikron3 -> 140
        }
    }

//Omikron Rotation Animation
    val omikronRotation by transition.animateFloat(
        label = "omikron_rotation_animation",
        transitionSpec = {
            tween(durationMillis = 3000)
        }
    ) { state ->
        when (state) {
            OmikronAnimation.Omikron0, OmikronAnimation.Omikron1, OmikronAnimation.Omikron2 -> 0f
            OmikronAnimation.Omikron3 -> 45f
        }

    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.omikron300),
            contentDescription = "Omicron",
            modifier = Modifier
                .size(omikronSize.dp)
                .alpha(alphaOmikron)
                .offset(x = omikronToLeft.x.dp, y = omikronToLeft.y.dp)
                .rotate(omikronRotation)

        )

        Image(
            painter = painterResource(id = R.drawable.mero),
            contentDescription = "logo",
            modifier = Modifier
                .width(120.dp)
                .height(100.dp)
                .alpha(alphaMero)
                .absoluteOffset(x = meroToRight)
        )
    }

    LaunchedEffect(Unit) {
        delay(2000)
        omikronStart = OmikronAnimation.Omikron1
        meroStart = MeroAnimation.Mero1
        delay(3000)
        omikronStart = OmikronAnimation.Omikron2
        meroStart = MeroAnimation.Mero2
        delay(2000)
        omikronStart = OmikronAnimation.Omikron3
        delay(2000)
        context.startActivity(Intent(context, AuthenticationActivity::class.java))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OmeroTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Splash()
        }
    }
}