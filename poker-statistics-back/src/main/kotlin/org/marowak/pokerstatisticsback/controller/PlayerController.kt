package org.marowak.pokerstatisticsback.controller

import org.marowak.pokerstatisticsback.dto.request.CreateNewPlayerDto
import org.marowak.pokerstatisticsback.entity.Player
import org.marowak.pokerstatisticsback.service.PlayerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/players")
@RestController
class PlayerController(private val playerService: PlayerService) {

    @GetMapping
    fun getAll(): List<Player> {
        return playerService.getAll()
    }

    @PostMapping
    fun create(@RequestBody request: CreateNewPlayerDto): Player {
        return playerService.create(request)
    }
}