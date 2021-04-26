<template>
  <v-app>
    <Navbar />
    <v-main>
      <!-- If using vue-router -->
      <router-view></router-view>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import Navbar from "@/components/main/Navbar.vue";
import Footer from "@/components/main/Footer.vue";

export default {
  name: "App",

  components: { Navbar, Footer },

  data: () => ({
    //
  }),
};
</script>
