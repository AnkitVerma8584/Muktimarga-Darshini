package com.ass.muktimargadarshini.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.data.local.UserDataStore
import com.ass.muktimargadarshini.domain.modals.Payment
import com.ass.muktimargadarshini.domain.modals.User
import com.ass.muktimargadarshini.domain.repository.PaymentRepository
import com.ass.muktimargadarshini.domain.utils.StringUtil
import com.ass.muktimargadarshini.ui.presentation.payment.PaymentState
import com.ass.muktimargadarshini.util.print
import com.razorpay.PaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _orderState = MutableStateFlow(PaymentState<Payment>())
    val orderState get() = _orderState.asStateFlow()

    private val _paymentState = MutableStateFlow(PaymentState<User>())
    val paymentState get() = _paymentState.asStateFlow()

    fun getOrder() {
        viewModelScope.launch {
            paymentRepository.getPaymentOrder().collectLatest {
                _orderState.value = it
            }
        }
    }

    private fun resetOrderState() {
        _orderState.value = PaymentState()
    }

    fun resetPaymentState() {
        _paymentState.value = PaymentState()
    }

    fun paymentCancelled() {
        _paymentState.value = PaymentState(
            isLoading = false,
            data = null,
            error = StringUtil.DynamicText("Payment cancelled")
        )
    }


    fun logout() {
        viewModelScope.launch {
            userDataStore.logout()
        }
    }

    fun verifyPayment(paymentData: PaymentData) {
        resetOrderState()
        viewModelScope.launch {
            paymentRepository.verifyPayment(
                orderId = paymentData.orderId,
                paymentId = paymentData.paymentId,
                paymentSignature = paymentData.signature
            ).collectLatest {
                it.print()
                _paymentState.value = it
                it.data?.let { u ->
                    userDataStore.saveUser(u)
                }
            }
        }
    }
}