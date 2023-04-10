package com.ass.muktimargadarshini.ui.presentation.navigation.screens.about.model

import com.ass.muktimargadarshini.R

sealed class Content(val index: Int, val title: Int) {
    object AboutUs : Content(0, R.string.about_us_header)
    object TermsAndCondition : Content(1, R.string.terms_and_condition)
    object PrivacyPolicy : Content(2, R.string.privacy_policy)
    object ShippingPolicy : Content(3, R.string.shipping_policy)
    object RefundPolicy : Content(4, R.string.refund)
}

