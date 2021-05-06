<template>
  <div id="cardview">
    <div v-if="item.product.status == 0">
      <div id="card">
        <div id="box">
          <img id="label" src="@/assets/label.png" alt="label" />
          <div id="dday">
            <p id="remain">마감 임박</p>
            <p id="day">D-{{ item.dday }}</p>
          </div>
          <v-img
            id="img"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div>
          <p id="title">{{ item.product.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.product.endDate }}</p>
          <p id="stock">수량 : {{ item.product.stock }}box</p>
          <p id="unit">무게 : {{ item.product.unit }}kg/box</p>
          <p id="max">{{ item.product.startPrice }}원</p>
          <p id="cur">
            <span id="rate">{{ item.discountRate | fixed }}%</span>
            {{ item.curPrice }}원/box
          </p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
    <div v-if="item.product.status == 1">
      <div id="card">
        <div id="box">
          <img id="label" src="@/assets/label.png" alt="label" />
          <div id="dday">
            <p id="remain">판매 대기</p>
            <p id="day">D-{{ item.dday }}</p>
          </div>
          <v-img
            id="img"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div>
          <p id="title">{{ item.product.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.product.endDate }}</p>
          <p id="stock">수량 : {{ item.product.stock }}box</p>
          <p id="unit">무게 : {{ item.product.unit }}kg/box</p>
          <p id="max">{{ item.product.startPrice }}원</p>
          <p id="cur">
            <span id="rate">{{ item.discountRate | fixed }}%</span>
            {{ item.curPrice }}원/box
          </p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
    <div v-if="item.product.status == 2">
      <div id="endCard">
        <img id="label" src="@/assets/endlabel.png" alt="label" />
        <div id="dday">
          <p id="soldout">매진</p>
        </div>
        <div id="grey">
          <div id="box">
            <v-img
              :src="item.product.imageUrl"
              height="270px"
              max-height="270px"
              :aspect-ratio="1 / 1"
            />
          </div>
        </div>
        <div>
          <p id="title">{{ item.product.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.product.endDate }}</p>
          <p id="stock">수량 : {{ item.product.stock }}box</p>
          <p id="unit">무게 : {{ item.product.unit }}kg/box</p>
          <p id="max">{{ item.product.startPrice }}원</p>
          <p id="cur">
            <span id="rate">{{ item.discountRate | fixed }}%</span>
            {{ item.curPrice }}원/box
          </p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
    <div v-if="item.product.status == 3">
      <div id="endCard">
        <img id="label" src="@/assets/endlabel.png" alt="label" />
        <div id="dday">
          <p id="end">경매 마감</p>
        </div>
        <div id="grey">
          <div id="box">
            <v-img
              :src="item.product.imageUrl"
              height="270px"
              max-height="270px"
              :aspect-ratio="1 / 1"
            />
          </div>
        </div>
        <div>
          <p id="title">{{ item.product.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.product.endDate }}</p>
          <p id="stock">수량 : {{ item.product.stock }}box</p>
          <p id="unit">무게 : {{ item.product.unit }}kg/box</p>
          <p id="max">{{ item.product.startPrice }}원</p>
          <p id="cur">
            <span id="rate">{{ item.discountRate | fixed }}%</span>
            {{ item.curPrice }}원/box
          </p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import "moment/locale/ko";
export default {
  //0:판매중 , 1: 판매대기, 2:품절, 3:경매마감
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
  },
};
</script>

<style src="@/assets/css/ExpireCard.css" scoped></style>
