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
  {title: 'Name', value: 'name'},
  {title: 'Nickname', value: 'nickname'},
  {title: 'Total IN', value: 'totalIn'},
  {title: 'Total OUT', value: 'totalOut'},
]

const items = [
  {
    id: '40d9044b-cb15-404f-83d0-8718ed51d7da',
    totalIn: "30",
    totalOut: "30",
    name: 'Артем Л.',
    nickname: 'radiant42',
  },
  {
    id: '5096a4a5-2b4b-46d7-acdc-0e7ad31168dc',
    totalIn: "40",
    totalOut: "10",
    name: 'Mister Twister.',
    nickname: '',
  },
  {
    id: 'e37cee87-ddd0-4724-9372-0bb6cc15087a',
    totalIn: "40",
    totalOut: "90",
    name: 'Koala',
    nickname: 'mr.koala',
  },
]
</script>
