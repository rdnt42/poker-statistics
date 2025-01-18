// Utilities
import {defineStore} from 'pinia'

export const useActiveGameStore =
  defineStore('activeGameStore', () => {
    const isDataUpdated = ref(false);

    const notifyDataUpdated = () => {
      isDataUpdated.value = true;
    };

    const resetDataUpdated = () => {
      isDataUpdated.value = false;
    };

    return {isDataUpdated, notifyDataUpdated, resetDataUpdated};
  });
