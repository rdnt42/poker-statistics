package org.marowak.pokerstatisticsback.dto.response

import org.marowak.pokerstatisticsback.entity.PlayerInGame
import java.time.OffsetDateTime
import java.util.*

data class ActiveGameFullDto(
    val id: UUID,
    val startDate: OffsetDateTime,
    val players: List<PlayerInGame>
)
