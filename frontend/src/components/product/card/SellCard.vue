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
            <p class="sellRemain">판매 대기</p>
            <p class="sellDay">{{ dday }}</p>
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
          <p class="sellUnit" v-if="item.product.category == 2">
            무게 : {{ item.product.stock }}kg
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
      if (this.item.dday >= 0) {
        return "D-" + this.item.dday;
      }
      return "";
    },
    unit() {
      if (this.item.product.category == 2) {
        return "Box";
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
.sellTitle,
.sellCur,
.sellRate,
.sellSoldout,
.sellEnd {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.sellStock,
.sellUnit,
.sellMax,
.sellDate,
.sellWatch {
  font-family: "NEXON Lv1 Gothic OTF";
}
.sellCardView {
  width: 300px;
  display: inline-block;
}
.sellCard {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  border-radius: 20px;
  cursor: pointer;
}
.sellCard:hover {
  box-shadow: 2px 2px rgb(179, 178, 178);
  transition: 0.4s;
}
.sellBox {
  overflow: hidden;
  border-radius: 20px 20px 0 0;
}
.sellImg:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.sellImgGrayScale {
  /* IE */
  filter: progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);
  /* Chrome, Safari */
  -webkit-filter: grayscale(1);
  /* Firefox */
  filter: grayscale(1) brightness(65%);
}
.v-img {
  border-radius: 20px 20px 0 0;
}
.sellInfo {
  height: 125px;
}
.sellInfo p {
  position: absolute;
}
.sellDday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
  text-align: center;
}
.sellLabel {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
}
.sellRemain {
  font-size: 13px;
  margin: 0 auto;
  color: white;
  width: 66px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.sellDay {
  font-size: 16px;
  margin: 0 auto;
  width: 66px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.sellTitle {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: center;
  font-size: 20px;
  padding-top: 15px;
  width: 200px;
  margin: 0 auto;
}
.sellStock {
  font-size: 15px;
  bottom: 85px;
  left: 20px;
}
.sellRate {
  color: red;
}
.sellNotrate {
  color: red;
  font-size: 16px;
}
.sellDate {
  width: 180px;
  font-size: 15px;
  left: 20px;
  bottom: 60px;
}
.sellMax {
  font-size: 17px;
  bottom: 0px;
  color: rgb(154, 150, 154);
  bottom: 50px;
  right: 20px;
  text-decoration: line-through;
}
.sellCurStock {
  font-size: 20px;
  bottom: 30px;
  left: 20px;
  margin-bottom: 10px;
}
.sellCurPrice {
  font-size: 20px;
  bottom: 30px;
  right: 20px;
  margin-bottom: 10px;
}
.sellDetail {
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
.sellSoldout,
.sellEnd {
  font-size: 14px;
  color: white;
  margin-top: 10px;
  width: 66px;
}
.sellUnit {
  font-size: 15px;
  bottom: 85px;
  right: 20px;
}
</style>
