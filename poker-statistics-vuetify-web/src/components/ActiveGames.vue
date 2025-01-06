<template>
  <v-data-table
    :headers="headers"
    :items="games"
    item-key="id"
    items-per-page="5"
    v-model="selectedRow"
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
import PokerService from "@/services/PokerService";
import type {ActiveGameResponse} from "@/types/ActiveGameResponse";

const games = ref<ActiveGameResponse[]>([]);
const headers = [
  {
    title: 'Start date',
    key: 'startDate',
    value: (item: ActiveGameResponse) => new Date(item.startDate).toLocaleString()
  },
];

onMounted(async () => {
  try {
    games.value = await PokerService.getAllActiveGames();
  } catch (error) {
    console.error("Error when fetch games:", error);
  }
});

const isModalOpen = ref<boolean>(false);
const selectedRow = ref<ActiveGameResponse | null>(null);

const onRowDblClick = (row: any) => {
  selectedRow.value = toRaw(row);
  isModalOpen.value = true;
};

</script>
