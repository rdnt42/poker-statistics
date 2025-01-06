package org.marowak.pokerstatisticsback.dto.response

import org.marowak.pokerstatisticsback.entity.PlayerInGame
import org.marowak.pokerstatisticsback.entity.type.PlayerStatusType
import java.util.*

data class PlayerInGameDto(
    val id: UUID,

    val cashIn: Int,

    val cashOut: Int?,

    val status: PlayerStatusType,

    val name: String,

    val nickName: String,
)

fun PlayerInGame.toDto(): PlayerInGameDto {
    return PlayerInGameDto(
        id = id,
        cashIn = cashIn,
        cashOut = cashOut,
        status = status,
        name = player.name,
        nickName = player.nickname,
    )
}
