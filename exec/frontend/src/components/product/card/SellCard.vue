<template>
  <div>
    <div class="sellCardView">
      <div class="sellCard" @click="goDetail(item.product.status)">
        <div class="sellBox" v-if="item.product.status == 0">
          <img class="sellLabel" src="@/assets/label/label.png" alt="label" />
          <div class="sellDday">
            <p class="sellRemain">{{ category }}</p>
            <p class="sellDay">{{ dday }}</p>
          </div>
          <v-img
            class="sellImg"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="sellBox" v-if="item.product.status == 1">
          <img class="sellLabel" src="@/assets/label/label.png" alt="label" />
          <div class="sellDday">
            <p class="sellEnd">판매 대기</p>
          </div>
          <v-img
            class="sellImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="sellBox" v-if="item.product.status == 2">
          <img
            class="sellLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="sellDday">
            <p class="sellSoldout">품절</p>
          </div>
          <v-img
            class="sellImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="sellBox" v-if="item.product.status == 3">
          <img
            class="sellLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="sellDday">
            <p class="sellEnd">경매 마감</p>
          </div>
          <v-img
            class="sellImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <p class="sellTitle">{{ item.product.name }}</p>
        <div class="sellInfo">
          <p class="sellDate">마감일 : {{ item.product.endDate }}</p>
          <p class="sellStock">수량 : {{ item.product.stock }}{{ unit }}</p>
          <p
            class="sellUnit"
            v-if="item.product.category == 2 && item.product.unit"
          >
            무게 : {{ item.product.unit }}kg
          </p>
          <p class="sellCurStock">판매량 : {{ item.total_cnt }}{{ unit }}</p>
          <p class="sellCurPrice">금액 : {{ item.total_price | comma }}원</p>
          <p class="sellDetail">상세 보기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import "moment/locale/ko";
export default {
  props: {
    item: Object,
  },
  computed: {
    category() {
      if (this.item.product.category == 1) {
        return "유통 임박";
      } else if (this.item.product.category == 2) {
        return "판매중";
      }
      return "최대 할인";
    },
    dday() {
      if (this.item.productCurDday > 0) {
        return "D-" + this.item.productCurDday;
      } else if (this.item.productCurDday == 0) {
        return "D-day";
      }
      return "";
    },
    unit() {
      if (this.item.product.category == 2) {
        return "box";
      }
      return "개";
    },
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
      } else if (status == 3) {
        alert("해당 상품은 마감되었습니다");
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
};
</script>

<style src="@/assets/css/product/card/SellCard.css" scoped></style>
