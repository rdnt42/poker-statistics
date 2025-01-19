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
                color="red"
                class="cursor-pointer"
                variant="tonal"
                v-bind="activatorProps"
              >
                mdi-close-circle
              </v-icon>
            </span>
          </template>
          <span>End game for player</span>
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
                variant="outlined"
                label="Name"
                disabled
                :model-value="player.name"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                variant="outlined"
                label="Nickname"
                disabled
                :model-value="player.nickname"
              />
            </v-col>
          </v-row>
          <v-row dense>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                variant="outlined"
                label="Cash IN"
                disabled
                type="number"
                :model-value="player.cashIn"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                variant="outlined"
                label="Cash OUT"
                :rules="[requiredRule]"
                type="number"
                step="10"
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
            text="End game for player"
            variant="tonal"
            @click="saveChanges"
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

type PlayerForComplete = {
  id: string,
  name: string,
  nickname: string,
  cashIn: number,
  cashOut: number | null,
};
const props = defineProps<{
  player: PlayerForComplete,
  gameId: string,
}>();

const activeGameStore = useActiveGameStore();

const editableValues = ref({
  cashOut: props.player.cashOut || 0,
});

const dialog = shallowRef(false)

const saveChanges = async () => {
  dialog.value = false;
  // TODO validate required
  await pokerService.finishGameForPlayer(props.gameId, props.player.id, editableValues.value.cashOut);
  activeGameStore.notifyDataUpdated();
};

</script>


