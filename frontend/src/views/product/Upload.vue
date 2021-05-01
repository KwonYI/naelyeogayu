<template>
  <div>
    <div>
      <h1>상품 등록</h1>
    </div>
    <v-divider width="1280" style="margin: 0 auto"></v-divider>
    <terms v-if="this.step === 1" @agreeall="check" />
    <forms v-if="this.step === 2" @back="backpage" @upload="submit" />
  </div>
</template>

<script>
import terms from "@/components/product/upload/terms.vue";
import forms from "@/components/product/upload/Uploadform.vue";
export default {
  components: {
    terms,
    forms,
  },
  data: function () {
    return {
      step: 1,
    };
  },
  methods: {
    check(flag) {
      if (flag) this.step++;
    },
    backpage(flag) {
      if (flag) this.step--;
    },
    submit(product) {
      product.sellerId = this.getUserId;
    },
  },
  computed: {
    getUserId() {
      return this.$store.getters["userStore/id"];
    },
  },
};
</script>

<style>
@font-face {
  font-family: "NEXON Lv1 Gothic OTF Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF Bold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

div h1 {
  padding: 10px;
}

div h1 {
  text-align: center;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
</style>
