import pokerClient from "@/services/PokerApiClient";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";
import type {HistoricalGameResponse} from "@/types/HistoricalGameResponse";
import type {PlayerResponse} from "@/types/PlayerResponse";
import type {AddExistedPlayerToGameRequest} from "@/types/AddExistedPlayerToGameRequest";
import type {FinishGameForPlayerRequest} from "@/types/FinishGameForPlayerRequest";
import type {ReturnPlayerIntoGameRequest} from "@/types/ReturnPlayerIntoGameRequest";
import type {PlayerInGameUpdateRequest} from "@/types/PlayerInGameUpdateRequest";
import type {CreateNewGameRequest} from "@/types/CreateNewGameRequest";
import CreateNewPlayer from "@/components/players/CreateNewPlayer.vue";

class PokerService {
  async getAllActiveGames(): Promise<ActiveGameFullResponse[]> {
    const response = await pokerClient.get("/api/v1/active-games")
    return response.data;
  }

  async getActiveGameFullInfo(id: string): Promise<ActiveGameFullResponse> {
    const response = await pokerClient.get(`/api/v1/active-games/${id}/full-info`)
    return response.data;
  }

  async getHistoricalGames(): Promise<HistoricalGameResponse[]> {
    const response = await pokerClient.get("/api/v1/historical-games");
    return response.data;
  }

  async getAllPlayers(): Promise<PlayerResponse[]> {
    const response = await pokerClient.get("/api/v1/players");
    return response.data;
  }

  async addExistedPlayerToGame(
    activeGameId: string,
    playerId: string,
    cashIn: number,
  ): Promise<void> {
    const request: AddExistedPlayerToGameRequest = {cashIn};
    const url = `/api/v1/active-games/${activeGameId}/players-in-game/${playerId}/add-existed`;
    const response = await pokerClient.post(url, request);

    return response.data;
  }

  async finishGameForPlayer(
    activeGameId: string,
    playerId: string,
    cashOut: number,
  ): Promise<void> {
    const request: FinishGameForPlayerRequest = {cashOut};
    const url = `/api/v1/active-games/${activeGameId}/players-in-game/${playerId}/finish`;
    await pokerClient.post(url, request);
  }

  async finishGame(
    activeGameId: string,
  ): Promise<void> {
    const url = `/api/v1/active-games/${activeGameId}/finish`;
    await pokerClient.post(url);
  }

  async returnPlayerIntoGame(
    activeGameId: string,
    playerId: string,
    cashIn: number,
  ): Promise<void> {
    const request: ReturnPlayerIntoGameRequest = {cashIn};
    const url = `/api/v1/active-games/${activeGameId}/players-in-game/${playerId}/return`;
    await pokerClient.post(url, request);
  }

  async updateActivePlayer(
    activeGameId: string,
    playerId: string,
    options: {
      cashIn?: number,
      cashOut?: number,
    }
  ): Promise<void> {
    const request: PlayerInGameUpdateRequest = {...options};
    const url = `/api/v1/active-games/${activeGameId}/players-in-game/${playerId}`;
    await pokerClient.patch(url, request);
  }

  async createNewGame(
    players: { id: string, cashIn: number }[],
  ): Promise<void> {
    const request: CreateNewGameRequest = {players}
    const url = `/api/v1/active-games`;
    await pokerClient.post(url, request);
  }

  async createNewPlayer(name: string, nickname: number): Promise<void> {
    const request: CreateNewPlayer = {name, nickname}
    const url = `/api/v1/players`;
    await pokerClient.post(url, request);
  }

}

export default new PokerService();
