package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Table(name = "active_games")
@Entity
data class ActiveGame(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val startDate: OffsetDateTime,

    @JoinColumn(name = "active_game_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val activePlayers: List<PlayerInGame>
)