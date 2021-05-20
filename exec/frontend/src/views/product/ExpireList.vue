<template>
  <div>
    <div id="explain">
      <p id="question">
        식품에는
        <span class="text" style="color: rgb(250, 98, 98)">유통기한</span>과
        <span class="text" style="color: rgb(250, 98, 98)">소비기한</span>이
        있다는 사실, 알고 계셨나요?
      </p>
      <div id="expirephoto"></div>
    </div>
    <pricegrid :category="1" />
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
import expire from "@/components/product/list/ProductList.vue";
import searchResult from "@/components/product/list/SearchResultList.vue";
import { mapGetters } from "vuex";

export default {
  components: {
    pricegrid,
    expire,
    searchResult,
  },
  data: function () {
    return {
      selected: "expire",
      card: {
        name: "expire",
        category: 1,
      },
      item: {
        word: "",
        category: 1,
      },
      search: false,
    };
  },
  created() {
    if (this.getSearhItem.length > 0) {
      this.item.word = this.getSearhItem;
      this.getSearch();
    }
  },
  methods: {
    getList() {
      this.selected = "expire";
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
