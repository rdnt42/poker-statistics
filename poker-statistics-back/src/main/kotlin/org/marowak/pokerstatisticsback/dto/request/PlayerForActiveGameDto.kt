package org.marowak.pokerstatisticsback.dto.request

import java.util.UUID

data class PlayerForActiveGameDto(
    val id: UUID,
    val cashIn: Int,
)
