<script setup lang="ts">
import {ref, onMounted} from 'vue';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';

onMounted(() => {
  players.value = [
    {
      id: '40d9044b-cb15-404f-83d0-8718ed51d7da',
      totalIn: "30",
      totalOut: "30",
      name: 'Артем Л.',
      nickname: 'marowak',
      profit: "0",
    },
    {
      id: '5096a4a5-2b4b-46d7-acdc-0e7ad31168dc',
      totalIn: "40",
      totalOut: "10",
      name: 'Peter L.',
      nickname: '',
      profit: "-30",
    },
    {
      id: 'e37cee87-ddd0-4724-9372-0bb6cc15087a',
      totalIn: "40",
      totalOut: "90",
      name: 'Deni',
      nickname: 'mr.panamba',
      profit: "50",
    },
  ]
});

const players = ref();
const selectedPlayer = ref();
</script>

<template>
  <DataTable v-model:selection="selectedPlayer" :value="players" selectionMode="single"
             dataKey="id" tableStyle="min-width: 50rem">
    <Column field="name" header="Name"></Column>
    <Column field="nickname" header="Nickname"></Column>
    <Column field="totalIn" header="Total IN"></Column>
    <Column field="totalOut" header="Total OUT"></Column>
    <Column field="profit" header="Profit"></Column>
  </DataTable>
</template>
