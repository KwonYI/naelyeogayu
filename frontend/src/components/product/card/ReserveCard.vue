<template>
  <div>
    <div class="reserveCardView">
      <div class="reserveCard" @click="goDetail(item.reserve.product.status)">
        <div class="reserveBox" v-if="item.reserve.product.status == 0">
          <img
            class="reserveLabel"
            src="@/assets/label/label.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveRemain">{{ category }}</p>
            <p class="reserveDay">{{ dday }}</p>
          </div>
          <v-img
            class="reserveImg"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveBox" v-if="item.reserve.product.status == 1">
          <img
            class="reserveLabel"
            src="@/assets/label/label.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveRemain">판매 대기</p>
            <p class="reserveDay">{{ dday }}</p>
          </div>
          <v-img
            class="reserveImgGrayScale"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveBox" v-if="item.reserve.product.status == 2">
          <img
            class="reserveLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveSoldout">품절</p>
          </div>
          <v-img
            class="reserveImgGrayScale"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveBox" v-if="item.reserve.product.status == 3">
          <img
            class="reserveLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveEnd">경매 마감</p>
          </div>
          <v-img
            class="reserveImgGrayScale"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <p class="reserveTitle">{{ item.reserve.product.name }}</p>
        <div class="reserveInfo">
          <p class="reserveRemainStock">
            수량 : {{ item.reserve.product.stock }}개
          </p>
          <p class="reserveStock">예약 수량 : {{ item.reserve.count }}개</p>
          <p class="reserveGoal">예약 : {{ item.reserve.price }}원</p>
          <p class="reserveMax">{{ item.reserve.product.startPrice }}원</p>
          <p class="reserveCur">
            <span class="reserveRate" v-if="item.productCurDiscountRate != ''"
              >{{ item.productCurDiscountRate | fixed }}%</span
            >
            {{ item.productCurPrice }}원/개
          </p>
          <p class="reserveDetail">상세 보기</p>
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
      if (this.item.reserve.product.category == 1) {
        return "유통 임박";
      } else if (this.item.reserve.product.category == 2) {
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
            params: { productId: this.item.reserve.product.id },
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
.reserveTitle,
.reserveCur,
.reserveRate,
.reserveSoldout,
.reserveEnd {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.reserveRemainStock,
.reserveStock,
.reserveGoal,
.reserveMax,
.reserveDate {
  font-family: "NEXON Lv1 Gothic OTF";
}
.reserveCardView {
  width: 300px;
  display: inline-block;
}
.reserveCard {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  border-radius: 20px;
  cursor: pointer;
}
.reserveCard:hover {
  box-shadow: 2px 2px rgb(179, 178, 178);
  transition: 0.4s;
}
.reserveBox {
  overflow: hidden;
  border-radius: 20px 20px 0 0;
}
.reserveImgGrayScale {
  /* IE */
  filter: progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);
  /* Chrome, Safari */
  -webkit-filter: grayscale(1);
  /* Firefox */
  filter: grayscale(1) brightness(65%);
}
.reserveImg:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.reserveImgGrayScale:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.v-img {
  border-radius: 20px 20px 0 0;
}
.reserveInfo {
  height: 125px;
}
.reserveInfo p {
  position: absolute;
}
.reserveDday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
  text-align: center;
}
.reserveLabel {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
}
.reserveRemain {
  font-size: 13px;
  margin: 0 auto;
  color: white;
  width: 66px;
  font-family: "NEXON Lv1 Gothic OTF";
}
.reserveDay {
  font-size: 16px;
  margin: 0 auto;
  width: 66px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.reserveTitle {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: center;
  font-size: 20px;
  padding-top: 15px;
  width: 100%;
  margin: 0 auto;
}
.reserveRemainStock {
  font-size: 15px;
  bottom: 80px;
  right: 20px;
}
.reserveStock {
  font-size: 15px;
  bottom: 80px;
  left: 20px;
}
.reserveCur {
  font-size: 20px;
  bottom: 30px;
  right: 20px;
  margin-bottom: 10px;
}
.reserveGoal {
  font-size: 15px;
  bottom: 60px;
  left: 20px;
}
.reserveMax {
  font-size: 17px;
  bottom: 50px;
  color: rgb(154, 150, 154);
  right: 20px;
  text-decoration: line-through;
}
.reserveRate {
  font-size: 15px;
  bottom: 85px;
  right: 20px;
}
.reserveDate {
  width: 180px;
  font-size: 15px;
  left: 20px;
  bottom: 60px;
}
.reserveNotrate {
  color: red;
  font-size: 16px;
}
.reserveDetail {
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
.reserveSoldout,
.reserveEnd {
  font-size: 14px;
  color: white;
  margin-top: 10px;
  width: 66px;
}
.reserveUnit {
  font-size: 15px;
  bottom: 85px;
  right: 20px;
}
</style>
