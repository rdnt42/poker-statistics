<template>
  <div>
    <v-btn
      color="primary"
      variant="outlined"
      @click="endGameHandle"
    >
      END GAME FOR ALL
    </v-btn>
  </div>
</template>

<script lang="ts" setup>

import pokerService from "@/services/PokerService";
import {useActiveGamesStore, useActiveGameStore} from "@/stores/app";

const props = defineProps<{
  gameId: string,
}>();

const activeGameStore = useActiveGameStore();
const activeGamesStore = useActiveGamesStore();

const endGameHandle = async () => {
  await pokerService.finishGame(props.gameId);
  activeGameStore.clearData();
  activeGamesStore.setNeedToUpdate();
};

</script>


