<template>
  <v-data-table
    :headers="headers"
    :items="games"
    item-key="id"
    items-per-page="5"
    v-model:selectedItem="selectedRow"
    class="elevation-1"
  >
    <template v-slot:item="{ item }">
      <tr @dblclick="onRowDblClick(item)">
        <td>{{ new Date(item.startDate).toLocaleString() }}</td>
      </tr>
    </template>
  </v-data-table>
  <ActiveGameModal
    :isOpen="isModalOpen"
    :selectedRow="selectedRow"
    @update:isOpen="isModalOpen = $event"
  />
</template>

<script lang="ts" setup>
import {ref} from "vue";
import pokerService from "@/services/PokerService";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";
import {useActiveGamesStore} from "@/stores/app";

const activeGamesStore = useActiveGamesStore();

const games = ref<ActiveGameFullResponse[]>([]);
const headers = [
  {
    title: 'Start date',
    key: 'startDate',
  },
];

const updateActiveGames = async () => {
  games.value = await pokerService.getAllActiveGames();
  activeGamesStore.setActualData();
};

watchEffect(async () => {
  if (activeGamesStore.isNeedToUpdate()) {
    console.log('update active games');
    await updateActiveGames();
  }
});

const isModalOpen = ref<boolean>(false);
const selectedRow = ref<ActiveGameFullResponse | null>(null);

const onRowDblClick = (row: any) => {
  selectedRow.value = row;
  isModalOpen.value = true;
};

</script>
