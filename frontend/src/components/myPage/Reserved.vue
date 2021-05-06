<template>
  <div class="reservedInfo">
    <div class="reservedInfoTitle">예약한 목록</div>
    <div class="reserveGrid" v-if="hasReserved">
      <reserveCard
        v-for="(item, index) in items"
        :item="item"
        :key="index"
      ></reserveCard>
    </div>
    <div class="reserveBody" v-else>
      <div class="reserveText">예약한 상품이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import reserveCard from "@/components/product/card/ReserveCard.vue";
export default {
  components: {
    reserveCard,
  },
  data() {
    return {
      items: [],
      hasReserved: true,
      isMore: false,
    };
  },
  methods: {
    async getReserve() {
      await this.$axios({
        url: "/reserve/list/" + this.$store.getters["userStore/id"],
        method: "GET",
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.items = response.data.data;
          } else {
            this.hasReserved = false;
          }
        })
        .catch((error) => {
          console.error(error);
          alert("예약 정보를 받아오는 중 에러가 발생했습니다.");
        });
    },
  },
  created() {
    this.getReserve();
  },
};
</script>

<style>
.reservedInfoTitle {
  clear: both;
  font-weight: bold;
  font-size: 25px;
  border-bottom: solid 2px green;
}
.reserveGrid {
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
