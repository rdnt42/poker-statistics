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

<script lang="ts" setup>
import {type PropType, ref, watch} from 'vue';
import PokerService from "@/services/PokerService";
import type {ActiveGameResponse} from "@/types/ActiveGameResponse";

const props = defineProps({
  isOpen: Boolean,
  selectedRow: Object as PropType<ActiveGameResponse | null>,
});

const emit = defineEmits(['update:isOpen']);

const loading = ref(false);
const error = ref(null);
const additionalData = ref<ActiveGameResponse | null>(null);

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

const fetchAdditionalData = async (id: string) => {
  loading.value = true;
  error.value = null;
  additionalData.value = null;
  try {
    additionalData.value = await PokerService.getActiveGameFullInfo(id);
  } catch (err: any) {
    error.value = err.message;
  } finally {
    loading.value = false;
  }
};

const closeModal = () => {
  localIsOpen.value = false;
};
</script>
