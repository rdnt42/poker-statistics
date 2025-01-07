package org.marowak.pokerstatisticsback.service

import jakarta.transaction.Transactional
import org.marowak.pokerstatisticsback.dto.request.AddExistedPlayerToGameDto
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
    fun addExistedPlayerToGame(id: UUID, request: AddExistedPlayerToGameDto): ActiveGameFullDto {
        val (playerId, cashIn) = request
        val game = activeGameRepository.findById(id).orElseThrow()
        playerInGameService.findByPlayerIdOrNull(playerId)?.let {
            throw IllegalStateException("Player ${it.playerId} already have active game")
        }

        playerInGameService.create(playerId, game.id, cashIn)

        return activeGameRepository
            .findById(id)
            .orElseThrow()
            .toFullDto()
    }
}