package org.marowak.pokerstatisticsback.service

import jakarta.transaction.Transactional
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
    @Transactional
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

    fun findByPlayerId(id: UUID): PlayerInGame {
        return playerInGameRepository.findById(id).orElseThrow()
    }

    @Transactional
    fun finishGame(playerId: UUID, cashOut: Int): PlayerInGame {
        val player = playerInGameRepository.findById(playerId)
            .orElseThrow()
            .copy(
                status = PlayerStatusType.FINISHED,
                cashOut = cashOut,
            )

        return playerInGameRepository.save(player)
    }

    @Transactional
    fun returnIntoGame(playerId: UUID, cashIn: Int): PlayerInGame {
        val player = playerInGameRepository.findById(playerId)
            .orElseThrow()
            .copy(
                status = PlayerStatusType.ACTIVE,
                cashIn = cashIn,
            )

        return playerInGameRepository.save(player)
    }
}