package com.ass.madhwavahini.ui.presentation.navigation.screens.about.model

import com.ass.madhwavahini.R

sealed class Content(val index: Int, val title: Int) {
    data object AboutUs : Content(0, R.string.about_us_header)
    data object TermsAndCondition : Content(1, R.string.terms_and_condition)
    data object PrivacyPolicy : Content(2, R.string.privacy_policy)
    data object ShippingPolicy : Content(3, R.string.shipping_policy)
    data object RefundPolicy : Content(4, R.string.refund)
}

