<template>
  <div class="productDetail">
    <div class="productDetailHeader">
      <a @click="goMain">홈</a>
      <v-icon>mdi-right-arrow</v-icon>
      <a @click="goPage">{{ category }}</a>
    </div>
    <div class="productDetailImg">
      <v-img :src="item.imageUrl"></v-img>
    </div>
    <div class="productDetailInfo">
      <div class="productDetailInfoHeader">
        <span>{{ category }}</span>
        <v-divider vertical></v-divider>
        <v-btn icon><v-icon>mdi-house</v-icon></v-btn>
      </div>
      <div class="productDetailTitle">{{ item.product.name }}</div>
      <div class="productDetailRate">{{ item.product.rate }}</div>
      <div class="productDetailMax">{{ item.product.startPrice }}</div>
      <div class="productDetailCur">{{ item.product.minPrice }}</div>
      <div class="productDetailCountName">수량</div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.productDetail {
  display: flex;
}
</style>
