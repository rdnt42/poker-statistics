package org.marowak.pokerstatisticsback.controller

import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.marowak.pokerstatisticsback.service.ActiveGameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/active-games")
class ActiveGameController(
    private val activeGameService: ActiveGameService
) {
    @GetMapping
    fun getAll(): List<ActiveGame> {
        return activeGameService.getAll()
    }

    @PostMapping
    fun create(): ActiveGame {
        return activeGameService.crateNewGame()
    }
}