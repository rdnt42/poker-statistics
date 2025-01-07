<template>
  <v-form @submit.prevent="submitForm">
    <div class="d-flex align-center">
      <v-select
        v-model="selectedPlayer"
        :item-props="itemProps"
        :items="players"
        label="Player"
        :disabled="isInProcessing"
        @update:modelValue="handlePlayerChange"
        required
      ></v-select>
      <v-text-field
        v-model="cashIn"
        label="Cash IN"
        type="number"
        :disabled="isInProcessing"
        :min="0"
        required
      ></v-text-field>
      <v-text-field
        v-model="playerName"
        :label="isAddExistedPlayer ? 'Player name' : 'Fill name'"
        type="string"
        :disabled="isInProcessing || isAddExistedPlayer"
        required
      ></v-text-field>
      <v-text-field
        v-model="playerNickname"
        :label="isAddExistedPlayer ? 'Player nickname' : 'Fill nickname'"
        type="string"
        :disabled="isInProcessing || isAddExistedPlayer"
        required
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
import PokerService from "@/services/PokerService";

type PlayerData = { id: string | null, name: string | null, nickname: string | null };
const nullPlayer = {
  id: null, name: '-- Create new player --', nickname: null
}

const players = ref<PlayerData  []>([]);
const selectedPlayer = ref<PlayerData>(nullPlayer);
const cashIn = ref<number>(0);
const playerName = ref<string | null>(null);
const playerNickname = ref<string | null>(null);
const loading = ref<boolean>(false);
const error = ref<string | null>(null);
const submitting = ref<boolean>(false);

const fetchPlayers = async () => {
  loading.value = true;
  error.value = null;
  try {
    const playersData = (await PokerService.getAllPlayers()).map((player) => ({
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
  submitting.value = true;

  try {
    console.log(`selectedPlayer`, selectedPlayer.value);
    // await PokerService.addPlayerToGame({
    //   playerId: selectedPlayerId.value,
    //   cashIn: cashIn.value,
    // });
    alert("Player added successfully!");
    resetForm();
  } catch (err: any) {
    alert(`Failed to add player: ${err.message}`);
  } finally {
    submitting.value = false;
  }
};

const itemProps = (item: PlayerData) => {
  return {
    title: item.name,
    subtitle: item.nickname,
  }
};

const handlePlayerChange = (value: PlayerData) => {
  if (value.id === null) {
    resetForm();
  } else {
    playerName.value = value.name;
    playerNickname.value = value.nickname;
  }
};

const resetForm = () => {
  selectedPlayer.value = nullPlayer;
  cashIn.value = 0;
  playerName.value = null;
  playerNickname.value = null;
};

const isPlayerNeedCreate = computed((): boolean => {
  return selectedPlayer.value.id === null && playerName.value !== null && playerNickname.value !== null;
});

const isAddExistedPlayer = computed((): boolean => {
  return selectedPlayer.value.id !== null;
});

const isInProcessing = computed((): boolean => {
  return loading.value || submitting.value;
});

onMounted(() => {
  fetchPlayers();
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
