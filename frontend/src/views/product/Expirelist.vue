<template>
  <div>
    <div id="explain">
      <p id="question">
        식품에는 <span class="date">유통기한</span>과
        <span class="date">소비기한</span>이 있다는 사실, 알고 계셨나요?
      </p>
      <div id="photo"></div>
      <p id="listtitle">상품 목록</p>
      <expirecard
        v-for="(item, index) in list"
        v-bind:key="item.id"
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
      <hr />
    </div>
  </div>
</template>

<script>
import expirecard from "@/components/product/card/expirecard.vue";
import InfiniteLoading from "vue-infinite-loading";
import Expirecard from "../../components/product/card/expirecard.vue";
import axios from "axios";
export default {
  components: {
    expirecard,
    InfiniteLoading,
    Expirecard,
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
        url: `/product/expire`,
      }).then((res) => {
        setTimeout(() => {
          if (3 > this.limit) {
            let data = res.data.data.product;
            console.log(data);
            for (let key in data) {
              this.list.push(data[key]);
            }
            this.limit += 3;
            $state.loaded();
          } else {
            $state.complete();
          }
        }, 1000);
      });
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: "NEXON Lv1 Gothic OTF Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF Bold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#explain {
  width: 1290px;
  margin: 0 auto;
}
#question {
  max-height: 40px;
  margin: 5px 0px 5px 150px;
  font-size: 18px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
  color: rgb(57, 57, 57);
}
#photo {
  margin: 0 auto;
  height: 170px;
  width: 1050px;
  border: solid 2px rgb(95, 95, 95);
  background-image: url("../../assets/expire.png");
}
#listtitle {
  text-align: center;
  font-size: 26px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
  color: rgb(0, 0, 0);
  max-height: 20px;
  margin-top: 20px;
  margin-bottom: 25px;
}
.date {
  font-size: 24px;
  color: rgb(250, 98, 98);
}
</style>
