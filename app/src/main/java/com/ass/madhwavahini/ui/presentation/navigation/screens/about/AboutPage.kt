package com.ass.madhwavahini.ui.presentation.navigation.screens.about

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.model.Content
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.model.TextBody
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.model.aboutBody
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.model.refundBody
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.model.tabs
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.model.termsAndConditionBody

@Composable
fun AboutPage() {
    val scrollState = rememberScrollState()
    val tabsScroll = rememberScrollState()


    val list = listOf(
        Content.AboutUs,
        Content.TermsAndCondition,
        Content.PrivacyPolicy,
        Content.ShippingPolicy,
        Content.RefundPolicy,
    )
    val currentPage = rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(state = tabsScroll)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            tabs.forEachIndexed { index, tab ->
                Text(
                    text = tab,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { currentPage.intValue = index },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (currentPage.intValue == index) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = if (currentPage.intValue == index) FontWeight.Bold else FontWeight.Medium,
                    textDecoration = if (currentPage.intValue == index) TextDecoration.Underline else TextDecoration.None
                )
            }
        }
        ContentPage(scrollState = scrollState, content = list[currentPage.intValue])
    }
}


@Composable
fun ContentPage(
    scrollState: ScrollState,
    content: Content
) {
    val body = listOf(
        aboutBody(),
        termsAndConditionBody(),
        refundBody(),
        termsAndConditionBody(),
        refundBody()
    )
    AnimatedContent(
        targetState = content,
        transitionSpec = {
            slideInHorizontally(initialOffsetX = {
                if (targetState.index > initialState.index) it else -it
            }) togetherWith slideOutHorizontally(targetOffsetX = {
                if (targetState.index > initialState.index) -it else it
            })
        }, label = "about"
    ) { targetCount ->
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = targetCount.title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextBody(annotatedString = body[content.index], scrollState)
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

