package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.*

@Table(name = "historical_games")
@Entity
class HistoricalGame(
    @Id
    val id: UUID,

    val startDate: OffsetDateTime,

    val endDate: OffsetDateTime,

    val totalCash: Int,

    val totalPlayers: Short,
)