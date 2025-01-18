<template>
  <v-table>
    <thead>
    <tr>
      <th class="text-left" scope="col">
        Name
      </th>
      <th class="text-left" scope="col">
        Nickname
      </th>
      <th class="text-left" scope="col">
        Cash IN
      </th>
      <th class="text-left" scope="col">
        Cash OUT
      </th>
      <th class="text-left" scope="col">
        Status
      </th>
      <th class="text-left" scope="col">
        Actions
      </th>
    </tr>
    </thead>
    <tbody>
    <tr
      v-for="item in players"
      :key="item.name"
    >
      <td>{{ item.name }}</td>
      <td>{{ item.nickname }}</td>
      <td>{{ item.cashIn }}</td>
      <td>{{ item.cashOut }}</td>
      <td>{{ item.status }}</td>
      <td>
        <div class="d-flex align-center justify-start">
          <div v-if="isPlayerActive(item)" class="mr-2">
            <FinishGameForPlayerModal
              :player="convertToFinishPlayer(item)"
              :game-id="game.id"
            />
          </div>
          <div v-if="!isPlayerActive(item)" class="mr-2">
            <ReturnPlayerIntoGameModal
              :player="convertToReturnPlayer(item)"
              :game-id="game.id"
            />
          </div>
          <div class="mr-2">
            <EditPlayerInGame
              :player="convertToFinishPlayer(item)"
              :game-id="game.id"
            />
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </v-table>
</template>

<script lang="ts" setup>

import type {PlayerInGameResponse} from "@/types/PlayerInGameResponse";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";
import {PlayerInGameStatus} from "@/types/types";

defineProps<{
  players: PlayerInGameResponse[],
  game: ActiveGameFullResponse,
}>();

const convertToFinishPlayer = (player: PlayerInGameResponse) => ({
  id: player.id,
  name: player.name,
  nickname: player.nickname,
  cashIn: player.cashIn,
  cashOut: player.cashOut ?? null,
});

const convertToReturnPlayer = (player: PlayerInGameResponse) => ({
  id: player.id,
  name: player.name,
  nickname: player.nickname,
  cashIn: player.cashIn,
  cashOut: player.cashOut || 0,
});

const isPlayerActive = (player: PlayerInGameResponse) => player.status !== PlayerInGameStatus.Finished;

</script>
