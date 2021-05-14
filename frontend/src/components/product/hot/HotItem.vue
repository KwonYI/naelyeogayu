<template>
  <div id="hot">
    <v-sheet class="mx-auto" elevation="8" max-width="1280" min-height="400">
      <div class="hotTitle">HOT 상품</div>
      <v-slide-group class="hotslide" active-class="success" show-arrows>
        <v-slide-item v-for="n in 6" v-bind:key="n">
          <hotCard></hotCard>
        </v-slide-item>
      </v-slide-group>
    </v-sheet>
  </div>
</template>

<script>
import hotCard from "@/components/product/hot/HotCard.vue";

export default {
  components: {
    hotCard,
  },
};
</script>

<style>
.hotTitle {
  font-size: 40px;
  margin-left: 15px;
}
</style>
