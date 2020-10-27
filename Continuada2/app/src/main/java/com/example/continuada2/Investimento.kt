package com.example.continuada2

import java.math.BigDecimal
import java.util.*

data class Investimento(
    val id: Int?,
    val aporteMensal: BigDecimal,
    val mes: String,
    val ano: Int
)