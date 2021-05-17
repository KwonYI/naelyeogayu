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
            <p class="buyRemain">판매 대기</p>
            <p class="buyDay">{{ dday }}</p>
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
          <img class="buyLabel" src="@/assets/label/label.png" alt="label" />
          <div class="buyDday">
            <p class="buyRemain">품절</p>
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
          <img class="buyLabel" src="@/assets/label/label.png" alt="label" />
          <div class="buyDday">
            <p class="buyRemain">경매 마감</p>
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
          <p class="buyUnit" v-if="this.item.product.category == 2">
            무게 : {{ item.product.unit }}kg
          </p>
          <p class="buyStock">수량 : {{ item.product.stock }}{{ unit }}</p>
          <p class="buyCount">구매 수량 : {{ item.count }}</p>
          <p class="buyDate">구매일 : {{ item.buyDate | subString }}</p>
          <p class="buyGoal">구매 : {{ item.price }}원</p>
          <p class="buyMax" v-if="item.product.status == 0">
            {{ item.product.startPrice }}원
          </p>
          <p class="buyCur">
            <span class="buyRate" v-if="item.product.status == 0"
              >{{ item.productCurDiscountRate | fixed }}%</span
            >
            {{ item.productCurPrice }}원/{{ unit }}
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
      if (this.item.productCurDday >= 0) {
        return "D-" + this.item.productCurDday;
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

<style scoped>
@font-face {
  font-family: "NEXON Lv1 Gothic OTF Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF Bold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "NEXON Lv1 Gothic OTF";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
.buyTitle,
.buyCur {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.buyStock,
.buyMax,
.buyDate,
.buyRate,
.buyGoal,
.buyCount,
.buyUnit {
  font-family: "NEXON Lv1 Gothic OTF";
}
.buyCardView {
  width: 300px;
  display: inline-block;
}
.buyCard {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  border-radius: 20px;
  cursor: pointer;
}
.buyCard:hover {
  box-shadow: 2px 2px rgb(179, 178, 178);
  transition: 0.4s;
}
.buyBox {
  overflow: hidden;
  border-radius: 20px 20px 0 0;
}
.buyImgGrayScale {
  /* IE */
  filter: progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);
  /* Chrome, Safari */
  -webkit-filter: grayscale(1);
  /* Firefox */
  filter: grayscale(1);
}
.buyImg:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.buyImgGrayScale:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.v-img {
  border-radius: 20px 20px 0 0;
}
.buyInfo {
  height: 150px;
}
.buyInfo p {
  position: absolute;
}
.buyDday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
  width: 66px;
  height: 68px;
  text-align: center;
}
.buyLabel {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
}
.buyRemain {
  font-size: 13px;
  margin: 0 auto;
  color: white;
  width: 66px;
  font-family: "NEXON Lv1 Gothic OTF";
}
.buyDay {
  font-size: 16px;
  margin: 0 auto;
  width: 66px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.buyTitle {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: center;
  font-size: 20px;
  padding-top: 15px;
  width: 100%;
  margin: 0 auto;
}
.buyStock {
  font-size: 15px;
  bottom: 80px;
  right: 20px;
}
.buyCount {
  font-size: 15px;
  bottom: 100px;
  left: 20px;
}
.buyCur {
  font-size: 20px;
  bottom: 30px;
  right: 20px;
  margin-bottom: 10px;
}
.buyGoal {
  font-size: 15px;
  bottom: 80px;
  left: 20px;
}
.buyMax {
  font-size: 17px;
  bottom: 50px;
  color: rgb(154, 150, 154);
  right: 20px;
  text-decoration: line-through;
}
.buyRate {
  font-size: 15px;
  bottom: 85px;
  right: 20px;
}
.buyNotrate {
  color: red;
  font-size: 16px;
}
.buyDate {
  font-size: 15px;
  left: 20px;
  bottom: 60px;
}
.buyUnit {
  font-size: 15px;
  right: 20px;
  bottom: 100px;
}
.buySub {
  font-size: 20px;
  bottom: 20px;
  left: 20px;
}
.buyDetail {
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
  width: 180px;
  border: 1px solid grey;
  bottom: 10px;
  left: 64px;
  text-align: center;
  border-radius: 10px;
  margin: 0 auto;
}
.buySoldout,
.buyEnd {
  font-size: 14px;
  color: white;
  margin-top: 10px;
  width: 66px;
}
</style>
