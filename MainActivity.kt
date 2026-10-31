package com.mraiagent.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MrAiAgentScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MrAiAgentScreen() {
    var prompt by remember { mutableStateOf("") }
    var duration by remember { mutableFloatStateOf(10f) }
    var style by remember { mutableStateOf("3D Animation") }
    var message by remember { mutableStateOf("Write a prompt to prepare your video.") }
    val styles = listOf("3D Animation", "Realistic", "Cinematic", "Cartoon")
    val bg = Color(0xFF0D1020)
    val panel = Color(0xFF191F38)
    val purple = Color(0xFF8056E8)

    MaterialTheme(colorScheme = darkColorScheme(
        primary = purple, background = bg, surface = panel,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Column(
            modifier = Modifier.fillMaxSize().background(bg)
                .verticalScroll(rememberScrollState()).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier.fillMaxWidth().background(
                    Brush.linearGradient(listOf(Color(0xFF202A55), Color(0xFF542D83))),
                    RoundedCornerShape(22.dp)
                ).padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🤖", fontSize = 42.sp)
                    Text("MR. AI AGENT", fontSize = 26.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("Your AI. Your Imagination. Your Video.", color = Color(0xFFE0D7FF), fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(24.dp))
            SectionTitle("✍️  Enter your video prompt")
            OutlinedTextField(
                value = prompt, onValueChange = { prompt = it },
                modifier = Modifier.fillMaxWidth().height(145.dp),
                placeholder = { Text("Example: A little puppy saves a baby deer in a forest...") },
                shape = RoundedCornerShape(14.dp)
            )
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SectionTitle("🎬  Video duration")
                Text("${duration.toInt()} seconds", color = Color(0xFFC9B8FF), fontWeight = FontWeight.Bold)
            }
            Slider(value = duration, onValueChange = { duration = it }, valueRange = 5f..27f, steps = 21)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("5 sec", color = Color.LightGray, fontSize = 12.sp)
                Text("27 sec", color = Color.LightGray, fontSize = 12.sp)
            }
            Spacer(Modifier.height(20.dp))
            SectionTitle("📱  Video format")
            Surface(color = panel, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Vertical 9:16")
                    Text("YouTube Shorts", color = Color(0xFFC9B8FF))
                }
            }
            Spacer(Modifier.height(20.dp))
            SectionTitle("🎨  Video style")
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = style, onValueChange = {}, readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    shape = RoundedCornerShape(14.dp)
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    styles.forEach { item ->
                        DropdownMenuItem(text = { Text(item) }, onClick = {
                            style = item; expanded = false
                        })
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    message = if (prompt.isBlank()) {
                        "Please enter a prompt first."
                    } else {
                        "Prompt ready! ${duration.toInt()} sec • $style • 9:16. Connect a video-generation API to create the actual video."
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("✨  Generate Video", fontSize = 17.sp, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.height(14.dp))
            Text(message, color = Color(0xFFC9B8FF), fontSize = 13.sp)
            Spacer(Modifier.height(20.dp))
            Text(
                "Prototype app • Actual AI video generation requires a secure video API and backend.",
                color = Color.Gray, fontSize = 11.sp
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.White)
}
