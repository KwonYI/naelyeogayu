<template>
  <div>
    <div id="explain">
      <p id="question">
        <span class="text" style="color: #5752f3">리퍼브 상품</span>이란?
      </p>
      <div id="refurbphoto"></div>
    </div>
    <pricegrid :category="3" />
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
      <component
        v-if="this.search"
        v-bind:is="selected"
        :name="card.name"
      ></component>
      <component v-else v-bind:is="selected" :card="card"></component>
    </div>
  </div>
</template>

<script>
import pricegrid from "@/components/product/sort/PriceGrid";
import refurb from "@/components/product/list/ProductList.vue";
import searchResult from "@/components/product/list/SearchResultList.vue";
import { mapGetters } from "vuex";

export default {
  components: {
    searchResult,
    refurb,
    pricegrid,
  },
  created() {
    if (this.getSearhItem.length > 0) {
      this.item.word = this.getSearhItem;
      this.getSearch();
    }
  },
  data: function () {
    return {
      selected: "refurb",
      card: {
        name: "refurb",
        category: 3,
      },
      item: {
        word: "",
        category: 3,
      },
      search: false,
    };
  },
  methods: {
    getList() {
      this.selected = "refurb";
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
  computed: {
    ...mapGetters({
      getSearhItem: "productStore/getSearhItem",
    }),
  },
};
</script>

<style src="@/assets/css/product/List.css" scoped></style>
