<template>
  <div id="refurbished">
    <hot></hot>
    <div class="iconButton">
      <v-icon
        :dark="!isCard"
        :class="{ clicked: !isCard }"
        @click="isCard = false"
        >mdi-menu</v-icon
      >
      <v-icon :dark="isCard" :class="{ clicked: isCard }" @click="isCard = true"
        >mdi-view-grid-outline</v-icon
      >
    </div>
    <list v-show="!isCard"></list>
    <grid v-show="isCard"></grid>
  </div>
</template>

<script>
import hot from "@/components/product/hot/HotItem.vue";
import list from "@/components/product/list/List.vue";
import grid from "@/components/product/list/Grid.vue";

export default {
  components: {
    hot,
    list,
    grid,
  },
  data() {
    return {
      choose: 1,
      isCard: false,
    };
  },
};
</script>

<style>
#refurbished {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.clicked {
  color: white;
  background-color: gray;
}
.iconButton {
  align-items: right;
  margin-left: auto;
  margin-right: 16.1%;
  margin: 2% 16.1% 1% auto;
}
</style>
