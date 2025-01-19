<template>
  <v-data-table
    :headers="headers"
    :items="players"
    item-key="name"
    items-per-page="10"
  ></v-data-table>
</template>

<script lang="ts" setup>
import {ref, onMounted} from "vue";
import type {PlayerResponse} from "@/types/PlayerResponse";
import pokerService from "@/services/PokerService";

const players = ref<PlayerResponse[]>([]);
const headers = [
  {title: 'Name', value: 'name'},
  {title: 'Nickname', value: 'nickname'},
  {title: 'Total IN', value: 'totalIn'},
  {title: 'Total OUT', value: 'totalOut'},
  {title: 'Profit', key: 'profit', value: (item: PlayerResponse) => `${item.totalOut - item.totalIn}`},
];

onMounted(async () => {
  try {
    players.value = await pokerService.getAllPlayers();
  } catch (error) {
    console.error("Error when fetch games:", error);
  }
});

</script>
