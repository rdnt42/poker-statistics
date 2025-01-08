package org.marowak.pokerstatisticsback.service

import org.marowak.pokerstatisticsback.entity.PlayerInGame
import org.marowak.pokerstatisticsback.entity.type.PlayerStatusType
import org.marowak.pokerstatisticsback.repository.PlayerInGameRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class PlayerInGameService(
    private val playerInGameRepository: PlayerInGameRepository
) {
    fun create(playerId: UUID, activeGameId: UUID, cashIn: Int): PlayerInGame {
        val player = PlayerInGame(
            id = playerId,
            cashIn = cashIn,
            activeGameId = activeGameId,
            status = PlayerStatusType.ACTIVE,
        )

        return playerInGameRepository.save(player)
    }

    fun findByPlayerIdOrNull(id: UUID): PlayerInGame? {
        return playerInGameRepository.findById(id).getOrNull()
    }
}