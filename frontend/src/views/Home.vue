<template>
  <div id="desc">
    <v-carousel hide-delimiters cycle interval="5000" height="400">
      <v-carousel-item src="@/assets/banner/1.png"></v-carousel-item>
      <v-carousel-item src="@/assets/banner/2.png"></v-carousel-item>
      <v-carousel-item src="@/assets/banner/4.png"></v-carousel-item>
      <v-carousel-item src="@/assets/banner/5.png"></v-carousel-item>
    </v-carousel>
    <div id="desc1"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style>
#desc {
  position: relative;
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}
</style>
