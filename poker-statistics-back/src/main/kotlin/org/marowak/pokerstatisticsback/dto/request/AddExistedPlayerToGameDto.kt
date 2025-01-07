package org.marowak.pokerstatisticsback.dto.request

import java.util.UUID

data class AddExistedPlayerToGameDto(
    val playerId: UUID,
    val cashIn: Int,
)
