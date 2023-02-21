package com.example.android.composeplayground

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.android.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlin.random.Random
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    val viewModel: LaunchEffectViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Modifiers()
            /*  // cardTutorial

              val painter = painterResource(id = R.drawable.yo)
              val description = "Soy yo"
              val title = "Soy yo"
              Box(
                  modifier = Modifier
                      .fillMaxWidth(0.5f)
                      .padding(16.dp)
              ) {
                  CardComposable(
                      painter = painter,
                      title = title,
                      contentDescription = description,
                  )
              }*/
            //TextStyling()

            /* Column(modifier = Modifier.fillMaxSize()) {

                 val boxesColorState = remember {
                     mutableStateOf(Color.Yellow)
                 }

                 ColorBoxState(
                     Modifier
                         .fillMaxSize()
                         .weight(1f)
                 ) {
                     boxesColorState.value = it
                 }

                 Box(
                     modifier = Modifier
                         .background(boxesColorState.value)
                         .weight(1f)
                         .fillMaxSize()
                 )
             }*/

            //ButtonsAndSnackBar()
            // ScrollableColumn()
            //LazyList()
            // ConstraintLayoutUsage()
            ComposeEffectHandlers().WithLaunchedEffect(viewModel)

        }
    }

    @Composable
    private fun ConstraintLayoutUsage() {
        val constraints = ConstraintSet {
            val greenBox = createRefFor("greenbox")
            val redBox = createRefFor("redbox")
            val guidelines = createGuidelineFromStart(0.2f)

            constrain(greenBox) { // the constraints applicable to this component
                top.linkTo(parent.top)
                start.linkTo(guidelines)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }

            constrain(redBox) { // the constraints applicable to this component
                top.linkTo(parent.top)
                start.linkTo(greenBox.end)
                end.linkTo(parent.end)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }

            createHorizontalChain(greenBox, redBox, chainStyle = ChainStyle.Packed)
        }


        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .layoutId("greenbox")
            )

            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .layoutId("redbox")
            )
        }
    }


    @Composable
    private fun LazyList() {
        val itemsSize = 100
        LazyColumn {
            /*      itemsIndexed(listOf(1, 2, 3, 4, 5, 6)) { index, item ->

                  }*/
            items(itemsSize) { lazyItem ->
                Text(
                    text = "Item $lazyItem",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )

            }
        }
    }

    @Composable
    private fun ScrollableColumn() {
        // this is used when we have a fixed amount of data, and more than 20 a lazyColumn
        // is worth it
        var scrollState = rememberScrollState()

        Column(
            modifier = Modifier.verticalScroll(
                scrollState
            )
        ) {
            for (i in 1..20) {
                Text(
                    text = "Item $i",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )
            }
        }
    }

    @Composable
    private fun ButtonsAndSnackBar() {
        val scaffoldState = rememberScaffoldState()
        // by is syntantic sugar that gives us the value directly
        var textFieldState by remember {
            mutableStateOf("")
        }
        val scope = rememberCoroutineScope()

        // this is a layout that allows the user to control stuff like snackBars, toasts and material design components
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            scaffoldState = scaffoldState
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = textFieldState,
                    label = {
                        "This is the hint"
                    },
                    onValueChange = { newString ->
                        textFieldState = newString
                    }, singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(textFieldState)
                    }
                }) {
                    Text(text = "Pls greet me")
                }
            }

        }
    }


    @Composable
    private fun Modifiers() {
        Column(
            modifier = Modifier // the modifiers are applied in sequence
                .background(Color.Red)  //1
                .fillMaxWidth() //2
                .fillMaxHeight(.7f) //3
                .padding(16.dp) //4
                .border(5.dp, Color.Black) // ...
                .padding(5.dp)
                .border(5.dp, Color.Yellow)
                .padding(5.dp)
                .border(5.dp, Color.Magenta)
                .padding(5.dp), horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Hello", modifier = Modifier
                    .padding(16.dp, 16.dp)
                    .border(5.dp, Color.Green)
                    .offset(16.dp, 16.dp)
                    .border(5.dp, Color.White)
                    .background(Color.LightGray)
            )
            // offset ignores the element and puts its on top no matter if there is something there
            // padding does not ignores the element and avoids crashing
            // spacer exists to make space between components
            Text(text = "Hello")
        }
    }

    @Composable
    fun CardComposable(
        painter: Painter,
        title: String,
        contentDescription: String,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            // with box its a container for our elements and allows them to stack in top of each other
            Box(modifier = Modifier.height(200.dp)) {
                // elements in this scope stack on top of each
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )

                // the box to our gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                ),
                                startY = 300f
                            )
                        )
                )

                // in order to give desired alignment into the text
                Box(
                    modifier = Modifier
                        .fillMaxSize() // fills the maxSize of the parent
                        .padding(12.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
                }
            }
        }
    }

    @Composable
    private fun TextStyling() {
        val fontFamily = FontFamily(
            Font(R.font.roboto_thin, FontWeight.Thin),
            Font(R.font.roboto_light, FontWeight.Light),
            Font(R.font.roboto_regular, FontWeight.Normal),
            Font(R.font.roboto_medium, FontWeight.Medium),
            Font(R.font.roboto_bold_italic, FontWeight.SemiBold),
            Font(R.font.roboto_bold, FontWeight.Bold),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010))
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 50.sp
                        )
                    ) {
                        append("Hello ")
                    }
                    append("Compose") // does not have style
                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 50.sp
                        )
                    ) {
                        append(" Test")
                    }
                },
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = fontFamily,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,

                )
        }
    }

    @Composable
    private fun ColorBoxState(modifier: Modifier = Modifier, updateColor: (Color) -> Unit) {

        // remember holds the last state of the data of the last composition
        // instead of resetting the value
        /*    val colorState = remember {
                mutableStateOf(Color.Yellow)
            } // if used without remember like this will always reset to this
    */
        Box(modifier = modifier
            .background(Color.Red)
            .clickable {
                updateColor(
                    Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat(),
                        alpha = 1f
                    )
                )
            }
        )
    }
}


