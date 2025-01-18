<template>
  <v-dialog v-model="localIsOpen">
    <v-card>
      <v-card-title>Active game details</v-card-title>
      <v-card-text>
        <p v-if="loading">Loading...</p>
        <p v-else-if="error">Error: {{ error }}</p>
        <div v-else-if="activeGame !== null">
          <ActiveGameDetails :game="activeGame"/>
          <AddActivePlayer
            :game="convertToShortGameInfo(activeGame)"
            @data-updated="handleDataChanged"
          />
          <div v-if="players !== null">
            <PlayersInGame :players="players" :game="activeGame"/>
          </div>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" text="cls" @click="closeModal">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import {ref, watch} from 'vue';
import pokerService from "@/services/PokerService";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";
import type {PlayerInGameResponse} from "@/types/PlayerInGameResponse";
import AddActivePlayer from "@/components/active-game/AddActivePlayer.vue";
import {PlayerInGameStatus} from "@/types/types";
import {useActiveGameStore} from "@/stores/app";

const props = defineProps<{
  isOpen: boolean;
  selectedRow: ActiveGameFullResponse | null;
}>();

const emit = defineEmits(['update:isOpen']);

const loading = ref(false);
const error = ref(null);
const activeGame = ref<ActiveGameFullResponse | null>(null);
const players = ref<PlayerInGameResponse[] | null>(null);

const localIsOpen = ref(props.isOpen);

const activeGameStore = useActiveGameStore()

// TODO rework
watchEffect(() => {
  if (activeGameStore.needToUpdate() && props.selectedRow) {
    fetchActiveGameFullInfo(props.selectedRow.id);
  }
})
watchEffect(() => {
  if (props.selectedRow) {
    fetchActiveGameFullInfo(props.selectedRow.id);
  }
});
watch(() => props.isOpen, (newVal) => {
  localIsOpen.value = newVal;
});

watch(localIsOpen, (newVal) => {
  emit('update:isOpen', newVal);
  if (newVal && props.selectedRow) {
    fetchActiveGameFullInfo(props.selectedRow.id);
  }
});

const fetchActiveGameFullInfo = async (id: string) => {
  loading.value = true;
  error.value = null;
  activeGame.value = null;
  try {
    activeGame.value = await pokerService.getActiveGameFullInfo(id);
    players.value = activeGame.value.players.sort((a, b) =>
      Number(a.status === PlayerInGameStatus.Finished) - Number(b.status === PlayerInGameStatus.Finished) ||
      a.name.localeCompare(b.name)
    );
    activeGameStore.setActualData();
  } catch (err: any) {
    error.value = err.message;
    activeGameStore.clearData();
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  localIsOpen.value = false;
};

const handleDataChanged = () => {
  if (props.selectedRow) {
    fetchActiveGameFullInfo(props.selectedRow.id);
  }
};

const convertToShortGameInfo = (game: ActiveGameFullResponse) => ({
  id: game.id,
  startDate: game.startDate,
  playersInGameIds: game.players.map((p) => p.id),
});

</script>
