package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.*
import org.marowak.pokerstatisticsback.entity.type.PlayerStatusType
import java.util.*


@Table(name = "players_in_game")
@Entity
class PlayerInGame(
    @Id
    val id: UUID,

    @PrimaryKeyJoinColumn
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    val player: Player? = null,

    val playerId: UUID,

    @JoinColumn(name = "active_game_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val activeGame: ActiveGame? = null,

    @Column(name = "active_game_id")
    val activeGameId: UUID,

    val cashIn: Int,

    val cashOut: Int? = null,

    @Enumerated(EnumType.STRING)
    val status: PlayerStatusType,
)