<template>
  <v-dialog v-model="localIsOpen">
    <v-card>
      <v-card-title>Active game details</v-card-title>
      <v-card-text>
        <p v-if="loading">Loading...</p>
        <p v-else-if="error">Error: {{ error }}</p>
        <div v-else-if="activeGame !== null">
          <AddActivePlayer
            :game="convertToShortGameInfo(activeGame)"
            @data-updated="handleDataChanged"
          />
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
                      :game-id="activeGame.id"
                      @data-updated="handleDataChanged"
                    />
                  </div>
                  <div v-if="!isPlayerActive(item)" class="mr-2">
                    <ReturnPlayerIntoGameModal
                      :player="convertToReturnPlayer(item)"
                      :game-id="activeGame.id"
                      @data-updated="handleDataChanged"
                    />
                  </div>
                  <div class="mr-2">
                    <EditPlayerInGame
                      :player="convertToFinishPlayer(item)"
                      :game-id="activeGame.id"
                      @data-updated="handleDataChanged"
                    />
                  </div>
                </div>
              </td>
            </tr>
            </tbody>
          </v-table>
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
import AddActivePlayer from "@/components/AddActivePlayer.vue";
import {PlayerInGameStatus} from "@/types/types";

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
  } catch (err: any) {
    error.value = err.message;
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

const convertToShortGameInfo = (game: ActiveGameFullResponse) => ({
  id: game.id,
  startDate: game.startDate,
  playersInGameIds: game.players.map((p) => p.id),
});

const isPlayerActive = (player: PlayerInGameResponse) => player.status !== PlayerInGameStatus.Finished;
</script>
