import type {PlayerInGameResponse} from "@/types/PlayerInGameResponse";

export interface ActiveGameFullResponse {
  id: string,
  startDate: Date,
  players: PlayerInGameResponse[],
}
