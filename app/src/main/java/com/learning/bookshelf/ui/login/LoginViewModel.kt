package com.learning.bookshelf.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.learning.bookshelf.util.HashUtil
import com.learning.bookshelf.util.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    var loginErrorMessage: String? = null
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onLogin(context: Context,navController: NavController) {
        viewModelScope.launch {
            val email = _uiState.value.email
            val password = _uiState.value.password

            val storedEmail = PreferenceManager.getUserEmail(context)
            val storedPasswordHash = PreferenceManager.getPassword(context)

            val hashedEnteredPassword = HashUtil.hashPwd(password)

            if (storedEmail == email && storedPasswordHash == hashedEnteredPassword) {
                _uiState.value.loginErrorMessage = "Login Success"
                _uiState.value = _uiState.value.copy(isLoginSuccessful = true)
                navController.navigate("book_list_screen")
            } else {
                _uiState.value = _uiState.value.copy(loginErrorMessage = "Invalid email or password")
            }
        }
    }
}
