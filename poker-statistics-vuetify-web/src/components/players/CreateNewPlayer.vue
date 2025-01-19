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
          text="Create new player"
          variant="outlined"
        ></v-btn>
      </template>

      <v-card
        prepend-icon="mdi-account"
        title="Create new player"
      >
        <v-card-text>
          <v-row dense>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                variant="outlined"
                label="Name"
                required
                :rules="[requiredRule]"
                v-model="editableValues.name"
              />
            </v-col>
            <v-col cols="12" md="4" sm="6">
              <v-text-field
                variant="outlined"
                label="Nickname"
                required
                :rules="[requiredRule]"
                v-model="editableValues.nickname"
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
            text="Create new player"
            variant="tonal"
            @click="createPlayerHandler"
          ></v-btn>
        </v-card-actions>
      </v-card>

    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import {shallowRef} from 'vue'
import pokerService from "@/services/PokerService";
import {usePlayersStore} from "@/stores/app";
import {requiredRule} from "@/components/utils";

const dialog = shallowRef(false);
const playersStore = usePlayersStore();

const editableValues = ref({
  name: null,
  nickname: null,
});

const createPlayerHandler = async () => {
  await createPlayer();
}
const createPlayer = async () => {
  await pokerService.createNewPlayer(editableValues.value.name, editableValues.value.nickname);
  playersStore.setNeedToUpdate();
  dialog.value = false;
};

</script>
