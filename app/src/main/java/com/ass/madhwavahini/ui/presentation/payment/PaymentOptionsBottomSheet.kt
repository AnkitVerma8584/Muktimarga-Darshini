package com.ass.madhwavahini.ui.presentation.payment

import android.app.Activity
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.Payment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activity.PaymentOptionsBottomSheet(
    sheetState: SheetState, paymentData: Payment, onDismiss: () -> Unit, onUpiSubmitClicked: (
        transactionID: String, amount: Int
    ) -> Unit, onRazorpayModeClicked: () -> Unit
) {
    var transactionId by remember {
        mutableStateOf("")
    }
    val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            PaymentOption(
                icon = R.drawable.ic_razorpay,
                text = "Pay via Razorpay",
                modifier = Modifier.clickable {
                    onRazorpayModeClicked.invoke()
                })
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
            Text(text = "Pay the amount to the above upi id or phone and submit the transaction id for verification.")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                value = transactionId, onValueChange = {
                    transactionId = it
                }, prefix = {
                    Text(text = "ID ")
                })
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.align(Alignment.End),
                enabled = transactionId.isNotBlank(),
                onClick = {
                    onUpiSubmitClicked(
                        transactionId, (paymentData.amount / 100)
                    )
                }) {
                Text(text = "Submit")
            }
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