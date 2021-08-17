<template>
  <div id="sortgrid">
    <p id="title">{{ category | title }}</p>
    <div id="list">
      <swiper class="swiper" :options="swiperOption">
        <swiper-slide v-for="(item, index) in list" :key="index">
          <ratecard :item="item" :index="index" />
        </swiper-slide>
        <div
          class="swiper-button-prev swiper-btn-prev"
          slot="button-prev"
        ></div>
        <div
          class="swiper-button-next swiper-btn-next"
          slot="button-next"
        ></div>
      </swiper>
    </div>
  </div>
</template>

<script>
import ratecard from "@/components/product/sort/RateCard";
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css";
import axios from "axios";
export default {
  components: {
    Swiper,
    SwiperSlide,
    ratecard,
  },
  props: {
    category: Number,
  },
  created() {
    this.getList(this.category);
  },
  methods: {
    getList(category) {
      axios({
        method: "get",
        url: `/product/sort/discount/onsale/${category}`,
      })
        .then((res) => {
          this.list = res.data.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  data: function () {
    return {
      list: "",
      swiperOption: {
        slidesPerView: 5,
        spaceBetween: 10,
        slidesPerGroup: 5,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },

        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
      },
    };
  },
  filters: {
    title(num) {
      if (num == 1) {
        return "유통임박 상품";
      } else if (num == 2) {
        return "못난이 농산물";
      } else {
        return "리퍼브 상품";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.swiper {
  height: 100%;
  width: 100%;

  .swiper-slide {
    justify-content: center;
    align-items: center;
  }
}
</style>

<style scoped>
#sortgrid {
  width: 1280px;
  margin: 20px auto;
  /* border: 1px solid black; */
}
#title {
  font-size: 22px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
#list {
  width: 1280px;
  margin: 0 auto;
}
</style>
