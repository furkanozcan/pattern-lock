package com.furkanozcan.patternlock.ui.component.dotview.model

data class Dot(
    val rowIndex: Float = 0.0f,
    val columnIndex: Float = 0.0f,
    val leftPoint: Int = 0,
    val rightPoint: Int = 0,
    val topPoint: Int = 0,
    val bottomPoint: Int = 0,
    val key: String? = null
)