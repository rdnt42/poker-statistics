// Utilities
import {defineStore} from 'pinia'

export const useActiveGameStore =
  defineStore('activeGameStore', () => {
    type DataState = {
      data: 'actual' | 'not-presented' | 'non-actual';
    };

    const data = ref<DataState>('non-actual');

    const notifyDataUpdated = () => {
      data.value = 'non-actual';
    };

    const setActualData = () => {
      data.value = 'actual';
    };

    const clearData = () => {
      data.value = 'not-presented';
    };

    const isDataPresented = () => {
      return data.value !== 'not-presented';
    };

    const needToUpdate = () => {
      return data.value === 'non-actual';
    };

    return {notifyDataUpdated, setActualData, clearData, isDataPresented, needToUpdate};
  });
