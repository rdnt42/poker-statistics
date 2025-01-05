<template>
  <v-data-table
    :headers="headers"
    :items="games"
    item-key="name"
    items-per-page="5"
    @dblclick="onRowDblClick"
  ></v-data-table>
  <ActiveGameModal
    :isOpen="isModalOpen"
    :selectedRow="selectedRow"
    @update:isOpen="isModalOpen = $event"
  />
</template>

<script setup>
import {ref, onMounted} from "vue";
import pokerClient from "@/services/PokerClient.ts";

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

const isModalOpen = ref(false);
const selectedRow = ref(null);

// Обработчик двойного клика на строке
const onRowDblClick = (event, row) => {
  console.log(row)
  console.log(event)
  selectedRow.value = row; // Сохраняем выбранную строку
  isModalOpen.value = true; // Открываем модальное окно
};
</script>
