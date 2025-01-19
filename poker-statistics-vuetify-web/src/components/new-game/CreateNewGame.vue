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
                      @selectNewPlayer="selectNewPlayerHandler"
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
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, shallowRef} from 'vue'
import pokerService from "@/services/PokerService";

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
  try {
    players.value = (await pokerService.getAllPlayers()).map((p) => ({
      id: p.id,
      cashIn: p.id,
      name: p.name,
      nickname: p.nickname,
    }));
  } catch (error) {
    console.error("Error when fetch players:", error);
  }
});
const createNewGame = async () => {
  let players = rows.value.filter((r) => r.id !== undefined);
  console.log(players);
  clear();
  dialog.value = false;
};
const addRow = (): void => {
  rows.value.push({
    rowId: getNewId(),
  });
  console.log(`add row, length ${rows.value.length}`);
};
const removeRow = (index: number): void => {
  const player = rows.value[index];

  if (player && player.id) {
    players.value?.push(player);
    players.value?.sort((a, b) => a.name.localeCompare(b.name));
  }

  rows.value = rows.value.filter((_, i) => i !== index);
};

const clear = () => {
  rows.value = [];
};

const selectNewPlayerHandler = (player: PlayerRow, index: number) => {
  const prevValue = rows.value[index];
  rows.value[index] = {...player, rowId: prevValue.rowId};
  if (players.value) {
    players.value = players.value.filter((p) => p.id !== player.id);
  }
};

const getNewId = (): string => {
  return crypto.randomUUID();
}

</script>
