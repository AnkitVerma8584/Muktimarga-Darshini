package com.ass.muktimargadarshini.ui.presentation.navigation.screens.about.model

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ass.muktimargadarshini.R

val tabs = listOf(
    "About Us", "Terms and Conditions", "Privacy Policy", "Shipping Policy", "Refund Policy"
)

@Composable
fun termsAndConditionBody(): AnnotatedString = buildAnnotatedString {
    withStyle(SpanStyle(fontSize = 16.sp,color = MaterialTheme.colorScheme.onBackground)) {
        append(stringResource(id = R.string.terms_and_conditions_body1))
    }
    pushStringAnnotation(tag = "url", annotation = "https://policies.google.com/terms")
    withStyle(style = SpanStyle(color = Color.Blue)) {
        append("\nGoogle play services\n")
    }
    pop()
    withStyle(SpanStyle(fontSize = 16.sp,color = MaterialTheme.colorScheme.onBackground)) {
        append(stringResource(id = R.string.terms_and_conditions_body2))
    }
    withStyle(
        SpanStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    ) {
        append("\n\n")
        append(stringResource(id = R.string.tnc_changes))
        append("\n")
    }
    withStyle(SpanStyle(fontSize = 16.sp,color = MaterialTheme.colorScheme.onBackground)) {
        append(stringResource(id = R.string.tnc_changes_body))
    }
    withStyle(
        SpanStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    ) {
        append("\n\n")
        append(stringResource(id = R.string.contact_us))
        append("\n")
    }
    withStyle(SpanStyle(fontSize = 16.sp,color = MaterialTheme.colorScheme.onBackground)) {
        append(stringResource(id = R.string.tnc_contact_us))
    }
    append("\n")
    pushStringAnnotation(tag = "mail", annotation = stringResource(id = R.string.contact_mail))
    withStyle(style = SpanStyle(color = Color.Blue)) {
        append(stringResource(id = R.string.contact_mail))
    }
    pop()
    toAnnotatedString()
}

@Composable
fun refundBody(): AnnotatedString = buildAnnotatedString {
    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
        append(stringResource(id = R.string.refund_policy_body))
    }
    withStyle(
        SpanStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    ) {
        append("\n\n")
        append(stringResource(id = R.string.contact_us))
        append("\n")
    }
    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
        append(stringResource(id = R.string.refund_policy_contact))
    }
    append("\n")
    pushStringAnnotation(tag = "mail", annotation = stringResource(id = R.string.contact_mail))
    withStyle(style = SpanStyle(color = Color.Blue)) {
        append(stringResource(id = R.string.contact_mail))
    }
    pop()
    toAnnotatedString()
}

@Composable
fun ColumnScope.TextBody(annotatedString: AnnotatedString, scrollState: ScrollState) {
    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "mail", start = offset, end = offset)
                .firstOrNull()?.let {
                    uriHandler.openUri("mailto:${it.item}?body=I need help regarding &subject=Required help in Madhva Vahini")
                }
            annotatedString.getStringAnnotations(tag = "url", start = offset, end = offset)
                .firstOrNull()?.let {
                    uriHandler.openUri(it.item)
                }
        },
        style = TextStyle(
            textAlign = TextAlign.Justify,
            fontStyle = FontStyle(R.font.poppins_regular)
        ),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(0.9f)
            .fillMaxHeight()
            .align(Alignment.CenterHorizontally)
            .verticalScroll(scrollState)
    )
}