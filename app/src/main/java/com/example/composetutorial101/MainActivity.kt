package com.example.composetutorial101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetutorial101.ui.theme.ComposeTutorial101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // setContent block defines the activity's layout where composable functions are called
            MessageCard(msg = Message("Jolene", "Your beauty is beyond compare"))
        }
    }
}

// this is a message object
data class Message(val sender: String, val body: String)

// Composable functions can only be called from other Composable functions
@Composable
fun MessageCard(msg: Message) {
    Row(Modifier.padding(8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Sender's picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            Text(text = msg.body)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.sender, color = Color.Gray, fontSize = 13.sp)
        }
    }
}

// the '@Preview' annotation is used on a composable function that does not take in parameters
@Preview(showBackground = true)
@Composable
fun MessageCardPreview() {
    MessageCard(msg = Message("Jolene", "Your beauty is beyond compare."))
    //MessageCard(msg = Message("Android Devs", "Jetpack Compose is so cool."))
}