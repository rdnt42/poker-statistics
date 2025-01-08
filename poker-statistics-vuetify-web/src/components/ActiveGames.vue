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
        <td>{{ item.startDate }}</td>
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
import {ref, onMounted} from "vue";
import pokerService from "@/services/PokerService";
import type {ActiveGameFullResponse} from "@/types/ActiveGameFullResponse";

const games = ref<ActiveGameFullResponse[]>([]);
const headers = [
  {
    title: 'Start date',
    key: 'startDate',
    value: (item: ActiveGameFullResponse) => new Date(item.startDate).toLocaleString()
  },
];

onMounted(async () => {
  try {
    games.value = await pokerService.getAllActiveGames();
  } catch (error) {
    console.error("Error when fetch games:", error);
  }
});

const isModalOpen = ref<boolean>(false);
const selectedRow = ref<ActiveGameFullResponse | null>(null);

const onRowDblClick = (row: any) => {
  selectedRow.value = row;
  isModalOpen.value = true;
};

</script>
