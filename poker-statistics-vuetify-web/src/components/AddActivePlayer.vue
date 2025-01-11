<template>
  <v-form @submit.prevent="submitForm">
    <div class="d-flex align-center">
      <v-select
        v-model="playerValues"
        :item-props="itemProps"
        :items="players"
        label="Player"
        :disabled="isInProcessing"
        @update:modelValue="handlePlayerChange"
        required
        :rules="[requiredRule]"
      ></v-select>
      <v-text-field
        v-model="playerValues.cashIn"
        label="Cash IN"
        type="number"
        :disabled="isInProcessing"
        :min="0"
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
        Add player to the game
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
  id: string | null,
  name: string | null,
  nickname: string | null
};
type AddingPlayer = {
  id: null | string,
  name: null | string,
  nickname: null | string,
  cashIn: number,
};
type GameData = {
  id: string,
  startDate: Date
};

const nullPlayer = {
  id: null,
  name: '-- Create new player --',
  nickname: null,
}
const props = defineProps<{
  game: GameData,
}>();
const emit = defineEmits(['data-updated']);

const players = ref<ExistedPlayer[]>([]);
const loading = ref<boolean>(false);
const error = ref<string | null>(null);
const submitting = ref<boolean>(false);
const defaultCashIn = 10;
const playerValues = ref<AddingPlayer>({
  id: null,
  name: null,
  nickname: null,
  cashIn: defaultCashIn,
});

const fetchGame = async () => {
  loading.value = true;
  error.value = null;
  try {
    const playersData = (await pokerService.getAllPlayers()).map((player) => ({
      id: player.id,
      name: player.name,
      nickname: player.nickname,
    })).sort((a, b) => a.name.localeCompare(b.name));

    players.value = [
      nullPlayer,
      ...playersData
    ];
  } catch (err: any) {
    error.value = err.message || "An error occurred";
  } finally {
    loading.value = false;
  }
};

const submitForm = async () => {
  if(props.game === null || props.game === undefined) throw new Error('Active game cannot be null');
  submitting.value = true;

  try {
    if (playerValues.value.id === null) {

    } else {
      await pokerService.addExistedPlayerToGame(props.game.id, playerValues.value.id, playerValues.value.cashIn);
    }
    emit('data-updated');
    resetForm();
  } catch (err: any) {
    alert(`Failed to add player: ${err.message}`);
  } finally {
    submitting.value = false;
  }
};

const itemProps = (item: ExistedPlayer) => {
  return {
    title: item.name,
    subtitle: item.nickname,
  }
};

const handlePlayerChange = (value: ExistedPlayer) => {
  if (value.id === null) {
    resetForm();
  } else {
    playerValues.value.name = value.name;
    playerValues.value.nickname = value.nickname;
  }
};

const resetForm = () => {
  playerValues.value.cashIn = defaultCashIn;
  playerValues.value.name = null;
  playerValues.value.nickname = null;
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
