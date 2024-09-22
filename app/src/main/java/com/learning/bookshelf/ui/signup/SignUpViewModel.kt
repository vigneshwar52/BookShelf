package com.learning.bookshelf.ui.signup

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.learning.bookshelf.api.CountryRetrofitInstance
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

class SignUpViewModel:ViewModel() {
    private val TAG = SignUpViewModel::class.java.simpleName
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState
    init {
        viewModelScope.launch {
            try {
                val countriesFromApi = CountryRetrofitInstance.api.getAllCountries()
                val countryNames = countriesFromApi.map { it.country }
                _uiState.value = _uiState.value.copy(countryList = countryNames)
            }catch (e: Exception) {
                Log.d(TAG, "Failed to fetch country list")
            }
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
