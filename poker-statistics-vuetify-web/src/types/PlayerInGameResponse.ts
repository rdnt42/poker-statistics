import type {PlayerInGameStatus} from "@/types/types";

export interface PlayerInGameResponse {
  id: string,
  cashIn: number,
  cashOut?: number,
  status: PlayerInGameStatus,
}
