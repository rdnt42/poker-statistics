<template>
  <v-card>
    <v-tabs
      v-model="activeTab"
      align-tabs="center"
      bg-color="deep-purple-accent-4"
      stacked
    >
      <v-tab value="0">
        <v-icon icon="mdi-poker-chip"></v-icon>
        Games
      </v-tab>

      <v-tab value="1">
        <v-icon icon="mdi-account-group"></v-icon>
        Players
      </v-tab>
    </v-tabs>

    <v-tabs-window v-model="activeTab">
      <v-window-item v-for="(tab, index) in tabs" :key="index" :value="index">
        <component :is="tab.component" />
      </v-window-item>
    </v-tabs-window>
  </v-card>
</template>

<script>
import { markRaw } from "vue";
import GamesStatistics from "@/components/GamesStatistics.vue";
import PlayersStatistics from "@/components/PlayersStatistics.vue";

export default {
  data() {
    return {
      activeTab: 0,
      tabs: [
        {name: "Games", component: markRaw(GamesStatistics)},
        {name: "Players", component: markRaw(PlayersStatistics)},
      ],
    };
  },
};
</script>
