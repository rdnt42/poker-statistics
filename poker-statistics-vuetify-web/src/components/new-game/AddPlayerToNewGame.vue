<template>
  <v-form>
    <div class="d-flex align-center">
      <v-select
        v-model="selectedPlayer"
        :item-props="playerTitleProps"
        :items="players"
        label="Player"
        placeholder="Select player"
      ></v-select>
      <v-text-field
        v-model="playerValues.cashIn"
        label="Cash IN"
        type="number"
        :min="0"
        :default="defaultCashIn"
        required
        :rules="[requiredRule]"
      ></v-text-field>
      <v-text-field
        v-model="playerValues.name"
        label="Player name"
        type="string"
        disabled
      ></v-text-field>
      <v-text-field
        v-model="playerValues.nickname"
        label="Player nickname"
        type="string"
        disabled
      ></v-text-field>
    </div>
  </v-form>
</template>

<script lang="ts" setup>
import {ref} from 'vue';
import {requiredRule} from "@/components/utils";
import type {PlayerResponse} from "@/types/PlayerResponse";

type ExistedPlayer = {
  id: string,
  name: string,
  nickname: string,
};
type AddPlayerValues = {
  id: string,
  name: string,
  nickname: string ,
  cashIn: number,
};

const defaultCashIn = 10;

const props = defineProps<{
  players: PlayerResponse[],
  index: number,
}>();

const emit = defineEmits(['selectNewPlayer']);

const playerValues = ref<AddPlayerValues>({
  id: null,
  name: null,
  nickname: null,
  cashIn: defaultCashIn,
});
const selectedPlayer = ref<ExistedPlayer | null>(null);

const playerTitleProps = (item: ExistedPlayer) => {
  return {
    title: item.name,
    subtitle: item.nickname,
  }
};


watch(selectedPlayer, (newValue: ExistedPlayer | null) => {
  if(newValue === null) return;

  playerValues.value.id = newValue.id;
  playerValues.value.name = newValue.name;
  playerValues.value.nickname = newValue.nickname;
  playerValues.value.cashIn = defaultCashIn;

  emit('selectNewPlayer', playerValues.value, props.index);
});
</script>
