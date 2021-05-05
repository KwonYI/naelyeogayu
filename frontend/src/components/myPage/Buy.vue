<template>
  <div class="buyInfo">
    <div class="buyInfoTitle">구매 목록</div>
    <div class="buyGrid" v-if="hasBuy">
      <BuyCard
        v-for="(item, index) in items"
        :item="item"
        :key="index"
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

<style>
.buyInfoTitle {
  clear: both;
  font-weight: bold;
  font-size: 25px;
  border-bottom: solid 2px green;
}
.buyGrid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.5%;
  width: 100% !important;
  margin-top: 15px;
  align-items: center;
}
.buyBody {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.buyText {
  padding-top: 5%;
  padding-bottom: 2%;
  font-size: 24px;
  font-weight: bold;
}
</style>
