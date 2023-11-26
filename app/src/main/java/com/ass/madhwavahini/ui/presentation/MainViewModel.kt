package com.ass.madhwavahini.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.repository.PaymentRepository
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.razorpay.PaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val userDataStore: UserDataStore,
    private val userRepository: UserRepository
) : ViewModel() {

    var user by mutableStateOf(User())
        private set

    var shouldShowPermissionRational by mutableStateOf(false)
        private set

    var shouldLogOut by mutableStateOf(false)
        private set

    fun dismissDialog() {
        shouldShowPermissionRational = false
    }

    fun onPermissionResult(
        isPermissionGranted: Boolean,
        shouldShowPermissionRationalDialog: Boolean
    ) {
        if (!isPermissionGranted && shouldShowPermissionRationalDialog) {
            shouldShowPermissionRational = true
        }
    }

    init {
        viewModelScope.launch {
            launch {
                userDataStore.userData.collectLatest {
                    user = it
                }
            }
        }
    }

    var isLoading by mutableStateOf(false)
        private set

    private val _orderState = Channel<UiState<Payment>>()
    val orderState get() = _orderState.receiveAsFlow()

    private val _paymentState = Channel<UiState<User>>()
    val paymentState get() = _paymentState.receiveAsFlow()

    fun getOrder() {
        viewModelScope.launch {
            paymentRepository.getPaymentOrder().collectLatest {
                isLoading = it.isLoading
                _orderState.send(it)
            }
        }
    }

    fun errorInPayment(error: String = "Some error occurred in payment.") {
        viewModelScope.launch {
            _paymentState.send(
                UiState(
                    isLoading = false,
                    data = null,
                    error = StringUtil.DynamicText(error)
                )
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logoutUser().collectLatest {
                isLoading = it.isLoading
                it.data?.let {
                    shouldLogOut = true
                }
            }
        }
    }

    fun verifyPayment(paymentData: PaymentData) {
        viewModelScope.launch {
            paymentRepository.verifyPayment(
                orderId = paymentData.orderId,
                paymentId = paymentData.paymentId,
                paymentSignature = paymentData.signature
            ).collectLatest {
                isLoading = it.isLoading
                _paymentState.send(it)
                it.data?.let { u ->
                    userDataStore.saveUser(u)
                }
            }
        }
    }
}