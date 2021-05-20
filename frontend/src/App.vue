<template>
  <v-app>
    <Navbar />
    <v-main>
      <!-- If using vue-router -->
      <router-view :key="$route.fullPath"></router-view>
    </v-main>
    <!-- <Footer /> -->
  </v-app>
</template>

<script>
import Navbar from "@/components/main/Navbar.vue";
// import Footer from "@/components/main/Footer.vue";

export default {
  name: "App",

  components: { Navbar },

  data: () => ({
    //
  }),
};
</script>
<style>
@font-face {
  font-family: "SDSamliphopangche_Outline";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Outline.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "NEXON Lv1 Gothic OTF";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "SDSamliphopangche_Basic";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Basic.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>
