<template>
  <div>
    <Expirecard
      v-for="(item, index) in list"
      :key="index"
      :item="list[index]"
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
</template>

<script>
import InfiniteLoading from "vue-infinite-loading";
import Expirecard from "@/components/product/card/ExpireCard.vue";
import axios from "axios";
export default {
  props: {
    name: String,
  },
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
        url: `/product/${this.name}/${this.limit}`,
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

<style></style>
