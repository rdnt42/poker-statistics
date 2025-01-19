<template>
  <div>
    <v-dialog
      v-model="dialog"
      max-width="800"
    >
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          v-bind="activatorProps"
          color="primary"
          text="Create new game"
          variant="outlined"
        ></v-btn>
      </template>

      <v-card
        prepend-icon="mdi-account"
        title="Start new game"
      >
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" v-for="(row, index) in rows" :key="row.rowId">
                <v-row class="d-flex align-center">
                  <v-col cols="11">
                    <AddPlayerToNewGame
                      :players="players"
                      :index="index"
                      :selected-player="row"
                      @selectNewPlayer="selectNewPlayerHandler"
                      @updateCashIn="updateCashInHandler"
                    />
                  </v-col>
                  <v-col cols="1" class="d-flex justify-end">
                    <v-btn icon @click="removeRow(index)">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>

            <v-btn
              block
              fab
              color="primary"
              @click="addRow"
              class="mt-4"
            >
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </v-container>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            text="Close"
            variant="plain"
            @click="dialog = false"
          ></v-btn>

          <v-btn
            color="primary"
            text="Create game"
            variant="tonal"
            @click="createNewGame"
            :disabled="isEmptySelectedPlayers"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, shallowRef} from 'vue'
import pokerService from "@/services/PokerService";
import {useActiveGamesStore} from "@/stores/app";

type PlayerRow = {
  rowId: string,
  id: string,
  cashIn: number,
  name: string,
  nickname: string,
};

const dialog = shallowRef(false);
const players = ref<PlayerRow[]>();
const rows = ref<PlayerRow[] | { rowId: string }[]>([]);

onMounted(async () => {
  await reset();
});

const activeGamesStore = useActiveGamesStore();

const createNewGame = async () => {
  let players = rows.value.filter((r) => r.id !== undefined);
  await pokerService.createNewGame(players);
  activeGamesStore.setNeedToUpdate();
  await reset();
  dialog.value = false;
};

const addRow = (): void => {
  rows.value.push({
    rowId: getNewId(),
  });
};

const removeRow = (index: number): void => {
  const player = rows.value[index];

  if (player && player.id) {
    players.value?.push(player);
    players.value?.sort((a, b) => a.name.localeCompare(b.name));
  }

  rows.value = rows.value.filter((_, i) => i !== index);
};

const reset = async () => {
  rows.value = [];
  players.value = (await pokerService.getAllPlayers()).map((p) => ({
    id: p.id,
    cashIn: p.id,
    name: p.name,
    nickname: p.nickname,
  })).sort((a, b) => a.name.localeCompare(b.name));
};

const selectNewPlayerHandler = (player: PlayerRow, index: number) => {
  const prevValue = rows.value[index];
  rows.value[index] = {...player, rowId: prevValue.rowId};
  if (players.value) {
    players.value = players.value.filter((p) => p.id !== player.id);
  }
};

const updateCashInHandler = (cashIn: PlayerRow, index: number) => {
  const player: PlayerRow = rows.value[index];
  player.cashIn = cashIn;
};

const getNewId = (): string => {
  return crypto.randomUUID();
};

const isEmptySelectedPlayers = computed((): boolean => {
  return rows.length === 0 || (rows.value.filter((r) => r.id !== undefined)).length === 0;
});

</script>
