<template>
  <div class="buyInfoForm">
    <div class="buyInfoTitle">구매 목록</div>
    <div class="buyGrid" v-if="hasBuy">
      <BuyCard
        v-for="(item, index) in items"
        v-bind:item="item"
        v-bind:key="index"
      ></BuyCard>
    </div>
    <div class="buyBody" v-else>
      <div class="buyText">구매한 상품이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import BuyCard from "@/components/product/card/BuyCard.vue";
export default {
  components: {
    BuyCard,
  },
  data() {
    return {
      items: [],
      hasBuy: true,
    };
  },
  methods: {
    async getBuy() {
      await this.$axios({
        url: "/buy/list/" + this.$store.getters["userStore/id"],
        method: "GET",
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.items = response.data.data;
          } else {
            this.hasBuy = false;
          }
        })
        .catch((error) => {
          console.error(error);
          alert("예약 정보를 받아오는 중 에러가 발생했습니다.");
        });
    },
  },
  created() {
    this.getBuy();
  },
};
</script>

<style src="@/assets/css/user/myPage/Buy.css" scoped></style>
