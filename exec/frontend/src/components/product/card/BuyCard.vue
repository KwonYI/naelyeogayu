<template>
  <div>
    <div class="buyCardView">
      <div class="buyCard" @click="goDetail(item.product.status)">
        <div class="buyBox" v-if="item.product.status == 0">
          <img class="buyLabel" src="@/assets/label/label.png" alt="label" />
          <div class="buyDday">
            <p class="buyRemain">{{ category }}</p>
            <p class="buyDay">{{ dday }}</p>
          </div>
          <v-img
            class="buyImg"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="buyBox" v-if="item.product.status == 1">
          <img class="buyLabel" src="@/assets/label/label.png" alt="label" />
          <div class="buyDday">
            <p class="buyEnd">판매 대기</p>
          </div>
          <v-img
            class="buyImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="buyBox" v-if="item.product.status == 2">
          <img class="buyLabel" src="@/assets/label/endlabel.png" alt="label" />
          <div class="buyDday">
            <p class="buySoldout">품절</p>
          </div>
          <v-img
            class="buyImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="buyBox" v-if="item.product.status == 3">
          <img class="buyLabel" src="@/assets/label/endlabel.png" alt="label" />
          <div class="buyDday">
            <p class="buyEnd">경매 마감</p>
          </div>
          <v-img
            class="buyImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <p class="buyTitle">{{ item.product.name }}</p>
        <div class="buyInfo">
          <p
            class="buyUnit"
            v-if="
              this.item.product.category == 2 && this.item.product.unit != 0
            "
          >
            무게 : {{ item.product.unit }}kg
          </p>
          <p class="buyStock">수량 : {{ item.product.stock }}{{ unit }}</p>
          <p class="buyCount">구매 수량 : {{ item.count }}{{ unit }}</p>
          <p class="buyDate">구매일 : {{ item.buyDate | subString }}</p>
          <p class="buyGoal">구매 : {{ item.price | comma }}원</p>
          <p class="buyMax" v-if="item.product.status == 0">
            {{ item.product.startPrice | comma }}원
          </p>
          <p class="buyCur">
            <span class="buyRate" v-if="item.product.status == 0"
              >{{ item.productCurDiscountRate | fixed }}%</span
            >
            {{ item.productCurPrice | comma }}원/{{ unit }}
          </p>
          <p class="buyDetail">상세 보기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
  computed: {
    category: function () {
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
    fixed(rate) {
      return rate.toFixed(2);
    },
    subString(string) {
      return string.substring(0, string.indexOf("T"));
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
};
</script>

<style src="@/assets/css/product/card/BuyCard.css" scoped></style>
