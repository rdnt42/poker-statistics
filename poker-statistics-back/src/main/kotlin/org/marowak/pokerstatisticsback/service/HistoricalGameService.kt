package org.marowak.pokerstatisticsback.service

import org.marowak.pokerstatisticsback.entity.HistoricalGame
import org.marowak.pokerstatisticsback.repository.HistoricalGameRepository
import org.springframework.stereotype.Service

@Service
class HistoricalGameService(
    private val historicalGameRepository: HistoricalGameRepository
) {
    fun getHistoricalGames(): List<HistoricalGame> {
        return historicalGameRepository.findAll()
    }
}