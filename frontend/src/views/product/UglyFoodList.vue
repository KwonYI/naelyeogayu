<template>
  <div>
    <div id="explain">
      <p id="question">
        이세상에 버려질
        <span class="text" style="color: #42cd06">농산물</span>은 없다!
      </p>
      <div id="uglypthoto"></div>
    </div>
    <pricegrid :category="2" />
    <div id="title">
      <p id="listtitle" @click="getList()">상품 목록</p>
      <div id="searchBar">
        <input
          class="input"
          type="text"
          placeholder="검색"
          v-model="item.word"
          @keyup.enter="getSearch()"
        />
        <img
          src="@/assets/label/search.png"
          alt="icon"
          id="icon"
          @click="getSearch()"
        />
      </div>
      <hr />
      <component v-if="this.search" v-bind:is="selected"></component>
      <component v-else v-bind:is="selected" :name="selected"></component>
    </div>
  </div>
</template>

<script>
import pricegrid from "@/components/product/sort/PriceGrid";
import ugly from "@/components/product/list/ProductList.vue";
import searchResult from "@/components/product/list/SearchResultList.vue";

export default {
  components: {
    ugly,
    searchResult,
    pricegrid,
  },
  data: function () {
    return {
      selected: "ugly",
      item: {
        word: "",
        category: 2,
      },
      search: false,
    };
  },
  methods: {
    getList() {
      this.selected = "ugly";
      this.item.word = "";
      this.search = false;
    },
    getSearch() {
      if (this.item.word.length > 0) {
        this.$store.dispatch("productStore/getSearchResult", this.item);
        this.search = true;
      } else {
        this.getList();
      }
    },
  },
  watch: {
    search: function () {
      if (this.search) {
        this.selected = "searchResult";
      }
    },
  },
};
</script>

<style src="@/assets/css/List.css" scoped></style>
