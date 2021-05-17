<template>
  <div>
    <div class="likeCardView">
      <div class="likeCard" @click="goDetail(item.product.status)">
        <div class="likeBox" v-if="item.product.status == 0">
          <img class="likeLabel" src="@/assets/label/label.png" alt="label" />
          <div class="likeDday">
            <p class="likeRemain">{{ category }}</p>
            <p class="likeDay">{{ dday }}</p>
          </div>
          <v-img
            class="likeImg"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="likeBox" v-if="item.product.status == 1">
          <img class="likeLabel" src="@/assets/label/label.png" alt="label" />
          <div class="likeDday">
            <p class="likeRemain">판매 대기</p>
            <p class="likeDay">{{ dday }}</p>
          </div>
          <v-img
            class="likeImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="likeBox" v-if="item.product.status == 2">
          <img
            class="likeLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="likeDday">
            <p class="likeSoldout">품절</p>
          </div>
          <v-img
            class="likeImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="likeBox" v-if="item.product.status == 3">
          <img
            class="likeLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="likeDday">
            <p class="likeEnd">경매 마감</p>
          </div>
          <v-img
            class="likeImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <p class="likeTitle">{{ item.product.name }}</p>
        <div class="likeInfo">
          <p class="likeDate">마감일 : {{ item.product.endDate }}</p>
          <p class="likeStock">수량 : {{ item.product.stock }}{{ unit }}</p>
          <p class="likeUnit" v-if="item.product.category == 2">
            무게 : {{ item.product.stock }}kg
          </p>
          <p class="likeMax" v-if="item.product.status == 0">
            {{ item.product.startPrice | comma }}원
          </p>
          <p class="likeCur">
            <span class="likeRate" v-if="item.product.status == 0"
              >{{ item.discountRate | fixed }}%</span
            >
            {{ item.curPrice | comma }}원/{{ unit }}
          </p>
          <p class="likeDetail">상세 보기</p>
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
.likeTitle,
.likeCur,
.likeRate,
.likeSoldout,
.likeEnd {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.likeStock,
.likeUnit,
.likeMax,
.likeDate,
.likeWatch {
  font-family: "NEXON Lv1 Gothic OTF";
}
.likeCardView {
  width: 300px;
  display: inline-block;
}
.likeCard {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  border-radius: 20px;
  cursor: pointer;
}
.likeCard:hover {
  box-shadow: 2px 2px rgb(179, 178, 178);
  transition: 0.4s;
}
.likeBox {
  overflow: hidden;
  border-radius: 20px 20px 0 0;
}
.likeImg:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
.likeImgGrayScale {
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
.likeInfo {
  height: 125px;
}
.likeInfo p {
  position: absolute;
}
.likeDday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
  text-align: center;
}
.likeLabel {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 20px;
}
.likeRemain {
  font-size: 13px;
  margin: 0 auto;
  color: white;
  width: 66px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.likeDay {
  font-size: 16px;
  margin: 0 auto;
  width: 66px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.likeTitle {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  text-align: center;
  font-size: 20px;
  padding-top: 15px;
  width: 200px;
  margin: 0 auto;
}
.likeStock {
  font-size: 15px;
  bottom: 85px;
  left: 20px;
}
.likeRate {
  color: red;
}
.likeNotrate {
  color: red;
  font-size: 16px;
}
.likeDate {
  width: 180px;
  font-size: 15px;
  left: 20px;
  bottom: 60px;
}
.likeMax {
  font-size: 17px;
  bottom: 0px;
  color: rgb(154, 150, 154);
  bottom: 50px;
  right: 20px;
  text-decoration: line-through;
}
.likeCur {
  font-size: 20px;
  bottom: 30px;
  right: 20px;
  margin-bottom: 10px;
}
.likeDetail {
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
.likeSoldout,
.likeEnd {
  font-size: 14px;
  color: white;
  margin-top: 10px;
  width: 66px;
}
.likeUnit {
  font-size: 15px;
  bottom: 85px;
  right: 20px;
}
</style>
