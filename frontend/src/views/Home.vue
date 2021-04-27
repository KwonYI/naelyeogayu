<template>
  <div>
    <div id="banner">
      <v-carousel hide-delimiters cycle interval="5000" height="400">
        <v-carousel-item src="@/assets/banner/1.png"></v-carousel-item>
        <v-carousel-item
          src="@/assets/banner/2.png"
          to="/login"
        ></v-carousel-item>
        <v-carousel-item src="@/assets/banner/3.png"></v-carousel-item>
        <v-carousel-item
          src="@/assets/banner/4.png"
          to="/login"
        ></v-carousel-item>
      </v-carousel>
    </div>
  </div>
</template>

<script>
export default {
  methods: {},
};
</script>

<style>
#banner {
  position: relative;
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}
</style>
