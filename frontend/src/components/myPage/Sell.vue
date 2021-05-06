<template>
  <div class="sellInfo">
    <div class="sellInfoTitle">판매 목록</div>
    <div class="sellGrid" v-if="hasSell">
      <SellCard
        v-for="(item, index) in items"
        :item="item"
        :key="index"
      ></SellCard>
    </div>
    <div class="reserveBody" v-else>
      <div class="reserveText">판매 기록이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import SellCard from "@/components/product/card/SellCard.vue";
export default {
  components: {
    SellCard,
  },
  data() {
    return {
      items: [],
      hasSell: true,
    };
  },
  methods: {
    async getSell() {
      await this.$axios({
        url: "/reserve/list/" + this.$store.getters["userStore/id"],
        method: "GET",
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.items = response.data.data;
          } else {
            this.hasSell = false;
          }
        })
        .catch((error) => {
          console.error(error);
          alert("판매 정보를 받아오는 중 에러가 발생했습니다.");
        });
    },
  },
  created() {
    this.getSell();
  },
};
</script>

<style>
.sellInfoTitle {
  clear: both;
  font-weight: bold;
  font-size: 25px;
  border-bottom: solid 2px green;
}
.sellGrid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.5%;
  width: 100% !important;
  margin-top: 15px;
  align-items: center;
}
.reserveBody {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.reserveText {
  padding-top: 5%;
  padding-bottom: 2%;
  font-size: 24px;
  font-weight: bold;
}
</style>
