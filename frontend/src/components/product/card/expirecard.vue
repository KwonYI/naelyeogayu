<template>
  <div id="cardview">
    <div id="card">
      <div id="box">
        <img id="label" src="@/assets/label.png" alt="label" />
        <div id="dday">
          <p id="remain">유통 임박</p>
          <p id="day">D-10</p>
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
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
};
</script>

<style src="@/assets/css/expirecard.css" scoped></style>
