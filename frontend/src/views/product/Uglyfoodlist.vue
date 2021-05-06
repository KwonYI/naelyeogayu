<template>
  <div>
    <div id="explain">
      <p id="question">
        이세상에 버려질
        <span class="text" style="color: #42cd06">농산물</span>은 없다!
      </p>
      <div id="photo"></div>
      <p id="listtitle">상품 목록</p>
      <hr />
      <Uglycard
        v-for="(item, index) in list"
        v-bind:key="index"
        v-bind:item="list[index]"
      />
      <infinite-loading
        @infinite="infiniteHandler"
        spinner="circles"
        ref="infiniteLoading"
      >
        <div slot="no-more" class="mb-12">목록의 끝입니다 :)</div>
        <div slot="no-results" class="mb-12">목록이 비어있습니다 :(</div>
      </infinite-loading>
    </div>
  </div>
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import Uglycard from "@/components/product/card/UglyCard.vue";
import axios from "axios";

export default {
  components: {
    Uglycard,
    InfiniteLoading,
  },
  created() {
    this.sizeCheck();
  },
  data: function () {
    return {
      list: [],
      limit: 0,
    };
  },
  methods: {
    infiniteHandler($state) {
      axios({
        method: "get",
        url: `/product/ugly/${this.limit}`,
      }).then((res) => {
        setTimeout(() => {
          if (this.getSize > this.limit) {
            let data = res.data.data;
            for (let key in data) {
              this.list.push(data[key]);
            }
            this.limit += 6;
            $state.loaded();
          } else {
            $state.complete();
          }
        }, 700);
      });
    },
    async sizeCheck() {
      await this.$store.dispatch("productStore/getListSize", 2);
    },
  },
  computed: {
    getSize() {
      return this.$store.getters["productStore/getsize"];
    },
  },
};
</script>

<style src="@/assets/css/List.css" scoped></style>
