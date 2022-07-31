package com.example.composetutorial101

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial101.ui.theme.ComposeTutorial101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // setContent block defines the activity's layout where composable functions are called
            ComposeTutorial101Theme { // Material theme installed by default by JetPack Compose
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }
}
// using a Material Theme allows composables to inherit styles as defined in the app's theme ensuring consistency across the app
// the theme is called in 'setContent' above and in 'Preview' Composable function below.
// the Empty Compose Activity template generates a default theme for your project that allows you to customize MaterialTheme
// you can find your custom theme in the Theme.kt file in the ui.theme subpackage.
// Material Design is built around three pillars: 'Color', 'Typography' and 'Shape'


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
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Composable functions can store local state in memory by using 'remember'
        // and track changes to the value passed to 'mutableStateOf'
        // when the value is updated, the Composables & their children get redrawn automatically
        // any changes to the state automatically updates the UI.
        // keeping track if the message is expanded or not
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )
        // toggling isExpanded variable when we click on this Column
        Column(Modifier.clickable { isExpanded = !isExpanded }){
            Text(
                text = msg.sender,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface( // Surface composable allows customizing of the message body's shape and elevation
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.body2,
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn { // renders only the elements visible on screen,it's designed to be very efficient for long lists
        items(messages) { text ->
            MessageCard(msg = text)
        }
    }
}

//Having used Material Design; colors, text and backgrounds will automatically adapt to the dark background
// Thanks to the Material Design support, Jetpack Compose can handle the dark theme by default.
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

// the '@Preview' annotation is used on a composable function that does not take in parameters
@Preview(showBackground = true)
@Composable
fun PreviewConversation() {
   ComposeTutorial101Theme {
       Conversation(messages = SampleData.conversationSample)
   }
}