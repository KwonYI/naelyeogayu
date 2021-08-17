<template>
  <div class="productDetail">
    <div class="productDetailHeader">
      <a @click="goMain" style="color: black; text-decoration: none">홈</a>
      <v-icon>mdi-chevron-right</v-icon>
      <a @click="goCategory" style="color: black; text-decoration: none">{{
        category
      }}</a>
    </div>
    <div class="productDetailTop" v-if="this.getDetailInfo">
      <DetailInfo :item="this.getDetailInfo" />
    </div>
    <div class="productDetailBottom" v-if="this.getDetailInfo">
      <DetailTap :item="this.getDetailInfo" />
    </div>
  </div>
</template>

<script>
import DetailInfo from "@/components/product/detail/DetailInfo.vue";
import DetailTap from "@/components/product/detail/DetailTap.vue";
import { mapGetters } from "vuex";

export default {
  components: {
    DetailInfo,
    DetailTap,
  },
  data() {
    return {
      item: [],
    };
  },
  created() {
    this.getDetail();
  },
  computed: {
    ...mapGetters({
      getDetailInfo: "productStore/getDetailInfo",
    }),
    category() {
      if (this.getDetailInfo) {
        if (this.getDetailInfo.product.category == 1) {
          return "유통임박 상품";
        } else if (this.getDetailInfo.product.category == 2) {
          return "못난이 농산물";
        }
        return "리퍼브 상품";
      }
      return "";
    },
  },
  methods: {
    goMain() {
      this.$router.push({ name: "Home" });
    },
    goCategory() {
      if (this.getDetailInfo.product.category == 1) {
        this.$router.push({ name: "Expire" });
        return;
      } else if (this.getDetailInfo.product.category == 2) {
        this.$router.push({ name: "Uglyfood" });
        return;
      }
      this.$router.push({ name: "Refurb" });
    },
    getDetail() {
      this.$store.dispatch(
        "productStore/productDetail",
        this.$route.params.productId
      );
    },
  },
};
</script>

<style src="@/assets/css/product/Detail.css" scoped></style>
