package com.learning.bookshelf.ui.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.learning.bookshelf.api.CountryRetrofitInstance
import com.learning.bookshelf.api.Repository
import com.learning.bookshelf.util.HashUtil
import com.learning.bookshelf.util.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val countryList: List<String> = emptyList(),
    val selectedCountry: String? = null,
    val isCountryDropdownExpanded: Boolean = false
)

class SignUpViewModel(private val repository: Repository = Repository()) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    init {
        viewModelScope.launch {
            val countriesFromApi = repository.getCountriesFromApi()
            val countryNames = countriesFromApi.map { it.country }
            _uiState.value = _uiState.value.copy(countryList = countryNames)
        }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onCountryMenuClick() {
        _uiState.value = _uiState.value.copy(isCountryDropdownExpanded = true)
    }

    fun onCountryDropdownDismiss() {
        _uiState.value = _uiState.value.copy(isCountryDropdownExpanded = false)
    }

    fun onCountrySelected(country: String) {
        _uiState.value = _uiState.value.copy(selectedCountry = country, isCountryDropdownExpanded = false)
    }

    fun onSignUp(context: Context,navController: NavController) {
        viewModelScope.launch {
            val email = _uiState.value.email
            val password = _uiState.value.password

            val hashedpwd = HashUtil.hashPwd(password)

            PreferenceManager.storeUserCredentials(context,email,hashedpwd)
            navController.navigate("login_screen")
        }
    }
}
