<template>
  <div>
    <div id="banner">
      <v-carousel hide-delimiters cycle interval="5000" height="300">
        <v-carousel-item src="@/assets/banner/1.png"></v-carousel-item>
        <v-carousel-item src="@/assets/banner/2.png"></v-carousel-item>
        <v-carousel-item
          src="@/assets/banner/3.png"
          to="/uglyfood"
        ></v-carousel-item>
        <v-carousel-item
          src="@/assets/banner/4.png"
          to="/refurb"
        ></v-carousel-item>
      </v-carousel>
    </div>
    <div id="hotlist">
      <p id="title">
        현재 할인 상품<img
          id="rating"
          src="@/assets/main/rating.png"
          alt="rating"
          height="24px"
        />
      </p>
      <rategrid :category="1" />
      <rategrid :category="2" />
      <rategrid :category="3" />
    </div>
  </div>
</template>

<script>
import rategrid from "@/components/product/sort/RateGrid";
export default {
  components: {
    rategrid,
  },
};
</script>

<style scoped>
#banner {
  position: relative;
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}

#hotlist {
  position: relative;
  width: 1280px;
  margin: 40px auto 0px auto;
}

#title {
  text-align: center;
  width: 220px;
  margin: 0 auto;
  font-size: 25px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
  vertical-align: middle;
}
#rating {
  margin-left: 10px;
  vertical-align: middle;
}
</style>
