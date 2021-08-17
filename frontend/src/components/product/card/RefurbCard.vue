<template>
  <div id="cardview">
    <div id="card" @click="goDetail(item.product.status)">
      <div id="box" class="" v-if="item.product.status == 0">
        <img id="label" src="@/assets/label/label.png" alt="label" />
        <div id="dday">
          <p id="remain">최대 할인</p>
          <p id="day">{{ maxrate }}%</p>
        </div>
        <v-img
          id="img"
          :src="item.product.imageUrl"
          height="270px"
          max-height="270px"
          :aspect-ratio="1 / 1"
        />
      </div>
      <div id="box" v-if="item.product.status == 1">
        <img id="label" src="@/assets/label/label.png" alt="label" />
        <div id="dday">
          <p id="end">판매 대기</p>
        </div>
        <v-img
          id="img"
          :src="item.product.imageUrl"
          height="270px"
          max-height="270px"
          :aspect-ratio="1 / 1"
        />
      </div>
      <div id="box" v-if="item.product.status == 2">
        <img id="label" src="@/assets/label/endlabel.png" alt="label" />
        <div id="dday">
          <p id="soldout">품절</p>
        </div>
        <v-img
          id="grey"
          :src="item.product.imageUrl"
          height="270px"
          max-height="270px"
          :aspect-ratio="1 / 1"
        />
      </div>
      <div id="box" v-if="item.product.status == 3">
        <img id="label" src="@/assets/label/endlabel.png" alt="label" />
        <div id="dday">
          <p id="end">경매 마감</p>
        </div>
        <v-img
          id="grey"
          :src="item.product.imageUrl"
          height="270px"
          max-height="270px"
          :aspect-ratio="1 / 1"
        />
      </div>
      <p id="title">{{ item.product.name }}</p>
      <div id="info">
        <p id="date">마감일 : {{ item.product.endDate }}</p>
        <p id="stock">수량 : {{ item.product.stock }}개</p>
        <p id="max">{{ item.product.startPrice | comma }}원</p>
        <p id="cur">
          <span id="rate" v-if="item.product.status == 0"
            >{{ item.discountRate | fixed }}%</span
          >
          <span id="notrate" v-if="item.product.status == 1">판매 대기</span>
          <span id="notrate" v-if="item.product.status == 2">품절</span>
          <span id="notrate" v-if="item.product.status == 3">마감</span>
          {{ item.curPrice | comma }}원/개
        </p>
        <p id="detail">상세 보기</p>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import "moment/locale/ko";
export default {
  data: function () {
    return {};
  },
  props: {
    item: Object,
  },
  filters: {
    calculate(date) {
      return moment(date, "YYYY-MM-DD").diff(
        moment().format("YYYY-MM-DD"),
        "days"
      );
    },
    fixed(rate) {
      return rate.toFixed(2);
    },
    comma(v) {
      return String(v).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    goDetail(status) {
      if (status == 2) {
        alert("해당 상품은 품절되었습니다");
        return;
      } else if (status == 3) {
        alert("해당 상품은 마감되었습니다");
        return;
      } else {
        this.$router.go(
          this.$router.push({
            name: "Detail",
            params: { productId: this.item.product.id },
          })
        );
      }
    },
  },
  computed: {
    maxrate() {
      var start = Number(this.item.product.startPrice);
      var end = Number(this.item.product.minPrice);
      return Math.floor(((start - end) / start) * 100);
    },
  },
};
</script>

<style src="@/assets/css/product/card/ListCard.css" scoped></style>
