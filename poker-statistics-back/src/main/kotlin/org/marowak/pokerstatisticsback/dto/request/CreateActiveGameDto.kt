package org.marowak.pokerstatisticsback.dto.request

data class CreateActiveGameDto(
    val players: List<PlayerForActiveGameDto>,
)
