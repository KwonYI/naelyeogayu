<template>
  <figure id="hotcard" @click="goDetail()">
    <v-img
      id="img"
      :src="item.product.imageUrl"
      height="220px"
      max-height="220px"
      :aspect-ratio="1 / 1"
    ></v-img>
    <img id="label" src="@/assets/label/rate.png" alt="label" />
    <p id="rate">{{ index + 1 }}위</p>
    <figcaption>
      <p id="name">{{ item.product.name }}</p>
      <p id="descript">{{ item.product.descript }}</p>
      <p v-if="item.product.category == 3" id="stock">
        수량 : {{ item.product.stock }} 개
      </p>
      <p v-else id="stock">수량 : {{ item.product.stock }} 박스</p>
      <p id="price">현재가 : {{ item.curPrice | comma }} 원</p>
      <p id="endDate">마감일 : {{ item.product.endDate }}</p>
      <img id="arrow" src="@/assets/label/arrow.png" alt="arrow" />
    </figcaption>
  </figure>
</template>

<script>
//카드 넓이 256, margin 8, 실제 넓이 240
export default {
  props: {
    item: Object,
    index: Number,
  },
  filters: {
    comma(v) {
      return String(v).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    fixed(v) {
      return Math.floor(v);
    },
  },
  methods: {
    goDetail() {
      this.$router.go(
        this.$router.push({
          name: "Detail",
          params: { productId: this.item.product.id },
        })
      );
    },
  },
};
</script>

<style src="@/assets/css/SortCard.css" scoped></style>
