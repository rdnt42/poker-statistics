<template>
  <v-data-table
    :headers="headers"
    :items="games"
    item-key="name"
    items-per-page="5"
  ></v-data-table>
</template>

<script lang="ts" setup>
import {ref, onMounted} from "vue";
import pokerService from "@/services/PokerService";
import type {HistoricalGameResponse} from "@/types/HistoricalGameResponse";


const games = ref<HistoricalGameResponse[]>([]);
const headers = [
  {
    title: 'Start date',
    key: 'startDate',
    value: (item: HistoricalGameResponse) => new Date(item.startDate).toLocaleString()
  },
  {title: 'End date', key: 'endDate', value: (item: HistoricalGameResponse) => new Date(item.endDate).toLocaleString()},
  {title: 'Number of players', value: 'totalPlayers'},
  {title: 'Total cash', value: 'totalCash'},
];

onMounted(async () => {
  try {
    games.value = await pokerService.getHistoricalGames();
  } catch (error) {
    console.error("Error when fetch games:", error);
  }
});

</script>
