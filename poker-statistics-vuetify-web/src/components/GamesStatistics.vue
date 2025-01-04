<template>
  <v-data-table
    :headers="headers"
    :items="items"
    item-key="name"
    items-per-page="5"
  ></v-data-table>
</template>

<script setup>
const headers = [
  {title: 'Start date', value: 'startDate'},
  {title: 'End date', value: 'endDate'},
  {title: 'Number of players', value: 'players'},
  {title: 'Total cash', value: 'totalCash'},
]

const items = [
  {
    id: '40d9044b-cb15-404f-83d0-8718ed51d7da',
    startDate: "2024-12-30T21:48:10",
    endDate: "2024-12-31T02:03:11",
    players: 6,
    totalCash: 90,
  },
  {
    id: '5096a4a5-2b4b-46d7-acdc-0e7ad31168dc',
    startDate: "2025-01-01T03:05:46",
    endDate: "2025-01-01T06:22:02",
    players: 8,
    totalCash: 120,
  },
  {
    id: 'e37cee87-ddd0-4724-9372-0bb6cc15087a',
    startDate: "2025-01-02T19:13:33",
    endDate: "2025-01-02T23:47:33",
    players: 7,
    totalCash: 110,
  },
]
</script>
