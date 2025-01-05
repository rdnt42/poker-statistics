package org.marowak.pokerstatisticsback.dto.response

import java.time.OffsetDateTime
import java.util.*

data class ActiveGameDto(
    val id: UUID,
    val startDate: OffsetDateTime,
)
