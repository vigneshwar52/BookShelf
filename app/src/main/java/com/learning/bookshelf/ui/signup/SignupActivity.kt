package com.learning.bookshelf.ui.signup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.learning.bookshelf.AppNavigation
import com.learning.bookshelf.model.Book
import com.learning.bookshelf.signup.ui.theme.BookShelfTheme
import com.learning.bookshelf.util.Validators

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            BookShelfTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel = viewModel()) {
    val uiState by signUpViewModel.uiState.collectAsState()
    val context = LocalContext.current

    BackHandler(enabled = true){}

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "SIGN UP", style = MaterialTheme.typography.h5, color = Color.DarkGray, modifier = Modifier.padding(10.dp))
            TextField(
                value = uiState.email.trim(),
                singleLine = true,
                maxLines = 1,
                onValueChange = { signUpViewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            TextField(
                value = uiState.password.trim(),
                singleLine = true,
                maxLines = 1,
                onValueChange = { signUpViewModel.onPasswordChange(it) },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(0.8f),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = uiState.selectedCountry ?: "Click here to Select Country",
                    modifier = Modifier.clickable { signUpViewModel.onCountryMenuClick() }.padding(10.dp),
                    color = Color.Red)

                DropdownMenu(
                    expanded = uiState.isCountryDropdownExpanded,
                    onDismissRequest = { signUpViewModel.onCountryDropdownDismiss() }
                ) {
                    uiState.countryList.forEach { country ->
                        DropdownMenuItem(onClick = { signUpViewModel.onCountrySelected(country) },
                            modifier = Modifier
                                .padding(2.dp)) {
                            Text(text = country, color = Color.Blue)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    when {
                        !Validators.isValidEmail(uiState.email) &&  !Validators.isValidPassword(uiState.password) -> {
                            Toast.makeText(
                                context,
                                "Invalid Email and Password",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        !Validators.isValidEmail(uiState.email) -> {
                            Toast.makeText(context, "Invalid Email Address", Toast.LENGTH_SHORT).show()
                        }

                        !Validators.isValidPassword(uiState.password) -> {
                            Toast.makeText(
                                context,
                                "Password must be at least 8 characters with one uppercase, one lowercase, one number, and one special character",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            signUpViewModel.onSignUp(context,navController)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sign Up", color = Color.White)
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BookShelfTheme {
        var navController:NavController = rememberNavController()
        SignUpScreen(navController)
    }
}