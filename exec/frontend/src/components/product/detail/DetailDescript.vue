<template>
  <div class="productDetailDescript">
    <div class="productDetailDescriptHeader">{{ item.product.name }}</div>
    <div class="productDetailDescriptBody" v-html="desc"></div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
  data() {
    return {
      desc: "",
    };
  },
  created() {
    this.desc = this.item.product.descript.replace(/(?:\r\n|\r|\n)/g, "<br>");
  },
};
</script>

<style src="@/assets/css/product/detail/DetailDescript.css" scoped></style>
