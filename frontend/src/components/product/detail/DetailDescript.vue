<template>
  <div class="productDetailDescript">
    <div class="productDetailDescriptHeader">{{ item.product.name }}</div>
    <div class="productDetailDescriptBody">
      {{ item.product.descript | fixed }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
  filters: {
    fixed(enter) {
      return enter.replace("\n", "<br />");
    },
  },
};
</script>

<style>
.productDetailDescript {
  border-left: 1px solid black;
  border-right: 1px solid black;
  border-bottom: 1px solid black;
}
.productDetailDescriptHeader {
  font-family: "NEXON Lv1 Gothic OTF Bold";
  font-size: 30px;
  padding: 3%;
}
.productDetailDescriptBody {
  font-family: "NEXON Lv1 Gothic OTF";
  font-size: 20px;
  padding: 0 5%;
  margin-bottom: 100px;
}
</style>
