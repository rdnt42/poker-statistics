<template>
  <v-dialog>
    <v-card>
      <v-card-title>
        End Game for Player
      </v-card-title>
      <v-card-text>
        <p><strong>Name:</strong> {{ player.name }}</p>
        <p><strong>Nickname:</strong> {{ player.nickname }}</p>
        <p><strong>Cash IN:</strong> {{ player.cashIn }}</p>
        <p><strong>Cash OUT:</strong> {{ player.cashOut }}</p>
        <p><strong>Status:</strong> {{ player.status }}</p>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" @click="confirm">OK</v-btn>
        <v-btn @click="close">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  isOpen: Boolean,
  player: {
    type: Object,
    required: true,
    default: () => ({
      name: '',
      nickname: '',
      cashIn: 0,
      cashOut: 0,
      status: '',
    }),
  },
});

const emit = defineEmits(['update:isOpen', 'confirm']);

const close = () => {
  emit('update:isOpen', false);
};

const confirm = () => {
  emit('confirm', props.player);
  close();
};
</script>
