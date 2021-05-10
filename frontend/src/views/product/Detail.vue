<template>
  <div class="productDetail">
    <div class="productDetailHeader">
      <a @click="goMain" style="color: black; text-decoration: none">홈</a>
      <v-icon>mdi-chevron-right</v-icon>
      <a @click="goPage" style="color: black; text-decoration: none">{{
        category
      }}</a>
    </div>
    <div class="productDetailTop" v-if="this.getDetailInfo">
      <DetailInfo :item="this.getDetailInfo" />
    </div>
    <div class="productDetailBottom" v-if="this.getDetailInfo">
      <DetailTap :item="this.getDetailInfo" />
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
      category: "",
    };
  },
  created() {
    this.getDetail();
  },
  computed: {
    ...mapGetters({
      getDetailInfo: "productStore/getDetailInfo",
      //   getUserId: "userStore/getUserId"
    }),
  },
  methods: {
    goMain() {
      this.$router.push({ name: "Home" });
    },
    goPage() {
      if (this.category == "유통임박 상품") {
        this.$router.push({ name: "Expire" });
      } else if (this.category == "못난이 농산물") {
        this.$router.push({ name: "Uglyfood" });
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

<style>
.productDetail {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  position: relative;
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}
.productDetailHeader {
  float: left;
  width: 100%;
}
.productDetailTop {
  width: 100%;
}
.productDetailBottom {
  width: 100%;
}
</style>
