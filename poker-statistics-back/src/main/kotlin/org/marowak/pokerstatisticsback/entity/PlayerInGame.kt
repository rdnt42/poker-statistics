package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.*
import org.marowak.pokerstatisticsback.entity.type.PlayerStatusType
import java.util.*

@Table(name = "players_in_game")
@Entity
class PlayerInGame(
    @Id
    val id: UUID,

    val cashIn: Int,

    val cashOut: Int?,

    @Enumerated(EnumType.STRING)
    val status: PlayerStatusType
)