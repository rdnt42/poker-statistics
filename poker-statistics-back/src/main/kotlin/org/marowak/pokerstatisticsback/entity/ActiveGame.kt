package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Table(name = "active_games")
@Entity
class ActiveGame(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val startDate: OffsetDateTime,

    @OneToMany(fetch = FetchType.LAZY)
    val activePlayers: List<PlayerInGame>
)