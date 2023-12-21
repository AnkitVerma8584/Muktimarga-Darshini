package com.ass.madhwavahini.ui.presentation.main.components.payment

import android.app.Activity
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ass.madhwavahini.BuildConfig
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.razorpay.Checkout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONObject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activity.PaymentBottomSheetModule(
    snackBarHostState: SnackbarHostState,
    orderState: Flow<UiState<Payment>>,
    paymentState: Flow<UiState<User>>,
    user: User,
    onError: () -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    val ctx = LocalContext.current

    // PAYMENT PAGE
    var paymentData by remember {
        mutableStateOf<Payment?>(null)
    }

    LaunchedEffect(key1 = lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            orderState.collectLatest { order ->
                paymentData = order.data
            }
        }
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            paymentState.collectLatest { payment ->
                payment.data?.let {
                    snackBarHostState.showSnackbar(
                        message = "Payment Verified.",
                        duration = SnackbarDuration.Short
                    )
                }
                payment.error?.let { txt ->
                    snackBarHostState.showSnackbar(
                        message = txt.asString(context = ctx),
                        duration = SnackbarDuration.Short,
                        actionLabel = SnackBarType.ERROR.name
                    )
                }
            }
        }
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    paymentData?.let { payment ->
        PaymentOptionsBottomSheet(sheetState = sheetState,
            paymentData = payment,
            onDismiss = { paymentData = null },
            onRazorpayModeClicked = {
                startPayment(
                    paymentData = payment,
                    name = user.userName,
                    phoneNumber = user.userPhone,
                    onError = onError
                )
                paymentData = null
            })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Activity.PaymentOptionsBottomSheet(
    sheetState: SheetState,
    paymentData: Payment,
    onDismiss: () -> Unit,
    onRazorpayModeClicked: () -> Unit
) {
    val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            PaymentOption(
                icon = R.drawable.ic_razorpay,
                text = "Pay via Razorpay",
                modifier = Modifier.clickable(onClick = onRazorpayModeClicked)
            )
            Spacer(modifier = Modifier.height(30.dp))
            PaymentOption(icon = R.drawable.ic_upi, text = "Pay via Upi")

            Spacer(modifier = Modifier.height(10.dp))
            UpiInfoField(
                "Upi Id",
                paymentData.upi,
                clipboardManager
            ) {
                Icon(imageVector = Icons.Default.PermIdentity, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(10.dp))
            UpiInfoField(
                "PhonePe number",
                paymentData.phone,
                clipboardManager
            ) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = null)
            }
            Spacer(modifier = Modifier.height(10.dp))
            UpiInfoField(
                "Amount to be paid",
                (paymentData.amount / 100).toString(),
                clipboardManager
            ) {
                Text(text = "Rs.")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Pay the amount to the above upi id or phone and contact the phone number for verification.")

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun PaymentOption(
    icon: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = text)
    }
}

private fun Activity.startPayment(
    paymentData: Payment,
    name: String,
    phoneNumber: String,
    onError: () -> Unit,
) {
    val checkout = Checkout()
    checkout.setKeyID(BuildConfig.LIVE_KEY)
    checkout.setImage(R.mipmap.ic_launcher)
    try {
        val options = JSONObject()
        options.put("name", "Madhva Vahini")
        options.put("description", "Payment for Madhva Vahini app")
        options.put("order_id", paymentData.orderId)
        options.put("theme.color", "#934B00")
        options.put("currency", "INR")
        options.put("prefill.email", "$name@gmail.com")
        options.put("prefill.contact", phoneNumber)
        options.put("amount", paymentData.amount)
        val retryObj = JSONObject()
        retryObj.put("enabled", true)
        retryObj.put("max_count", 4)
        options.put("retry", retryObj)
        checkout.open(this, options)
    } catch (e: Exception) {
        onError.invoke()
    }
}