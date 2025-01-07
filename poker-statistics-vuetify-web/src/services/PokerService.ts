import pokerClient from "@/services/PokerApiClient";
import type {ActiveGameResponse} from "@/types/ActiveGameResponse";
import type {HistoricalGameResponse} from "@/types/HistoricalGameResponse";
import type {PlayerResponse} from "@/types/PlayerResponse";

class PokerService {
  async getAllActiveGames(): Promise<ActiveGameResponse[]> {
    const response = await pokerClient.get("/api/v1/active-games")
    return response.data;
  }

  async getActiveGameFullInfo(id: string): Promise<ActiveGameResponse> {
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

}

export default new PokerService();
