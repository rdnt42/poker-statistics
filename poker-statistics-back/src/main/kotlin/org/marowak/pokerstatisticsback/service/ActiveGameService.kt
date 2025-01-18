package org.marowak.pokerstatisticsback.service

import jakarta.transaction.Transactional
import org.marowak.pokerstatisticsback.dto.request.AddExistedPlayerToGameDto
import org.marowak.pokerstatisticsback.dto.request.FinishGameForPlayerDto
import org.marowak.pokerstatisticsback.dto.request.PatchPlayerInGameDto
import org.marowak.pokerstatisticsback.dto.request.ReturnPlayerIntoGameDto
import org.marowak.pokerstatisticsback.dto.response.*
import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.marowak.pokerstatisticsback.repository.ActiveGameRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class ActiveGameService(
    private val activeGameRepository: ActiveGameRepository,
    private val playerInGameService: PlayerInGameService,
    private val playerService: PlayerService,
    private val historicalGameService: HistoricalGameService,
) {
    fun getAll(): List<ActiveGameDto> {
        return activeGameRepository.findAll()
            .map { it.toDto() }
    }

    fun createNewGame(): ActiveGameDto {
        val game = ActiveGame(UUID.randomUUID(), OffsetDateTime.now(), emptyList())
        return activeGameRepository
            .save(game)
            .toDto()
    }

    fun getById(id: UUID): ActiveGameDto {
        return activeGameRepository
            .findById(id)
            .orElseThrow()
            .toDto()
    }

    fun getFullInfoById(id: UUID): ActiveGameFullDto {
        return activeGameRepository
            .findById(id)
            .orElseThrow()
            .toFullDto()
    }

    @Transactional
    fun addExistedPlayerToGame(gameId: UUID, playerId: UUID, request: AddExistedPlayerToGameDto) {
        val (cashIn) = request
        val game = activeGameRepository.findById(gameId).orElseThrow()
        playerInGameService.findByPlayerIdOrNull(playerId)?.let {
            throw IllegalStateException("Player ${it.id} already have active game")
        }

        playerInGameService.create(playerId, game.id, cashIn)
    }

    @Transactional
    fun finishGameForPlayer(gameId: UUID, playerId: UUID, request: FinishGameForPlayerDto) {
        val (cashOut) = request
        val player = playerInGameService.findByPlayerId(playerId)
        check(player.activeGameId == gameId) {
            "Player $playerId already have another active game"
        }

        playerInGameService.finishGame(playerId, cashOut)
    }

    @Transactional
    fun returnPlayerIntoGame(gameId: UUID, playerId: UUID, request: ReturnPlayerIntoGameDto) {
        val (cashIn) = request
        val player = playerInGameService.findByPlayerId(playerId)
        check(player.activeGameId == gameId) {
            "Player $playerId already have another active game"
        }
            check(cashIn >= player.cashIn) {
            "Player cash IN value cannot be less than the current state"
        }

        playerInGameService.returnIntoGame(playerId, cashIn)
    }

    @Transactional
    fun patchPlayer(gameId: UUID, playerId: UUID, request: PatchPlayerInGameDto) {
        val player = playerInGameService.findByPlayerId(playerId)
        check(player.activeGameId == gameId) {
            "Player $playerId already have another active game"
        }

        playerInGameService.patch(playerId, request)
    }

    @Transactional
    fun finishGame(gameId: UUID) {
        val game = activeGameRepository.findById(gameId).orElseThrow()
        playerService.updatePlayersCash(game.activePlayers)
        historicalGameService.create(game.activePlayers, game.startDate)
        this.activeGameRepository.deleteById(gameId)
    }
}