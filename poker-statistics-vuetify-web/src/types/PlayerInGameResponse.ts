import type {PlayerInGameStatus} from "@/types/types";

export interface PlayerInGameResponse {
  id: string,
  name: string,
  nickname: string,
  cashIn: number,
  cashOut?: number,
  status: PlayerInGameStatus,
}
