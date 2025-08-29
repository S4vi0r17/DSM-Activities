package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                BusinessCardScreen()
            }
        }
    }
}

@Composable
fun BusinessCardScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(), color = Color(0xFF9FD71B)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoSection(Modifier.size(160.dp))
                NameSection()
                TitleSection()
            }

            ContactInfoSection(modifier = Modifier.padding(bottom = 70.dp))
        }
    }
}

@Composable
fun LogoSection(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.android_16_logo),
        contentDescription = "Android 16 logo",
        modifier = modifier
    )
}

@Composable
fun NameSection(modifier: Modifier = Modifier) {
    Text(
        text = "Eder Benites", color = Color(0xFFffffff), fontSize = 60.sp, modifier = modifier
    )
}

@Composable
fun TitleSection(modifier: Modifier = Modifier) {
    Text(
        text = "Android Developer Enjoyer",
        color = Color(0xFF5c6364),
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}

@Composable
fun ContactInfoSection(modifier: Modifier = Modifier) {
    Column(modifier) {
        PhoneInfoRow()
        SocialInfoRow()
        EmailInfoRow()
    }
}

@Composable
fun PhoneInfoRow(modifier: Modifier = Modifier) {
    ContactRow(painterResource(R.drawable.phone_icon), "Phone Icon", "+51 935 857 259", modifier)
}

@Composable
fun SocialInfoRow(modifier: Modifier = Modifier) {
    ContactRow(painterResource(R.drawable.share_icon), "Share Icon", "@S4vi0r17", modifier)

}

@Composable
fun EmailInfoRow(modifier: Modifier = Modifier) {
    ContactRow(
        painterResource(R.drawable.mail_icon), "Mail Icon", "eder.benites@unmsm.edu.pe", modifier
    )

}

@Composable
fun ContactRow(
    icon: Painter, contentDescription: String, info: String, modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = modifier.size(24.dp),
            tint = Color(0xFF5c6364)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = info, fontSize = 18.sp, color = Color(0xFF5c6364))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        LogoSection()
    }
}
