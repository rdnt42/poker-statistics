package org.marowak.pokerstatisticsback.controller

import org.marowak.pokerstatisticsback.entity.HistoricalGame
import org.marowak.pokerstatisticsback.service.HistoricalGameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/historical-games")
class HistoricalGameController(
    private val historicalGameService: HistoricalGameService
) {

    @GetMapping
    fun getAll(): List<HistoricalGame> {
        return historicalGameService.getHistoricalGames()
    }
}