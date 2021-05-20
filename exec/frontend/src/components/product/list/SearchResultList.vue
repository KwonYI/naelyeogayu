<template>
  <div>
    <div v-if="getSize.length > 0">
      <component
        v-bind:is="name"
        v-for="(item, index) in getSize"
        :key="index"
        :item="item"
      />
    </div>
    <div v-else>
      <p id="noresult">검색결과가 없습니다!</p>
    </div>
  </div>
</template>

<script>
import expire from "@/components/product/card/ExpireCard.vue";
import refurb from "@/components/product/card/RefurbCard.vue";
import ugly from "@/components/product/card/UglyCard.vue";
export default {
  components: {
    expire,
    refurb,
    ugly,
  },
  props: {
    name: String,
  },
  computed: {
    getSize() {
      return this.$store.getters["productStore/getResultList"];
    },
  },
};
</script>

<style scoped>
#noresult {
  text-align: center;
  width: 400px;
  margin: 100px auto;
  font-size: 25px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
  color: rgb(83, 82, 82);
}
</style>
