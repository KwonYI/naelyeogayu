<template>
  <div class="productDetailDescript">
    <div class="productDetailDescriptHeader">상세정보</div>
    <div class="productDetailDescriptBody">{{ item.product.descript }}</div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
};
</script>

<style></style>
