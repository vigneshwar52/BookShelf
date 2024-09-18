package com.learning.bookshelf.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.learning.bookshelf.api.CountryRetrofitInstance
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

class SignUpViewModel(private val countryList: CountryRetrofitInstance = CountryRetrofitInstance) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    init {
        viewModelScope.launch {
//            val countries = countryList.countryApi.getAllCountries()
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

    fun onSignUp(navController: NavController) {
        // Handle sign-up logic and navigate to the login screen
        navController.navigate("login_screen")
    }
}
