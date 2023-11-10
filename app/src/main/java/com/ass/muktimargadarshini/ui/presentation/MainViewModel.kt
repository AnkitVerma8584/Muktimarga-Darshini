package com.ass.muktimargadarshini.ui.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.domain.modals.Payment
import com.ass.muktimargadarshini.domain.modals.User
import com.ass.muktimargadarshini.domain.repository.PaymentRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.payment.PaymentState
import com.razorpay.PaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user get() = _user.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                userDataStore.userData.collectLatest {
                    _user.value = it
                }
            }
        }
    }

    var isLoading = mutableStateOf(false)
        private set


    private val _orderState = Channel<PaymentState<Payment>>()
    val orderState get() = _orderState.receiveAsFlow()

    private val _paymentState = Channel<PaymentState<User>>()
    val paymentState get() = _paymentState.receiveAsFlow()

    fun getOrder() {
        viewModelScope.launch {

            paymentRepository.getPaymentOrder().collectLatest {
                isLoading.value = it.isLoading
                _orderState.send(it)
            }
        }
    }

    fun paymentCancelled() {
        viewModelScope.launch {
            _paymentState.send(
                PaymentState(
                    isLoading = false,
                    data = null,
                    error = StringUtil.DynamicText("Payment cancelled")
                )
            )
        }
    }

    fun errorInPayment() {
        viewModelScope.launch {
            _paymentState.send(
                PaymentState(
                    isLoading = false,
                    data = null,
                    error = StringUtil.DynamicText("Some error occurred in payment.")
                )
            )
        }
    }


    fun logout() {
        viewModelScope.launch {
            userDataStore.logout()
        }
    }

    fun verifyPayment(paymentData: PaymentData) {
        viewModelScope.launch {
            paymentRepository.verifyPayment(
                orderId = paymentData.orderId,
                paymentId = paymentData.paymentId,
                paymentSignature = paymentData.signature
            ).collectLatest {
                isLoading.value = it.isLoading
                _paymentState.send(it)
                it.data?.let { u ->
                    userDataStore.saveUser(u)
                }
            }
        }
    }
}