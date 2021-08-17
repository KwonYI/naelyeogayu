<template>
  <div class="reserveInfoForm">
    <div class="reserveInfoTitle">예약한 목록</div>
    <div class="reserveGrid" v-if="hasReserved">
      <ReserveCard
        v-for="(item, index) in items"
        v-bind:item="item"
        v-bind:key="index"
      ></ReserveCard>
    </div>
    <div class="reserveBody" v-else>
      <div class="reserveText">예약한 상품이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import ReserveCard from "@/components/product/card/ReserveCard.vue";
export default {
  components: {
    ReserveCard,
  },
  data() {
    return {
      items: [],
      hasReserved: true,
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

<style src="@/assets/css/user/myPage/Reserved.css" scoped></style>
