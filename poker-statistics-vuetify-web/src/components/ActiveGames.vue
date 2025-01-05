<template>
  <v-data-table
    :headers="headers"
    :items="games"
    item-key="name"
    items-per-page="5"
  ></v-data-table>
</template>

<script>
import { ref, onMounted } from "vue";
import pokerClient from "@/services/PokerClient.ts"; // Пример: используем axios для запросов

export default {
  setup() {
    const games = ref([]);
    const headers = [
      {title: 'Start date', key: 'startDate', value: item => new Date(item.startDate).toLocaleString()},
    ]

    onMounted(async () => {
      try {
        const response = await pokerClient.get("/api/v1/active-games");
        games.value = response.data;
      } catch (error) {
        console.error("Error when fetch games:", error);
      }
    });

    return {
      games,
      headers,
    };
  },
};

</script>
