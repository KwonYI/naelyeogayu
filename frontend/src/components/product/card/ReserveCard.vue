<template>
  <div>
    <div class="reserveCardView">
      <div class="reserveCard">
        <div class="reserveBox">
          <div class="reserveDday">
            <p class="reserveRemain">{{ category }}</p>
            <p class="reserveDay">D-10</p>
          </div>
          <v-img
            class="reserveImg"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveInfo">
          <p class="reserveTitle">{{ item.product.name }}</p>
          <p class="reserveRemainStock">
            남은 수량 : {{ item.product.stock }}개
          </p>
          <p class="reserveStock">예약 수량 : {{ item.count }}개</p>
          <p class="reserveRate">
            하락율 : <span style="color: red"> - {{ item.product.rate }}%</span>
          </p>
          <p class="reserveMax">{{ item.product.startPrice }}원</p>
          <p class="reserveCur">현재 : {{ item.product.minPrice }}원/개</p>
          <p class="reserveGoal">예약 : {{ item.price }}원</p>
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
        return "유통임박 상품";
      } else if (this.item.product.category == 2) {
        return "못난이 농산물";
      }
      return "리퍼브 상품";
    },
    isFinish: function () {
      if (this.item.product.status == 0) {
        return false;
      }
      return true;
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
.reserveGoal {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.reserveRemainStock,
.reserveStock,
.reserveMax,
.reserveDate,
.reserveRate {
  font-family: "NEXON Lv1 Gothic OTF";
}
.reserveCardView {
  width: 300px;
  display: inline-block;
}
.reserveCard {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  cursor: pointer;
}
.reserveBox {
  overflow: hidden;
}
.reserveImgGrayScale {
  /* IE */
  filter: progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);
  /* Chrome, Safari */
  -webkit-filter: grayscale(1);
  /* Firefox */
  filter: grayscale(1);
}
.reserveImg:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.reserveInfo {
  height: 210px;
}
.reserveInfo p {
  position: absolute;
}
.reserveDday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 10px;
  background-color: rgb(208, 119, 134);
  text-align: center;
}
.reserveRemain {
  font-size: 12px;
  margin: 0 7px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF";
}
.reserveDay {
  font-size: 17px;
  margin-top: 5px;
  margin-bottom: 5px;
  color: white;
  font-weight: bold;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.reserveTitle {
  width: 180px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  bottom: 150px;
  left: 20px;
  font-size: 20px;
}
.reserveRemainStock {
  font-size: 15px;
  bottom: 135px;
  right: 20px;
}
.reserveStock {
  font-size: 15px;
  bottom: 110px;
  right: 20px;
}
.reserveCur {
  font-size: 20px;
  bottom: 32px;
  right: 20px;
}
.reserveGoal {
  font-size: 20px;
  bottom: 0px;
  right: 20px;
}
.reserveMax {
  bottom: 60px;
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
  font-size: 15px;
  right: 20px;
  bottom: 0px;
}
</style>
