<template>
  <div>
    <div class="buyCardView">
      <div class="buyCard">
        <div class="buyBox">
          <div class="buyDday">
            <p class="buyRemain">{{ category }}</p>
            <p class="buyDay">D-10</p>
          </div>
          <v-img
            :class="[isFinish ? 'buyImgGrayScale' : 'buyImg']"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="buyInfo">
          <p class="buyTitle">{{ item.product.name }}</p>
          <p class="buyStock">남은 수량 : {{ item.product.stock }}개</p>
          <p class="buyRate">
            하락율 : <span style="color: red"> - {{ item.product.rate }}%</span>
          </p>
          <p class="buyMax">{{ item.product.startPrice }}원</p>
          <p class="buyCur">현재 : {{ item.product.minPrice }}원/개</p>
          <p class="buySub">구매</p>
          <p class="buyDate">{{ item.buyDate }}</p>
          <p class="buyGoal">{{ item.price }}원</p>
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
.buyTitle,
.buyCur,
.buyGoal,
.buySub {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.buyRemainStock,
.buyStock,
.buyMax,
.buyDate,
.buyRate {
  font-family: "NEXON Lv1 Gothic OTF";
}
.buyCardView {
  width: 300px;
  display: inline-block;
}
.buyCard {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  cursor: pointer;
}
.buyBox {
  overflow: hidden;
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
.buyInfo {
  height: 200px;
}
.buyInfo p {
  position: absolute;
}
.buyDday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 10px;
  background-color: rgb(208, 119, 134);
  text-align: center;
}
.buyRemain {
  font-size: 12px;
  margin: 0 7px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF";
}
.buyDay {
  font-size: 17px;
  margin-top: 5px;
  margin-bottom: 5px;
  color: white;
  font-weight: bold;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.buyTitle {
  width: 180px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  bottom: 140px;
  left: 20px;
  font-size: 20px;
}
.buyStock {
  font-size: 15px;
  bottom: 120px;
  right: 20px;
}
.buyCur {
  font-size: 20px;
  bottom: 45px;
  right: 20px;
}
.buyGoal {
  font-size: 20px;
  bottom: 0px;
  right: 20px;
}
.buyMax {
  bottom: 70px;
  color: rgb(154, 150, 154);
  right: 20px;
  text-decoration: line-through;
}
.buyRate {
  font-size: 15px;
  bottom: 95px;
  right: 20px;
}
.buyDate {
  font-size: 15px;
  left: 20px;
  bottom: 0px;
}
.buySub {
  font-size: 20px;
  bottom: 20px;
  left: 20px;
}
</style>
