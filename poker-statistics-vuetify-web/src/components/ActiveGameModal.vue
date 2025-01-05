<template>
  <v-dialog v-model="localIsOpen" max-width="500px">
    <v-card>
      <v-card-title>Active game details</v-card-title>
      <v-card-text>
        <p v-if="loading">Loading...</p>
        <p v-else-if="error">Error: {{ error }}</p>
        <div v-else>
          <p><strong>Data:</strong> {{ additionalData }}</p>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" text="cls" @click="closeModal">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import {ref, watch} from 'vue';
import pokerClient from "@/services/PokerClient.ts";

const props = defineProps({
  isOpen: Boolean,
  selectedRow: Object,
});

const emit = defineEmits(['update:isOpen']);

const loading = ref(false);
const error = ref(null);
const additionalData = ref(null);

const localIsOpen = ref(props.isOpen);

watch(() => props.isOpen, (newVal) => {
  localIsOpen.value = newVal;
});

watch(localIsOpen, (newVal) => {
  emit('update:isOpen', newVal);
  if (newVal && props.selectedRow) {
    fetchAdditionalData(props.selectedRow.id);
  }
});

const fetchAdditionalData = async (id) => {
  loading.value = true;
  error.value = null;
  additionalData.value = null;
  try {
    const response = await pokerClient.get(`/api/v1/active-games/${id}/full-info`);
    additionalData.value = response.data;
  } catch (err) {
    error.value = err.message;
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  localIsOpen.value = false;
};
</script>
