<template>
  <div id="grid">
    <card v-for="n in 20" :key="n"></card>
  </div>
</template>

<script>
import card from "@/components/product/list/GridCard.vue";

export default {
  components: {
    card,
  },
};
</script>

<style>
#grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 2%;
  width: 100% !important;
  align-items: center;
}
</style>
