package com.ass.madhwavahini.ui.presentation.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.Payment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentOptionsBottomSheet(
    sheetState: SheetState, paymentData: Payment, onDismiss: () -> Unit, onUpiSubmitClicked: (
        transactionID: String, amount: Int
    ) -> Unit, onRazorpayModeClicked: () -> Unit
) {
    var transactionId by remember {
        mutableStateOf("")
    }
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Row(modifier = Modifier.clickable { onRazorpayModeClicked() }) {
            Image(
                painter = painterResource(id = R.drawable.ic_filter), contentDescription = null
            )
            Text(text = "Pay Using razorpay")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Pay Using Upi")

        OutlinedTextField(
            value = paymentData.upi,
            onValueChange = {},
            readOnly = true,
        )

        OutlinedTextField(
            value = paymentData.phone,
            onValueChange = {},
            readOnly = true,
        )
        OutlinedTextField(value = (paymentData.amount / 100).toString(),
            onValueChange = {},
            readOnly = true,
            prefix = {
                Text(text = "Rs.")
            })
        Text(text = "UPI the amount to the above upi id or phone and submit the transaction id.")

        OutlinedTextField(value = transactionId, onValueChange = {
            transactionId = it
        }, prefix = {
            Text(text = "ID/")
        })
        Button(onClick = {
            onUpiSubmitClicked(
                transactionId, (paymentData.amount / 100)
            )
        }) {
            Text(text = "Submit")
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}