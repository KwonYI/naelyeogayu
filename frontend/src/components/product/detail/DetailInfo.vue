<template>
  <div class="productDetailInfo">
    <div class="productDetailInfoImg">
      <v-img :src="item.product.imageUrl"></v-img>
    </div>
    <div class="productDetailInfoBody">
      <div class="productDetailInfoHeader">
        <span>{{ category }}</span>
        <v-divider vertical></v-divider>
        <v-btn text><v-icon left>mdi-home-outline</v-icon></v-btn>
      </div>
      <div class="productDetailTitle">{{ item.product.name }}</div>
      <div class="productDetailRemain">
        남은 수량 {{ item.product.stock }}개
      </div>
      <div class="productPriceDetail">
        <span class="productDetailRate">{{ item.discountRate }}%</span>
        <span class="productPriceDetailInfo">
          <div class="productDetailMax">
            {{ item.product.startPrice | comma }}원
          </div>
          <div class="productDetailCur">{{ item.curPrice | comma }}원</div>
        </span>
      </div>
      <div class="productDetailCountName">수량</div>
      <div class="productDetailCount">
        <v-btn class="countButtonLeft" tile @click="addCount"
          ><v-icon>mdi-plus</v-icon></v-btn
        >
        <span class="countNumber">{{ count }}</span>
        <v-btn class="countButtonRight" tile @click="subCount"
          ><v-icon>mdi-minus</v-icon></v-btn
        >
      </div>
      <div class="productDetailPriceName">입찰금액</div>
      <div class="productDetailPrice">{{ price | comma }}원</div>
      <div class="productDetailButton">
        <div class="productDetailReserveButton">예약하기</div>
        <div class="productDetailBuyButton">입찰하기</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
  data() {
    return {
      count: 0,
    };
  },
  computed: {
    price() {
      return this.count * this.item.curPrice;
    },
    category() {
      if (this.item.product.category == 1) {
        return "유통임박 상품";
      } else if (this.item.product.category == 2) {
        return "못난이 농산물";
      }
      return "리퍼브 상품";
    },
  },
  filters: {
    comma(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    reserve() {},
    buy() {},
    addCount() {
      if (this.count == this.item.product.stock) return;
      this.count += 1;
    },
    subCount() {
      if (this.count == 0) return;
      this.count -= 1;
    },
  },
  created() {
    console.log("detailInfo : ", this.item);
  },
};
</script>

<style>
.productDetailTitle,
.productDetailRate,
.productDetailCur,
.productDetailPrice {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.productDetailInfoHeader,
.productDetailMax,
.productDetailCountName,
.productDetailCount,
.productDetailRemain,
.productDetailPriceName {
  font-family: "NEXON Lv1 Gothic OTF";
}
.productDetailInfo {
  display: flex;
  flex-direction: row;
  margin-bottom: 5%;
}
.productDetailInfoImg {
  float: left;
  width: 60%;
  padding-top: 3%;
}
.productDetailInfoBody {
  margin-left: 5%;
  width: 30%;
}
.productDetailInfoHeader {
  font-size: 13px;
  border-bottom: solid 1px lightgray;
  margin-bottom: 15%;
}
.productDetailTitle {
  font-size: 30px;
  margin-top: 5%;
}
.productDetailRemain {
  font-size: 15px;
  color: gray;
  margin-bottom: 8%;
}
.productPriceDetail {
  margin-bottom: 10%;
}
.productDetailRate {
  font-size: 52px;
  float: left;
  color: red;
  padding-right: 15px;
}
.productDetailMax {
  font-size: 17px;
  color: gray;
  text-decoration: line-through;
}
.productDetailCur {
  font-size: 30px;
  padding-bottom: 10px;
}
.productDetailCountName {
  font-size: 20px;
  border-bottom: solid 1px gray;
}
.productDetailCount {
  margin-top: 3%;
  margin-bottom: 10%;
  align-items: center;
  float: right;
  width: 170px;
}
.countButtonLeft {
  float: left;
}
.countButtonRight {
  float: right;
}
.countNumber {
  font-size: 25px;
  text-align: center;
}
.productDetailPriceName {
  clear: both;
  font-size: 20px;
  border-bottom: solid 1px gray;
}
.productDetailPrice {
  margin-top: 3%;
  margin-bottom: 15%;
  font-size: 30px;
  float: right;
}
.productDetailButton {
  clear: both;
}
.productDetailReserveButton {
  font-size: 20px;
  font-weight: bold;
  float: left;
  text-align: center;
  padding-top: 2%;
  padding-bottom: 2%;
  border: 2px solid black;
  width: 50%;
  cursor: pointer;
}
.productDetailBuyButton {
  font-size: 20px;
  font-weight: bold;
  float: right;
  text-align: center;
  padding-top: 2%;
  padding-bottom: 2%;
  background-color: #abf200;
  border: 2px solid #abf200;
  width: 50%;
  cursor: pointer;
}
</style>
