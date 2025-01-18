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
import {useActiveGameStore} from "@/stores/app";

const props = defineProps<{
  gameId: string,
}>();

const activeGameStore = useActiveGameStore();

const endGameHandle = async () => {
  await pokerService.finishGame(props.gameId);
  activeGameStore.notifyDataUpdated();
};

</script>


