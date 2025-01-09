<!--<template>-->
<!--  <v-dialog>-->
<!--    <v-card>-->
<!--      <v-card-title>-->
<!--        End Game for Player-->
<!--      </v-card-title>-->
<!--      <v-card-text>-->
<!--        <p><strong>Name:</strong> {{ player.name }}</p>-->
<!--        <p><strong>Nickname:</strong> {{ player.nickname }}</p>-->
<!--        <p><strong>Cash IN:</strong> {{ player.cashIn }}</p>-->
<!--        <p><strong>Cash OUT:</strong> {{ player.cashOut }}</p>-->
<!--        <p><strong>Status:</strong> {{ player.status }}</p>-->
<!--      </v-card-text>-->
<!--      <v-card-actions>-->
<!--        <v-btn color="primary" @click="confirm">OK</v-btn>-->
<!--        <v-btn @click="close">Cancel</v-btn>-->
<!--      </v-card-actions>-->
<!--    </v-card>-->
<!--  </v-dialog>-->
<!--</template>-->

<!--<script lang="ts" setup>-->
<!--import { defineProps, defineEmits } from 'vue';-->

<!--const props = defineProps({-->
<!--  isOpen: Boolean,-->
<!--  player: {-->
<!--    type: Object,-->
<!--    required: true,-->
<!--    default: () => ({-->
<!--      name: '',-->
<!--      nickname: '',-->
<!--      cashIn: 0,-->
<!--      cashOut: 0,-->
<!--      status: '',-->
<!--    }),-->
<!--  },-->
<!--});-->

<!--const emit = defineEmits(['update:isOpen', 'confirm']);-->

<!--const close = () => {-->
<!--  emit('update:isOpen', false);-->
<!--};-->

<!--const confirm = () => {-->
<!--  emit('confirm', props.player);-->
<!--  close();-->
<!--};-->
<!--</script>-->


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
                required
              > {{props.player?.name}}</v-text-field>
            </v-col>

            <v-col
              cols="12"
              md="4"
              sm="6"
            >
              <v-text-field
                hint="example of helper text only on focus"
                label="Middle name"
              ></v-text-field>
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
            @click="dialog = false"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts" setup>
import {shallowRef, watch} from 'vue'
import type {PropType} from "vue";

type PlayerForFinishingGame = {
  id: string,
  name: string,
  nickname: string,
  cashIn: number,
  cashOut: number | undefined,
};
const props = defineProps({
  player: Object as PropType<PlayerForFinishingGame>,
});

const dialog = shallowRef(false)

watch(dialog, () => {
  console.log(props.player);
});
</script>


