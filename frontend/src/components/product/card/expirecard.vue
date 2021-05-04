<template>
  <div id="cardview">
    <div v-if="item.status == 0">
      <div id="card">
        <div id="box">
          <img id="label" src="@/assets/label.png" alt="label" />
          <div id="dday">
            <p id="remain">유통 임박</p>
            <p id="day">D-{{ item.expirationDate | calculate }}</p>
          </div>
          <v-img
            id="img"
            :src="item.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div>
          <p id="title">{{ item.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.endDate }}</p>
          <p id="stock">수량 : {{ item.stock }}개</p>
          <p id="max">{{ item.startPrice }}원</p>
          <p id="cur"><span id="rate">50%</span> 2000원/개</p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
    <div v-if="item.status == 1">
      <div id="card">
        <div id="box">
          <img id="label" src="@/assets/label.png" alt="label" />
          <div id="dday">
            <p id="remain">판매 대기</p>
            <p id="day">D-{{ item.expirationDate | calculate }}</p>
          </div>
          <v-img
            id="img"
            :src="item.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div>
          <p id="title">{{ item.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.endDate }}</p>
          <p id="stock">수량 : {{ item.stock }}개</p>
          <p id="max">{{ item.startPrice }}원</p>
          <p id="cur"><span id="rate">50%</span> 2000원/개</p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
    <div v-if="item.status == 2">
      <div id="endCard">
        <img id="label" src="@/assets/endlabel.png" alt="label" />
        <div id="dday">
          <p id="soldout">매진</p>
        </div>
        <div id="grey">
          <div id="box">
            <v-img
              :src="item.imageUrl"
              height="270px"
              max-height="270px"
              :aspect-ratio="1 / 1"
            />
          </div>
        </div>
        <div>
          <p id="title">{{ item.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.endDate }}</p>
          <p id="stock">수량 : {{ item.stock }}개</p>
          <p id="max">{{ item.startPrice }}원</p>
          <p id="cur"><span id="rate">50%</span> 2000원/개</p>
          <p id="detail">상세 보기</p>
        </div>
      </div>
    </div>
    <div v-if="item.status == 3">
      <div id="endCard">
        <img id="label" src="@/assets/endlabel.png" alt="label" />
        <div id="dday">
          <p id="end">경매 마감</p>
        </div>
        <div id="grey">
          <div id="box">
            <v-img
              :src="item.imageUrl"
              height="270px"
              max-height="270px"
              :aspect-ratio="1 / 1"
            />
          </div>
        </div>
        <div>
          <p id="title">{{ item.name }}</p>
        </div>
        <div id="info">
          <p id="date">마감일 : {{ item.endDate }}</p>
          <p id="stock">수량 : {{ item.stock }}개</p>
          <p id="max">{{ item.startPrice }}원</p>
          <p id="cur"><span id="rate">50%</span> 2000원/개</p>
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
  },
};
</script>

<style src="@/assets/css/ExpireCard.css" scoped></style>
