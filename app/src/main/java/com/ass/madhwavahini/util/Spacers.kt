package com.ass.madhwavahini.util

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val sw4 = @Composable { GetSpacerOfWidth(width = 4) }
val sw8 = @Composable { GetSpacerOfWidth(width = 8) }
val sw12 = @Composable { GetSpacerOfWidth(width = 12) }
val sw16 = @Composable { GetSpacerOfWidth(width = 16) }

val sh4 = @Composable { GetSpacerOfHeight(height = 4) }
val sh8 = @Composable { GetSpacerOfHeight(height = 8) }
val sh12 = @Composable { GetSpacerOfHeight(height = 12) }
val sh16 = @Composable { GetSpacerOfHeight(height = 16) }

@Composable
private fun GetSpacerOfWidth(width: Int) = Spacer(modifier = Modifier.width(width.dp))

@Composable
private fun GetSpacerOfHeight(height: Int) = Spacer(modifier = Modifier.height(height.dp))
