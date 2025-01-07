<template>
  <v-form @submit.prevent="submitForm">
    <div class="d-flex align-center">
      <v-select
        v-model="selectedPlayerId"
        :items="players"
        item-text="name"
        item-value="id"
        label="Player"
        :disabled="loading || submitting"
        required
      ></v-select>
      <v-text-field
        v-model="cashIn"
        label="Cash IN"
        type="number"
        :disabled="submitting"
        required
      ></v-text-field>
      <v-btn
        color="primary"
        type="submit"
        :loading="submitting"
        :disabled="!selectedPlayerId || cashIn <= 0 || loading"
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

const players = ref<{ id: string; name: string }[]>([]);
const selectedPlayerId = ref<string | null>(null);
const cashIn = ref<number>(0);
const loading = ref(false);
const error = ref<string | null>(null);
const submitting = ref(false);

const fetchPlayers = async () => {
  loading.value = true;
  error.value = null;
  try {
    players.value = await PokerService.getAllPlayers();
  } catch (err: any) {
    error.value = err.message || "An error occurred";
  } finally {
    loading.value = false;
  }
};

const submitForm = async () => {
  if (!selectedPlayerId.value || cashIn.value <= 0) {
    alert("Please select a player and enter a valid Cash IN amount.");
    return;
  }

  submitting.value = true;

  try {
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

const resetForm = () => {
  selectedPlayerId.value = null;
  cashIn.value = 0;
};

onMounted(() => {
  fetchPlayers();
});
</script>
