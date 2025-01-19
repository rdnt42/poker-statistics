<template>
  <v-form @submit.prevent="addPlayerToGame">
    <p class="d-flex align-center mb-4">
      Select from existed or create new player
    </p>
    <div class="d-flex align-center">
      <v-icon
        color="secondary"
        class="mr-3 cursor-pointer"
        @click="resetForm"
        v-tooltip.bottom="'Reset player form'"
      >
        mdi-reload
      </v-icon>
      <v-select
        v-model="selectedPlayer"
        :item-props="playerTitleProps"
        :items="players"
        label="Player"
        :disabled="isInProcessing"
        placeholder="Select player"
      ></v-select>
      <v-text-field
        v-model="playerValues.cashIn"
        label="Cash IN"
        type="number"
        step="10"
        :min="0"
        :disabled="isInProcessing"
        :default="defaultCashIn"
        required
        :rules="[requiredRule]"
      ></v-text-field>
      <v-text-field
        v-model="playerValues.name"
        :label="isAddExistedPlayer ? 'Player name' : 'Fill name'"
        type="string"
        :disabled="isInProcessing || isAddExistedPlayer"
        required
        :rules="[requiredRule]"
      ></v-text-field>
      <v-text-field
        v-model="playerValues.nickname"
        :label="isAddExistedPlayer ? 'Player nickname' : 'Fill nickname'"
        type="string"
        :disabled="isInProcessing || isAddExistedPlayer"
        required
        :rules="[requiredRule]"
      ></v-text-field>
      <v-btn
        type="submit"
        variant="outlined"
        :loading="submitting"
        :disabled="isInProcessing || (!isPlayerNeedCreate && !isAddExistedPlayer)"
        class="ml-4"
      >
        {{ isAddExistedPlayer ? 'Add existed' : 'Create new' }}
      </v-btn>
    </div>
    <p v-if="loading">Loading players...</p>
    <p v-if="error" class="error-text">Failed to load players: {{ error }}</p>
  </v-form>
</template>

<script lang="ts" setup>
import {ref, onMounted} from 'vue';
import pokerService from "@/services/PokerService";
import {requiredRule} from "@/components/utils";

type ExistedPlayer = {
  id: string,
  name: string,
  nickname: string,
};
type AddPlayerValues = {
  id: string | null,
  name: string | null,
  nickname: string | null,
  cashIn: number,
};
type GameData = {
  id: string,
  startDate: Date,
  playersInGameIds: string[],
};

const defaultCashIn = 10;

const props = defineProps<{
  game: GameData,
}>();

const players = ref<ExistedPlayer[]>([]);
const playerValues = ref<AddPlayerValues>({
  id: null,
  name: null,
  nickname: null,
  cashIn: defaultCashIn,
});
const selectedPlayer = ref<ExistedPlayer | null>(null);

const loading = ref<boolean>(false);
const error = ref<string | null>(null);
const submitting = ref<boolean>(false);

const fetchGame = async () => {
  loading.value = true;
  error.value = null;
  try {
    // TODO return available players from backend
    const onlyNewPlayers = (await pokerService.getAllPlayers())
      .filter(player => !props.game.playersInGameIds.includes(player.id));
    players.value = onlyNewPlayers.map((player) => ({
      id: player.id,
      name: player.name,
      nickname: player.nickname,
    })).sort((a, b) => a.name.localeCompare(b.name));
  } catch (err: any) {
    error.value = err.message || "An error occurred";
  } finally {
    loading.value = false;
  }
};

const addPlayerToGame = async () => {
  submitting.value = true;

  try {
    if (playerValues.value.id !== null) {
      await pokerService.addExistedPlayerToGame(props.game.id, playerValues.value.id, playerValues.value.cashIn);
    } else {
      // TODO add new player
      console.log(`add new player ${playerValues.value.name}, ${playerValues.value.nickname}, ${playerValues.value.cashIn}`)
    }
    emit('data-updated');
    // TODO show info about result
    resetForm();
  } catch (err: any) {
    alert(`Failed to add player: ${err.message}`);
  } finally {
    submitting.value = false;
  }
};

const playerTitleProps = (item: ExistedPlayer) => {
  return {
    title: item.name,
    subtitle: item.nickname,
  }
};

const resetForm = () => {
  playerValues.value.id = null;
  playerValues.value.name = null;
  playerValues.value.nickname = null;
  playerValues.value.cashIn = defaultCashIn;
  selectedPlayer.value = null;
};

const isPlayerNeedCreate = computed((): boolean => {
  return playerValues.value.id === null &&
    playerValues.value.name !== null &&
    playerValues.value.nickname !== null;
});

const isAddExistedPlayer = computed((): boolean => {
  return playerValues.value.id !== null;
});

const isInProcessing = computed((): boolean => {
  return loading.value || submitting.value;
});

onMounted(() => {
  fetchGame();
});

watch(selectedPlayer, (newValue: ExistedPlayer | null) => {
  if(newValue === null) return;

  playerValues.value.id = newValue.id;
  playerValues.value.name = newValue.name;
  playerValues.value.nickname = newValue.nickname;
  playerValues.value.cashIn = defaultCashIn;
});
</script>

<style scoped>
.v-btn {
  background-color: #007bff;
  color: white;
}

.v-btn[disabled] {
  background-color: grey;
}
</style>
