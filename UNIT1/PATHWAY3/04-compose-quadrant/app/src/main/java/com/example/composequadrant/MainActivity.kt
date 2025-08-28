package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeQuadrantTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Row(Modifier.weight(1f)) {
                            Quadrant(
                                stringResource(R.string.title_text_composable),
                                stringResource(R.string.description_text_composable),
                                Modifier
                                    .background(Color(0xFFEADDFF))
                                    .weight(1f)
                            )
                            Quadrant(
                                stringResource(R.string.title_image_composable),
                                stringResource(R.string.description_image_composable),
                                Modifier
                                    .background(Color(0xFFD0BCFF))
                                    .weight(1f)
                            )
                        }
                        Row(Modifier.weight(1f)) {
                            Quadrant(
                                stringResource(R.string.title_row_composable),
                                stringResource(R.string.description_row_composable),
                                Modifier
                                    .background(Color(0xFFB69DF8))
                                    .weight(1f)
                            )
                            Quadrant(
                                stringResource(R.string.title_column_composable),
                                stringResource(R.string.description_column_composable),
                                Modifier
                                    .background(Color(0xFFF6EDFF))
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Quadrant(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description, textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeQuadrantTheme {
        Quadrant(
            "Text composable",
            "Displays text and follows the recommended Material Design guidelines.",
            Modifier.background(Color(0xFFEADDFF))
        )
    }
}
