export interface CreateNewGameRequest {
  players: PlayerForActiveGame[],
}

export interface PlayerForActiveGame {
  id: string,
  cashIn: number,
}
