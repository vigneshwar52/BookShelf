package com.learning.bookshelf.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learning.bookshelf.ui.login.ui.theme.BookShelfTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Label
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting3(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val uiState by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), contentAlignment = Alignment.Center ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Login", style = MaterialTheme.typography.h5, modifier = Modifier.padding(20.dp), color = Color.DarkGray)

            TextField(
                value = uiState.email,
                singleLine = true,
                maxLines = 1,
                onValueChange = { loginViewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            TextField(
                value = uiState.password,
                singleLine = true,
                maxLines = 1,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.8f),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Box(
                modifier = Modifier.fillMaxWidth(0.8f).padding(10.dp),
                contentAlignment = Alignment.TopStart // Aligns the text at the start
            ) {
                Text(
                    text = "Don't have an Account?\nClick here to sign up",
                    modifier = Modifier
                        .clickable {
                            navController.navigate("sign_up_screen")
                        },
                    color = Color.Red
                )
            }

            Button(
                onClick = {
                    loginViewModel.onLogin(context,navController)
                    uiState.loginErrorMessage?.let {
                       Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }},
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Login", color = Color.White)
            }
        }
        uiState.loginErrorMessage?.let {
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    BookShelfTheme {
        val navController: NavHostController = rememberNavController()
        LoginScreen(navController, loginViewModel = LoginViewModel())
    }
}