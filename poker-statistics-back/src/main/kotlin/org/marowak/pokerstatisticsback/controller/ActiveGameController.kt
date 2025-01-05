package org.marowak.pokerstatisticsback.controller

import org.marowak.pokerstatisticsback.dto.response.ActiveGameDto
import org.marowak.pokerstatisticsback.dto.response.ActiveGameFullDto
import org.marowak.pokerstatisticsback.entity.ActiveGame
import org.marowak.pokerstatisticsback.service.ActiveGameService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/active-games")
class ActiveGameController(
    private val activeGameService: ActiveGameService
) {
    @GetMapping
    fun getAll(): List<ActiveGame> {
        return activeGameService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ActiveGameDto {
        return activeGameService.getById(id)
    }

    @GetMapping("/{id}/full-info")
    fun getFullInfoById(@PathVariable id: UUID): ActiveGameFullDto {
        return activeGameService.getFullInfoById(id)
    }

    @PostMapping
    fun create(): ActiveGame {
        return activeGameService.crateNewGame()
    }
}