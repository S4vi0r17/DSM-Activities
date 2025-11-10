package tech.s4vi0r.sqlbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.s4vi0r.sqlbasics.data.EmailDatabaseHelper
import tech.s4vi0r.sqlbasics.ui.theme.SQLBasicsTheme

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: EmailDatabaseHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar la base de datos con datos de ejemplo
        dbHelper = EmailDatabaseHelper(this)
        val db = dbHelper.writableDatabase // Esto crea la DB si no existe
        // Mantener la DB abierta durante el ciclo de vida de la Activity
        
        enableEdgeToEdge()
        setContent {
            SQLBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SqlBasicsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    
    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}

@Composable
fun SqlBasicsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SQL Basics",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Base de datos inicializada ✓",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Abre Database Inspector para ver los datos",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SqlBasicsScreenPreview() {
    SQLBasicsTheme {
        SqlBasicsScreen()
    }
}