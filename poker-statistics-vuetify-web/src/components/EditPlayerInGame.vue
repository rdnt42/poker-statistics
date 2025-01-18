<template>
  <div>
    <v-dialog
      v-model="dialog"
      max-width="600"
    >
      <template v-slot:activator="{ props: activatorProps }">
        <v-tooltip bottom>
          <template v-slot:activator="{ props: tooltipActivatorProps }">
            <span v-bind="tooltipActivatorProps">
              <v-icon
                color="blue"
                class="cursor-pointer"
                variant="tonal"
                v-bind="activatorProps"
              >
                mdi-pencil
              </v-icon>
            </span>
          </template>
          <span>Edit player stats</span>
        </v-tooltip>
      </template>

      <v-card
        prepend-icon="mdi-account"
        title="End game for player"
      >
        <v-card-text>
          <v-row dense>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                label="Name"
                disabled
                :model-value="player.name"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                label="Nickname"
                disabled
                :model-value="player.nickname"
              />
            </v-col>
          </v-row>
          <v-row dense>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                label="Cash IN"
                :rules="[requiredRule]"
                type="number"
                :min="0"
                v-model="editableValues.cashIn"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                label="Cash OUT"
                :rules="[requiredRule]"
                type="number"
                :min="0"
                v-model="editableValues.cashOut"
              />
            </v-col>
          </v-row>
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
            text="Save changes"
            variant="tonal"
            @click="closeModal"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import {shallowRef} from 'vue'
import {requiredRule} from "@/components/utils";
import pokerService from "@/services/PokerService";
import {useActiveGameStore} from "@/stores/app";

type PlayerInGame = {
  id: string,
  name: string,
  nickname: string,
  cashIn: number,
  cashOut: number | null,
};
const props = defineProps<{
  player: PlayerInGame,
  gameId: string,
}>();

const editableValues = ref({
  cashIn: props.player.cashIn,
  cashOut: props.player.cashOut,
});
const activeGameStore = useActiveGameStore();

const dialog = shallowRef(false)

const closeModal = async () => {
  dialog.value = false;
  await pokerService.updateActivePlayer(props.gameId, props.player.id, {
    cashIn: editableValues.value.cashIn,
    cashOut: editableValues.value.cashOut ?? undefined,
  });
  activeGameStore.notifyDataUpdated();
};

</script>
