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
import terms from "@/components/product/upload/Terms.vue";
import forms from "@/components/product/upload/UploadForm.vue";
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
    async submit(product) {
      product.sellerId = Number(this.getUserId);
      await this.$store.dispatch("productStore/upload", product);
      alert("등록 성공! 해당 카테고리로 이동합니다");
      if (product.category == 1) {
        this.$router.push({ name: "Expire" });
      } else if (product.category == 2) {
        this.$router.push({ name: "Uglyfood" });
      } else {
        this.$router.push({ name: "Refurb" });
      }
    },
  },
  computed: {
    getUserId() {
      return this.$store.getters["userStore/id"];
    },
  },
};
</script>

<style src="@/assets/css/product/Upload.css" scoped></style>
