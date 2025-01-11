package org.marowak.pokerstatisticsback.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import org.marowak.pokerstatisticsback.entity.type.PlayerStatusType
import java.util.*


@Table(name = "players_in_game")
@Entity
data class PlayerInGame(
    @Id
    val id: UUID,

    @PrimaryKeyJoinColumn
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    val player: Player? = null,

    @JoinColumn(name = "active_game_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val activeGame: ActiveGame? = null,

    @Column(name = "active_game_id")
    val activeGameId: UUID,

    val cashIn: Int,

    val cashOut: Int? = null,

    @JdbcType(PostgreSQLEnumJdbcType::class)
    @Enumerated(EnumType.STRING)
    val status: PlayerStatusType,
)