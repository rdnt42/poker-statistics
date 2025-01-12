package org.marowak.pokerstatisticsback.dto.request

data class PatchPlayerInGameDto(
    val cashIn: Int?,
    val cashOut: Int?,
)
