<template>
  <v-form>
    <v-container>
      <v-row>
        <v-col cols="12" sm="3">
          <v-text-field
            variant="outlined"
            label="Start"
            :model-value="new Date(game.startDate).toLocaleString()"
            readonly
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="3">
          <v-text-field
            variant="outlined"
            label="Duration"
            :model-value="getDurationText()"
            readonly
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="3">
          <v-text-field
            variant="outlined"
            label="Players"
            :model-value="game.players.length"
            readonly
          ></v-text-field>
        </v-col>
        <v-col cols="12" sm="2">
          <FinishGame :game-id="game.id"/>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script lang="ts" setup>
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";

type GameDuration = {
  hours: number,
  minutes: number,
  seconds: number,
}
const props = defineProps<{
  game: ActiveGameFullResponse,
}>();

const timer = ref();
const gameDuration = ref<GameDuration>();

onMounted(() => {
  refreshGameTime();
  timer.value = setInterval(() => {
    refreshGameTime();
  }, 1000);
});

const refreshGameTime = () => {
  const start = new Date(props.game.startDate).getTime();
  const difference = new Date().getTime() - start;
  const hours = Math.floor(difference / (1000 * 60 * 60)); // переводим в часы
  const minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60)); // переводим в минуты
  const seconds = Math.floor((difference % (1000 * 60)) / 1000); // переводим в секунды

  gameDuration.value = {
    hours,
    minutes,
    seconds,
  };
};

const getDurationText = (): string => {
  const time = gameDuration.value;
  if (!time) return ' ';

  return `${time.hours}h ${time.minutes}m ${time.seconds}s`;
};

</script>


