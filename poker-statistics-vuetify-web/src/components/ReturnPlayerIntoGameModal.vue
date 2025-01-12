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
                color="green"
                class="cursor-pointer"
                variant="tonal"
                v-bind="activatorProps"
              >
                mdi-restart
              </v-icon>
            </span>
          </template>
          <span>Return player into game</span>
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
                :min="player.cashIn"
                v-model="editableValues.cashIn"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                label="Cash OUT"
                disabled
                type="number"
                :model-value="player.cashOut"
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
            text="Return player into game"
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

type PlayerForReturn = {
  id: string,
  name: string,
  nickname: string,
  cashIn: number,
  cashOut: number,
};
const props = defineProps<{
  player: PlayerForReturn,
  gameId: string,
}>();

const editableValues = ref({
  cashIn: props.player.cashIn,
});
const emit = defineEmits(['data-updated']);

const dialog = shallowRef(false)

const closeModal = async () => {
  dialog.value = false;
  await pokerService.returnPlayerIntoGame(props.gameId, props.player.id, editableValues.value.cashIn);
  emit('data-updated');
};

</script>


