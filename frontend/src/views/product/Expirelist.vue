<template>
  <div>
    <div id="explain">
      <p id="question">
        식품에는
        <span class="text" style="color: rgb(250, 98, 98)">유통기한</span>과
        <span class="text">소비기한</span>이 있다는 사실, 알고 계셨나요?
      </p>
      <div id="explain1"></div>
      <p id="listtitle">상품 목록</p>
      <hr />
      <Expirecard
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
import Expirecard from "@/components/product/card/ExpireCard.vue";
import axios from "axios";
export default {
  components: {
    InfiniteLoading,
    Expirecard,
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
        url: `/product/expire/${this.limit}`,
      }).then((res) => {
        setTimeout(() => {
          if (this.getSize > this.limit) {
            let data = res.data.data;
            for (let key in data) {
              this.list.push(data[key].product);
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
      await this.$store.dispatch("productStore/getListSize", 1);
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
