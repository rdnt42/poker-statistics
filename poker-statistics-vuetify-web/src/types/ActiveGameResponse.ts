import type {PlayerInGameResponse} from "@/types/PlayerInGameResponse";

export interface ActiveGameResponse {
  id: string,
  startDate: Date,
  players: PlayerInGameResponse[],
}
