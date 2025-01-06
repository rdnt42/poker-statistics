package org.marowak.pokerstatisticsback.dto.response

import org.marowak.pokerstatisticsback.entity.ActiveGame
import java.time.OffsetDateTime
import java.util.*

data class ActiveGameFullDto(
    val id: UUID,
    val startDate: OffsetDateTime,
    val players: List<PlayerInGameDto>
)

fun ActiveGame.toFullDto(): ActiveGameFullDto {
    return ActiveGameFullDto(
        id = id,
        startDate = startDate,
        players = activePlayers.map { it.toDto() }
    )
}
