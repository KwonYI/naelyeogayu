<template>
  <div>
    <div v-if="getSize.length > 0">
      <Expirecard v-for="(item, index) in getSize" :key="index" :item="item" />
    </div>
    <div v-else>
      <p id="noresult">검색결과가 없습니다!</p>
    </div>
  </div>
</template>

<script>
import Expirecard from "@/components/product/card/ExpireCard.vue";
export default {
  components: {
    Expirecard,
  },
  methods: {},
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
