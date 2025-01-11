<template>
  <div>
    <v-dialog
      v-model="dialog"
      max-width="600"
    >
      <template v-slot:activator="{ props: activatorProps }">
        <v-icon
          color="red"
          class="cursor-pointer"
          variant="tonal"
          v-bind="activatorProps"
        >
          mdi-close-circle
        </v-icon>
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
                disabled
                type="number"
                :min="0"
                :model-value="player.cashIn"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                label="Cash OUT"
                required
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
            text="End game for player"
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

type PlayerForFinishingGame = {
  id: string,
  name: string,
  nickname: string,
  cashIn: number,
  cashOut: number | undefined,
};
const props = defineProps<{
  player: PlayerForFinishingGame,
  gameId: string,
}>();

const editableValues = ref({
  cashOut: props.player.cashOut || 0,
});
const emit = defineEmits(['data-updated']);

const dialog = shallowRef(false)

const closeModal = async () => {
  dialog.value = false;
  await pokerService.finishGameForPlayer(props.gameId, props.player.id, editableValues.value.cashOut);
  emit('data-updated');
};

</script>


