<template>
  <v-data-table
    :headers="headers"
    :items="players"
    item-key="name"
    items-per-page="10"
  ></v-data-table>
</template>

<script>
import {ref, onMounted} from "vue";
import pokerClient from "@/services/PokerClient.ts"; // Пример: используем axios для запросов

export default {
  setup() {
    const players = ref([]);
    const headers = [
      {title: 'Name', value: 'name'},
      {title: 'Nickname', value: 'nickname'},
      {title: 'Total IN', value: 'totalIn'},
      {title: 'Total OUT', value: 'totalOut'},
      {title: 'Profit', key: 'profit', value: item => `${item.totalIn - item.totalOut}`},
    ]

    onMounted(async () => {
      try {
        const response = await pokerClient.get("/api/v1/players");
        players.value = response.data;
      } catch (error) {
        console.error("Error when fetch games:", error);
      }
    });

    return {
      players,
      headers,
    };
  },
};

</script>
