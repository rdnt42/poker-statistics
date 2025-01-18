package org.marowak.pokerstatisticsback.service

import jakarta.transaction.Transactional
import org.marowak.pokerstatisticsback.entity.HistoricalGame
import org.marowak.pokerstatisticsback.entity.PlayerInGame
import org.marowak.pokerstatisticsback.repository.HistoricalGameRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class HistoricalGameService(
    private val historicalGameRepository: HistoricalGameRepository
) {
    fun getAll(): List<HistoricalGame> {
        return historicalGameRepository.findAll()
    }

    @Transactional
    fun create(players: List<PlayerInGame>, startDate: OffsetDateTime) {
        val totalCashIn = players.sumOf { it.cashIn }
        val historicalGame = HistoricalGame(
            id = UUID.randomUUID(),
            startDate = startDate,
            endDate = OffsetDateTime.now(),
            totalCash = totalCashIn,
            totalPlayers = players.size.toShort(),
        )

        historicalGameRepository.save(historicalGame)
    }
}