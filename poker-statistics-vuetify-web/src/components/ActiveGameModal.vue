<template>
  <v-dialog v-model="localIsOpen">
    <v-card>
      <v-card-title>Active game details</v-card-title>
      <v-card-text>
        <p v-if="loading">Loading...</p>
        <p v-else-if="error">Error: {{ error }}</p>
        <div v-else>
          <AddActivePlayer
            :game="selectedRow ? {id: selectedRow.id, startDate: selectedRow.startDate} : null"
            @data-updated="handleAddActivePlayerEvent"
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
                <v-icon
                  color="red"
                  class="cursor-pointer"
                  @click="openFinishGameForPlayerModal(item)"
                >
                  mdi-close-circle
                </v-icon>
              </td>
            </tr>
            </tbody>
          </v-table>
        </div>
      </v-card-text>
      <FinishGameForPlayerModal
        :isOpen="isFinishGameModalOpen"
        @update:isOpen="isFinishGameModalOpen = $event"
      />
      <v-card-actions>
        <v-btn color="primary" text="cls" @click="closeModal">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import {type PropType, ref, watch} from 'vue';
import pokerService from "@/services/PokerService";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";
import type {PlayerInGameResponse} from "@/types/PlayerInGameResponse";
import AddActivePlayer from "@/components/AddActivePlayer.vue";

const props = defineProps({
  isOpen: Boolean,
  selectedRow: Object as PropType<ActiveGameFullResponse | null>,
});

const emit = defineEmits(['update:isOpen']);

const loading = ref(false);
const error = ref(null);
const activeGame = ref<ActiveGameFullResponse | null>(null);
const players = ref<PlayerInGameResponse[] | null>(null);

const isFinishGameModalOpen = ref<boolean>(false);
const selectedPlayerForFinish = ref<PlayerInGameResponse | null>(null);

const localIsOpen = ref(props.isOpen);

watch(() => props.isOpen, (newVal) => {
  localIsOpen.value = newVal;
});

watch(localIsOpen, (newVal) => {
  emit('update:isOpen', newVal);
  if (newVal && props.selectedRow) {
    fetchAdditionalData(props.selectedRow.id);
  }
});

const fetchAdditionalData = async (id: string) => {
  loading.value = true;
  error.value = null;
  activeGame.value = null;
  try {
    activeGame.value = await pokerService.getActiveGameFullInfo(id);
    players.value = activeGame.value.players;
  } catch (err: any) {
    error.value = err.message;
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  localIsOpen.value = false;
};

const handleAddActivePlayerEvent = () => {
  if (props.selectedRow) {
    fetchAdditionalData(props.selectedRow.id);
  }
};

const openFinishGameForPlayerModal = (player: PlayerInGameResponse) => {
  selectedPlayerForFinish.value = player;
  isFinishGameModalOpen.value = true;
};
</script>
