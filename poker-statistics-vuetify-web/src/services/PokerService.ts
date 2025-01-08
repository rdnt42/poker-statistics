import pokerClient from "@/services/PokerApiClient";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";
import type {HistoricalGameResponse} from "@/types/HistoricalGameResponse";
import type {PlayerResponse} from "@/types/PlayerResponse";
import type {AddExistedPlayerToGameRequest} from "@/types/AddExistedPlayerToGameRequest";
import type {ActiveGameResponse} from "@/types/ActiveGameResponse";

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
  ): Promise<ActiveGameFullResponse> {
    const request: AddExistedPlayerToGameRequest = {playerId, cashIn};
    const response = await pokerClient.post(`/api/v1/active-games/${activeGameId}/add-existed-player`, request);

    return response.data;
  }

}

export default new PokerService();
