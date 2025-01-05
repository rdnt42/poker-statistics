package org.marowak.pokerstatisticsback.service

import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.marowak.pokerstatisticsback.repository.ActiveGameRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class ActiveGameService(
    private val activeGameRepository: ActiveGameRepository
) {
    fun getAll(): List<ActiveGame> {
        return activeGameRepository.findAll()
    }

    fun crateNewGame(): ActiveGame {
        val game = ActiveGame(UUID.randomUUID(), OffsetDateTime.now())
        activeGameRepository.save(game)

        return game
    }

    fun getById(id: UUID): ActiveGame {
        return activeGameRepository.findById(id).orElseThrow()
    }
}