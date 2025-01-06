package org.marowak.pokerstatisticsback.dto.response

import org.marowak.pokerstatisticsback.entity.ActiveGame
import java.time.OffsetDateTime
import java.util.*

data class ActiveGameDto(
    val id: UUID,
    val startDate: OffsetDateTime,
)

fun ActiveGame.toDto(): ActiveGameDto {
    return ActiveGameDto(id, startDate)
}
