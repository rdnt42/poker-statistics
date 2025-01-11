package org.marowak.pokerstatisticsback.controller

import org.marowak.pokerstatisticsback.dto.request.AddExistedPlayerToGameDto
import org.marowak.pokerstatisticsback.dto.request.FinishGameForPlayerDto
import org.marowak.pokerstatisticsback.service.ActiveGameService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/active-games/{gameId}/players-in-game")
class PlayerInGameController(
    private val activeGameService: ActiveGameService
) {

    @PostMapping("/{playerId}/add-existed")
    fun addExistedPlayer(
        @PathVariable gameId: UUID,
        @PathVariable playerId: UUID,
        @RequestBody request: AddExistedPlayerToGameDto
    ) {
        activeGameService.addExistedPlayerToGame(gameId, playerId, request)
    }

    @PostMapping("/{playerId}/finish")
    fun finishGameForPlayer(
        @PathVariable gameId: UUID,
        @PathVariable playerId: UUID,
        @RequestBody request: FinishGameForPlayerDto
    ) {
        activeGameService.finishGameForPlayer(gameId, playerId, request)
    }
}