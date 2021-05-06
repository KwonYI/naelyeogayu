<template>
  <div v-if="this.getDetailInfo">
    {{ this.getDetailInfo.product.name }}
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  data: function () {
    return {};
  },
  created() {
    this.getDetail();
  },
  methods: {
    getDetail() {
      this.$store.dispatch(
        "productStore/productDetail",
        this.$route.params.productId
      );
    },
  },
  computed: {
    ...mapGetters({
      getDetailInfo: "productStore/getDetailInfo",
      //   getUserId: "userStore/getUserId"
    }),
  },
};
</script>

<style></style>
