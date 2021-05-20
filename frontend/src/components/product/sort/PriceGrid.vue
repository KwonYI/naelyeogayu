<template>
  <div id="sortgrid">
    <p id="title">
      최저가 상품<img
        id="price"
        src="@/assets/label/won.png"
        alt="price"
        height="26px"
      />
    </p>
    <div id="list">
      <swiper class="swiper" :options="swiperOption">
        <swiper-slide v-for="(item, index) in list" :key="index">
          <pricecard :item="item" :index="index" />
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
import pricecard from "@/components/product/sort/PriceCard";
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/css/swiper.css";
import axios from "axios";
export default {
  components: {
    Swiper,
    SwiperSlide,
    pricecard,
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
        url: `/product/sort/price/onsale/${category}`,
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
#price {
  margin-left: 7px;
  vertical-align: middle;
}
</style>
