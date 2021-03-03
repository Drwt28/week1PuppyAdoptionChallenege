/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.puppyproject.Model.Puppy
import com.example.puppyproject.Model.Puppys
import com.example.puppyproject.ui.theme.PuppyProjectTheme
import dev.chrisbanes.accompanist.coil.CoilImage
import org.intellij.lang.annotations.JdkConstants
import java.util.*
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            PuppyProjectTheme {

                val navigator   =  rememberNavController()
                NavHost(navigator, startDestination = "puppiesList") {
                    composable("puppiesList") {  Greeting("hello",navigator)}
                    composable("detailScreen") { PuppyDetailScreen() }
                }
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android",navigator)
//                }
            }
        }
    }
}

var list = Arrays.asList("Hello","Bhanu","Deepanshu")


@Composable
fun PuppyDetailScreen(){
    PuppyDetail(puppyId =2L, upPress = {})
}


@Composable
fun SingleIconItem(imageVector: ImageVector)
{


    IconButton(onClick = { /*TODO*/ },Modifier.background(color = Color(0xffe0f7fa)).clip(shape = RoundedCornerShape(20.dp))) {
        Icon(imageVector = imageVector, contentDescription = "")
    }

//
//    Box(
//        modifier =
//        Modifier
//            .height(50.dp).width(50.dp).clip(shape =  CircleShape).padding(8.dp)
//            .background(Color(0xffe0f7fa))
//    )
//    {
//        Icon(imageVector = imageVector, contentDescription = "",modifier =
//        Modifier.fillMaxSize())
//
//    }


}

@Composable
fun Greeting(name: String,navigator: NavHostController) {

    Scaffold(
        topBar ={
            TopAppBar(

                backgroundColor = Color.White,
                title = {Text("Puppy Adoption",style = MaterialTheme.typography.h6.copy(color = Color.Black))},
                elevation = 0.dp


            )
        },
        content = { LazyColumn(
            modifier =  Modifier.padding(4.dp),

            ){


            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    SingleIconItem(imageVector = Icons.Filled.FavoriteBorder)
                    Spacer(Modifier.width(10.dp))
                    SingleIconItem(imageVector = Icons.Filled.Filter)

                }
            }


            item{
                Text("Puppies",style = MaterialTheme.typography.h4.copy(letterSpacing = 4.sp))
            }

            items(Puppys.size){
                Puppys.map {

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navigator.navigate("detailScreen");

                            },
                        elevation = 3.dp,
                        shape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp)

                    ) {
                        Row(
                            modifier = Modifier
                                .size(160.dp)
                                .background(Color(0xfffff59d)),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                Modifier
                                    .size(160.dp)
                                    .background(Color(0xffb3e5fc))
                                    .weight(1f)){
                                CoilImage(
                                    data = it.imageUrl,
                                    contentDescription = "contentDescription",
                                    contentScale = ContentScale.Crop,

                                    )
                            }
                            Box(

                                Modifier
                                    .background(Color(0xfffff59d))
                                    .size(50.dp)
                                    .padding(10.dp)
                                    .weight(1f)) {

                                Column(){
                                    Text(it.name,style = MaterialTheme.typography.h6)
                                    Text("Rs "+it.price.toString(),style = MaterialTheme.typography.subtitle1)

                                }

//                            Box(Modifier.background(Color.White).fillMaxSize())

                            }
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription ="sad" ,Modifier.background(color =Color(0xfffff59d)))

//                            Box(
//                                Modifier
//                                    .background(Color.White)
//                                    .clip(shape = RoundedCornerShape(20.dp))
//                                    .padding(10.dp)){
//
//                            }

                        }

                    }

                }
            }
        }}

    )

}


@Preview
@Composable
fun DefaultPreview() {
    PuppyProjectTheme {
        Greeting("Android", rememberNavController())
    }
}

@Composable
fun PuppyDetail(
    puppyId: Long,
    upPress: () -> Unit
) {
    val snack = Puppys[0]



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(snack.name)
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        content = {
            LazyColumn(content = {
                item{
                    Surface(

                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .clip(
                                shape = CircleShape
                            ),
                        color = Color.Yellow

                    ){
                        CoilImage(
                            data = "https://images.unsplash.com/photo-1610244130620-fd348aa7854e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
                            contentDescription = "contentDescription",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(48.dp)
                                .height(48.dp)
                                .clip(shape = CircleShape),
                        )
                    }


                }
                item{
                    Title(snack)

                }
            })
        }
    )


}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(color = Color.White)
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)

    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            tint = Color.Yellow,
            contentDescription = "hello"
        )
    }
}


@Composable
private fun Body(
    scroll: ScrollState
) {
    val HzPadding =Modifier.padding(8.dp)
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(8.dp))
            Surface() {
                Column {
                    Spacer(Modifier.height(8.dp))
                    Spacer(Modifier.height(8.dp))

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "header",
                        style = MaterialTheme.typography.overline,
                        color = Color.Black

                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "place holder",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(40.dp))
                    Text(
                        text = "stringResource(R.string.ingredients)",
                        style = MaterialTheme.typography.overline,
                        color = Color.Black,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "",
                        style = MaterialTheme.typography.body1,
                        color = Color.Black,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))


                }
            }
        }
    }
}

@Composable
private fun Title(snack: Puppy) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = 20.dp)

    ) {

        val HzPadding =Modifier.padding(8.dp)

        val typo =  MaterialTheme.typography;
        val colors =  MaterialTheme.colors





        Spacer(Modifier.height(4.dp))
        Text(
            text = snack.name,
            style = MaterialTheme.typography.h4,
            color = colors.primary,
            modifier = HzPadding
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer interdum diam quis augue molestie faucibus dapibus sit amet ex. Sed magna lorem, convallis ac ante sed, mollis porta mi. Praesent urna arcu, porta in ex id, pellentesque ultrices lorem. Ut aliquet semper augue. Nunc accumsan mauris justo, eget pretium massa vestibulum quis. Duis tincidunt vitae quam quis dictum. Suspendisse mattis consequat ante eget blandit. Etiam nec vehicula purus, in varius lorem.\n" +
                    "\n" +
                    "Proin sodales nec metus et placerat. Vivamus vitae nunc feugiat, aliquam enim eget, cursus odio. Aenean iaculis at lacus quis porta. In hac habitasse platea dictumst. Quisque ornare lorem eu nisl vulputate, eu pellentesque magna finibus. Cras euismod sagittis nisl, eget fringilla nisi iaculis at. Integer aliquet pulvinar ultricies. Ut tempus ligula quis dui pellentesque condimentum.\n" +
                    "",   style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = (snack.price.toString()),
            style = MaterialTheme.typography.h6,
            color = colors.primary,
            modifier = HzPadding
        )

        Spacer(Modifier.height(4.dp))

    }
}

@Composable
private fun Image(
    imageUrl: String,
    scroll: Int
) {
    val HzPadding =Modifier.padding(8.dp)

    val typo =  MaterialTheme.typography;
    val colors =  MaterialTheme.colors

    Surface(

        modifier = Modifier
            .height(200.dp)
            .width(200.dp),
        color = Color.Yellow

    ){
        CoilImage(
            data = "https://images.unsplash.com/photo-1610244130620-fd348aa7854e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            contentDescription = "contentDescription",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clip(shape = CircleShape),
        )
    }



}



@Composable
private fun SnackDetailPreview() {
    PuppyProjectTheme {
        PuppyDetail(
            puppyId = 1L,
            upPress = { }
        )
    }
}

@Composable
private fun PuppyDetailDarkPreview() {
    PuppyProjectTheme(darkTheme = true) {
        PuppyDetail(
            puppyId = 1L,
            upPress = { }
        )
    }
}



