<template>
  <div class="productDetail">
    <div class="productDetailHeader">
      <a @click="goMain" style="color: black; text-decoration: none">홈</a>
      <v-icon>mdi-chevron-right</v-icon>
      <a @click="goPage" style="color: black; text-decoration: none">{{
        category
      }}</a>
    </div>
    <div class="productDetailTop" v-if="item.length !== 0">
      <DetailInfo :item="item" />
    </div>
    <div class="productDetailBottom" v-if="item.length !== 0">
      <DetailTap :item="item" />
    </div>
  </div>
</template>

<script>
import DetailInfo from "@/components/product/detail/DetailInfo.vue";
import DetailTap from "@/components/product/detail/DetailTap.vue";

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
    this.getInfo();
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
    async getInfo() {
      await this.$axios({
        url: "/product/detail/" + this.$route.params.item.product.id,
        method: "GET",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            console.log(response.data.data);
            this.item = response.data.data;
            if (this.item.product.category == 1) {
              this.category = "유통임박 상품";
            } else if (this.item.product.category == 2) {
              this.category = "못난이 농산물";
            } else {
              this.category = "리퍼브 상품";
            }
          } else {
            alert("존재하지 않는 상품입니다.");
            this.$router.push({ name: "HOME" });
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
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
