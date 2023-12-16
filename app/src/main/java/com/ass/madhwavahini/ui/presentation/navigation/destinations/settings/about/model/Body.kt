package com.ass.madhwavahini.ui.presentation.navigation.destinations.settings.about.model

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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.util.print

val tabs = listOf(
    "About Us", "Terms and Conditions", "Privacy Policy", "Shipping Policy", "Refund Policy"
)

@Composable
fun aboutBody(): AnnotatedString = buildAnnotatedString {
    withStyle(
        SpanStyle(color = MaterialTheme.colorScheme.onBackground)
    ) {
        append(
            stringResource(id = R.string.about_us)
        )
    }
}

@Composable
fun termsAndConditionBody(): AnnotatedString {

    val tncBody = listOf(
        Pair(R.string.tnc_header_1, R.string.tnc_body_1),
        Pair(R.string.tnc_header_2, R.string.tnc_body_2),
        Pair(R.string.tnc_header_3, R.string.tnc_body_3),
        Pair(R.string.tnc_header_4, R.string.tnc_body_4),
        Pair(R.string.tnc_header_5, R.string.tnc_body_5)
    )

    return buildAnnotatedString {
        withStyle(SpanStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)) {
            append(stringResource(id = R.string.tnc))
        }

        append("\n\n")
        tncBody.forEach {
            withStyle(
                SpanStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground
                )
            ) {
                append(stringResource(id = it.first))
            }
            append("\n\n")
            withStyle(SpanStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)) {
                append(stringResource(id = it.second))
            }
            append("\n\n\n\n")
        }
        withStyle(
            SpanStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("\n\n\n")
            append(stringResource(id = R.string.tnc_changes))
            append("\n\n")
        }
        withStyle(SpanStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)) {
            append(stringResource(id = R.string.tnc_changes_body))
        }
        append("\n\n\n\n")
        withStyle(
            SpanStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {

            append(stringResource(id = R.string.contact_us))

        }
        append("\n\n")
        withStyle(SpanStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)) {
            append(stringResource(id = R.string.tnc_contact_us))
        }
        append(" ")
        pushStringAnnotation(tag = "mail", annotation = stringResource(id = R.string.contact_mail))
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
            append(stringResource(id = R.string.contact_mail))
        }
        pop()
        toAnnotatedString()
    }
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
    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
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
                    try {
                        uriHandler.openUri("mailto:${it.item}?body=I need help regarding &subject=Required help in Madhva Vahini")
                    } catch (e: Exception) {
                        e.print()
                    }
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