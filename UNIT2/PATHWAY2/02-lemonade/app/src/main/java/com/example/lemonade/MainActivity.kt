package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview
@Composable
fun LemonadeApp() {
    ImagenWithDescriptionOfLemonade(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun ImagenWithDescriptionOfLemonade(modifier: Modifier = Modifier) {

    var currentStep by remember { mutableIntStateOf(2) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    when (currentStep) {
        1 -> {
            LemonTextAndImage(
                R.drawable.lemon_tree,
                R.string.lemon_tree_content_description,
                {
                    currentStep = 2
                    squeezeCount = (2..4).random()
                },
                R.string.lemon_tree_instructions
            )
        }

        2 -> {


            LemonTextAndImage(
                R.drawable.lemon_squeeze,
                R.string.lemon_content_description,
                {
                    squeezeCount--
                    if (squeezeCount <= 0) currentStep = 3
                },
                R.string.squeeze_lemon_instructions,
                Modifier,
                squeezeCount
            )
        }

        3 -> {
            LemonTextAndImage(
                R.drawable.lemon_drink,
                R.string.full_glass_content_description,
                { currentStep = 4 },
                R.string.drink_lemonade_instructions
            )

        }

        4 -> {
            LemonTextAndImage(
                R.drawable.lemon_restart,
                R.string.empty_glass_content_description,
                { currentStep = 1 },
                R.string.restart_instructions
            )
        }
    }
}

@Composable
fun LemonTextAndImage(
    painter: Int,
    contentDescription: Int,
    onImageClick: () -> Unit,
    instruction: Int,
    modifier: Modifier = Modifier,
    count: Int? = null,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onImageClick() }) {
            Image(
                painter = painterResource(painter),
                contentDescription = stringResource(contentDescription),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (count != null) {
            Text(
                text = count.toString(),
                fontSize = 28.sp
            )
        } else {
            Text(
                text = " ",
                fontSize = 28.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(instruction),
            fontSize = 18.sp
        )

    }
}
