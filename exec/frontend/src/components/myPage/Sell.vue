<template>
  <div class="sellInfoForm">
    <div class="sellInfoTitle">판매 목록</div>
    <div class="sellGrid" v-if="hasSell">
      <SellCard
        v-for="(item, index) in items"
        v-bind:item="item"
        v-bind:key="index"
      ></SellCard>
    </div>
    <div class="sellBody" v-else>
      <div class="sellText">판매 기록이 없습니다.</div>
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
        url: "/product/sell/" + this.$store.getters["userStore/id"],
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

<style src="@/assets/css/user/myPage/Sell.css" scoped></style>
