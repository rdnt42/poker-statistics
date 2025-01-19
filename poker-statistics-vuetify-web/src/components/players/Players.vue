<template>
  <CreateNewPlayer/>
  <v-data-table
    :headers="headers"
    :items="players"
    item-key="name"
    items-per-page="10"
  ></v-data-table>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import type {PlayerResponse} from "@/types/PlayerResponse";
import pokerService from "@/services/PokerService";
import {usePlayersStore} from "@/stores/app";

let playersStore = usePlayersStore();

const players = ref<PlayerResponse[]>([]);
const headers = [
  {title: 'Name', value: 'name'},
  {title: 'Nickname', value: 'nickname'},
  {title: 'Total IN', value: 'totalIn'},
  {title: 'Total OUT', value: 'totalOut'},
  {title: 'Profit', key: 'profit', value: (item: PlayerResponse) => `${item.totalOut - item.totalIn}`},
];

const updatePlayers = async () => {
  players.value = (await pokerService.getAllPlayers())
    .sort((a, b) => a.name.localeCompare(b.name));
  playersStore.setActualData();
}

watchEffect(async () => {
  if (playersStore.isNeedToUpdate()) {
    await updatePlayers();
  }
});

</script>
