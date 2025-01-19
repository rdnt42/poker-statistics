package org.marowak.pokerstatisticsback.service

import jakarta.transaction.Transactional
import org.marowak.pokerstatisticsback.dto.request.PatchPlayerInGameDto
import org.marowak.pokerstatisticsback.dto.request.PlayerForActiveGameDto
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
    fun create(playerId: UUID, gameId: UUID, cashIn: Int): PlayerInGame {
        val player = PlayerInGame(
            id = playerId,
            cashIn = cashIn,
            activeGameId = gameId,
            status = PlayerStatusType.ACTIVE,
        )

        return playerInGameRepository.save(player)
    }

    @Transactional
    fun createList(requestPlayers: List<PlayerForActiveGameDto>, gameId: UUID): List<PlayerInGame> {
        val players = requestPlayers.map {
            PlayerInGame(
                id = it.id,
                activeGameId = gameId,
                cashIn = it.cashIn,
                status = PlayerStatusType.ACTIVE,
            )
        }

        return playerInGameRepository.saveAll(players)
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

    @Transactional
    fun patch(playerId: UUID, request: PatchPlayerInGameDto): PlayerInGame {
        val player = playerInGameRepository.findById(playerId)
            .orElseThrow()
        val updatedPlayer = player.copy(
            status = PlayerStatusType.ACTIVE,
            cashIn = request.cashIn ?: player.cashIn,
            cashOut = request.cashOut ?: player.cashOut,
        )

        return playerInGameRepository.save(updatedPlayer)
    }
}