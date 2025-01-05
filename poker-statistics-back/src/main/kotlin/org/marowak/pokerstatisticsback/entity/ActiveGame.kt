package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Table(name = "active_games")
@Entity
class ActiveGame(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val startDate: OffsetDateTime
)