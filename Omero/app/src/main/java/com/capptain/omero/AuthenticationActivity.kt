package com.capptain.omero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capptain.omero.ui.theme.OmeroTheme
import com.capptain.omero.ui.theme.robotoFamily


class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OmeroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Logo()
                        CredentialsView()
                    }

                }
            }
        }
    }
}

@Composable
fun Logo() {

    val infiniteTransition = rememberInfiniteTransition()

    //Infinite Omikron Animation to top and bottom. When is on the top the shadow is at maxValue and when it is on the bottom the shadow is 0
    val omikronInfiniteAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column {
        Row  (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom ,
            modifier = Modifier
                .padding()
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.omikron300),
                    contentDescription = "Omicron",
                    modifier = Modifier
                        .width(70.dp)
                        .height(82.dp)
                        .absoluteOffset(y = (omikronInfiniteAnimation * -1).dp)
                )
                Canvas(
                    modifier = Modifier
                        .width(omikronInfiniteAnimation.dp)
                        .height(2.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    drawRoundRect(
                        color = Color.Gray,
                    )
                }

            }
            Text(
                text = "mero",
                fontSize = 40.sp ,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)

            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }

}

@Composable
fun CredentialsView() {
    Column {
        Text(
            text = stringResource(id = R.string.enter_app),
            fontSize = 25.sp,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    OmeroTheme {

        Column {
            Logo()
            CredentialsView()
        }


    }
}