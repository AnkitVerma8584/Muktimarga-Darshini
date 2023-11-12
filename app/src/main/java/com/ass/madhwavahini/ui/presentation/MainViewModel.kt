package com.ass.madhwavahini.ui.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.data.local.dao.FilesDao
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.repository.PaymentRepository
import com.ass.madhwavahini.domain.utils.StringUtil
import com.ass.madhwavahini.ui.presentation.payment.PaymentState
import com.ass.madhwavahini.util.print
import com.razorpay.PaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val userDataStore: UserDataStore,
    private val filesDao: FilesDao,
    private val application: Application
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
                it.print()
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
            isLoading.value = true
            filesDao.getAllFileIds().forEach {
                val file = File(application.filesDir, "file_$it.txt")
                if (file.exists()) {
                    file.delete()
                }
            }
            userDataStore.logout()
            isLoading.value = false
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