package com.capptain.omero

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capptain.omero.ui.theme.LinkBlueDark
import com.capptain.omero.ui.theme.OmeroTheme
import com.capptain.omero.ui.theme.PrimaryLight
import com.capptain.omero.ui.theme.SecondaryLight
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
//                    Column {
//                        Logo()
//                        CredentialsView()
//                        ButtonsView()
//                        DividerView()
//                        SocialMediaLogin()
//
//                    }
                    ShowContent(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun ShowContent(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current

    when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            //LANDSCAPE
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Logo(isPortrait = false)
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column( modifier = modifier
                        .weight(4f)) {
                        CredentialsView(modifier = modifier, isPortrait = false)
                        ButtonsView(modifier = modifier, isPortrait = false)

                    }
                    Column(
                        modifier = modifier
                            .widthIn(max = 50.dp)
                            .fillMaxSize()
                    ) {
                        DividerView(modifier = modifier, isPortrait = false)

                    }
                    Column(
                        modifier = modifier
                            .weight(2f)
                    ) {
                        SocialMediaLogin(modifier = modifier, isPortrait = false)

                    }

                }

            }
        }
        else -> {
            //PORTRAIT
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Logo(isPortrait = true)
                CredentialsView(modifier = modifier, isPortrait = true)
                ButtonsView(modifier = modifier, isPortrait = true)
                DividerView(modifier = modifier, isPortrait = true)
                SocialMediaLogin(modifier = modifier, isPortrait = true)

            }
        }
    }

}

@Composable
fun Logo(isPortrait: Boolean) {

    val infiniteTransition = rememberInfiniteTransition()

    //Infinite Omikron Animation to top and bottom. When is on the top the shadow is at maxValue and when it is on the bottom the shadow is 0
    val omikronInfiniteAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if(isPortrait) {30f} else {15f} ,
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
                    .padding(top = if (isPortrait) {40.dp} else {20.dp} , bottom = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.omikron300),
                    contentDescription = "Omicron",
                    modifier = Modifier
                        .width(if (isPortrait) {70.dp} else {40.dp} )
                        .height(if(isPortrait) {82.dp} else {55.dp} )
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
                fontSize = if(isPortrait) {40.sp} else {30.sp}  ,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)

            )
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsView(modifier: Modifier = Modifier, isPortrait: Boolean) {
    var email = remember { mutableStateOf(TextFieldValue()) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column {

        Text(
            text = stringResource(id = R.string.enter_app),
            fontSize = 18.sp,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = if (isPortrait) {20.dp } else { 5.dp} )
        )

        //Username
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = { Text(text = stringResource(id = R.string.email), color = Color.LightGray) },
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        
        Spacer(modifier = modifier.height(10.dp))
        
        //Password
        TextField(
            value = password,
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if(passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                //Περιγραφή για accessibility
                val description = if (passwordVisible) stringResource(id = R.string.password_hide) else stringResource(
                    id = R.string.password_show)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            onValueChange = { password = it },
            placeholder = { Text(text = stringResource(id = R.string.password), color = Color.LightGray) },
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

    }
}

@Composable
fun ButtonsView(modifier: Modifier = Modifier, isPortrait: Boolean){

    val gradient1 = Brush.verticalGradient(listOf(SecondaryLight, PrimaryLight))
    val gradient2 = Brush.verticalGradient(listOf(Color.White, Color.White))
    if(isPortrait) {

        //Forgot password
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)) {
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.forgot_password)),
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = {},
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = robotoFamily,
                    color = LinkBlueDark
                )
            )
        }

        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Sign in button
            GradientButton(
                text = stringResource(id = R.string.enter),
                gradient = gradient1,
                buttonTextColor = Color.White
            )

            Text(
                text = stringResource(id = R.string.first_time),
                fontSize = 15.sp,
                fontFamily = robotoFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .align(Alignment.Start),
            )

            GradientButton(
                text = stringResource(id = R.string.sign_up),
                gradient = gradient2,
                buttonTextColor = PrimaryLight
            )

        }
    } else {
        Row{
            Column(modifier = modifier
                .weight(1f)
                .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                //Forgot password
                Box() {
                    ClickableText(
                        text = AnnotatedString(stringResource(id = R.string.forgot_password)),
                        modifier = Modifier
                            .align(Alignment.Center),
                        onClick = {},
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = robotoFamily,
                            color = LinkBlueDark
                        )
                    )
                }

                GradientButton(
                    text = stringResource(id = R.string.enter),
                    gradient = gradient1,
                    buttonTextColor = Color.White
                )


            }
            Column(modifier = modifier
                .weight(1f)
                .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.first_time),
                    fontSize = 15.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                )

                GradientButton(
                    text = stringResource(id = R.string.sign_up),
                    gradient = gradient2,
                    buttonTextColor = PrimaryLight
                )
            }
        }
    }
}

@Composable
fun DividerView(modifier: Modifier = Modifier, isPortrait: Boolean) {
    if (isPortrait) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = modifier.widthIn(min = 50.dp, max = 150.dp))
            Text(text = stringResource(id = R.string.or), fontSize = 15.sp, fontFamily = robotoFamily, modifier = modifier.padding(horizontal = 10.dp))
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = modifier.widthIn(min = 50.dp, max = 150.dp))
    }

    } else {
        Column(
            modifier = modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider(color = Color.LightGray, modifier = modifier
                .fillMaxHeight()
                .width(1.dp)
                .heightIn(min = 100.dp, max = 300.dp))

            Text(text = stringResource(id = R.string.or), fontSize = 15.sp, fontFamily = robotoFamily, modifier = modifier.padding(horizontal = 10.dp, vertical = 10.dp))
            Divider(color = Color.LightGray, modifier = modifier
                .width(1.dp)
                .heightIn(min = 100.dp, max = 300.dp))
        }
    }
}

@Composable
fun SocialMediaLogin(modifier: Modifier = Modifier, isPortrait: Boolean) {
    Column {
        Text(
            text = stringResource(id = R.string.connect_with_social),
            fontSize = 18.sp,

            fontFamily = robotoFamily,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .align(if(isPortrait) {
                    Alignment.Start
                } else {
                    Alignment.CenterHorizontally
                })
        )

        Row(modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            Image(painter = painterResource(id = R.drawable.google_logo), contentDescription = "google sign in", modifier = modifier
                .width(50.dp)
                .height(50.dp)
                .clickable { })
            Image(painter = painterResource(id = R.drawable.facebook_logo), contentDescription = "google sign in", modifier = modifier
                .width(50.dp)
                .height(40.dp)
                .clickable { })
        }
        }


}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    buttonTextColor: Color,
    onClick: () -> Unit = {},
) {


    Button(onClick = { onClick() },
        Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .background(gradient, shape = RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
        ,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = buttonTextColor)
    ) {
        Text(text = text,
            fontSize = 22.sp,
            fontFamily = robotoFamily,
            fontWeight = FontWeight.ExtraBold)
    }

}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Composable
fun DefaultPreviewLandscape() {

        OmeroTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                ShowContent(modifier = Modifier)
            }
        }

}

@Preview(showBackground = true)
@Composable
private fun AuthenticationPortrait() {

        OmeroTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                ShowContent(modifier = Modifier)
            }
        }


}