<template>
  <div class="detailTap">
    <div class="detailTapHeader">
      <div
        :class="[
          leftActive ? 'detailTapHeaderLeftIsActive' : 'detailTapHeaderLeft',
        ]"
        @click="leftActive = true"
      >
        상세설명
      </div>
      <div
        :class="[
          leftActive ? 'detailTapHeaderRight' : 'detailTapHeaderRightIsActive',
        ]"
        @click="leftActive = false"
      >
        구매후기
      </div>
    </div>
    <div class="detailTapBody">
      <div v-if="leftActive">
        <Descript :item="item"></Descript>
      </div>
      <div v-else>
        <Review :item="item"></Review>
      </div>
    </div>
  </div>
</template>

<script>
import Descript from "@/components/product/detail/DetailDescript.vue";
import Review from "@/components/product/detail/DetailReview.vue";
export default {
  components: {
    Descript,
    Review,
  },
  props: {
    item: Object,
  },
  data() {
    return {
      leftActive: true,
    };
  },
};
</script>

<style>
.detailTapHeaderRight,
.detailTapHeaderLeft {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.detailTapHeader {
  width: 100%;
}
.detailTapHeaderLeft {
  float: left;
  border-top: 1px solid gray;
  border-bottom: 1px solid gray;
  padding: 1% 0;
  text-align: center;
  cursor: pointer;
  font-size: 20px;
  font-family: "SDSamliphopangche_Outline";
  width: 50%;
}
.detailTapHeaderLeftIsActive {
  float: left;
  border-top: 1px solid gray;
  border-bottom: 1px solid gray;
  padding: 1% 0;
  text-align: center;
  width: 50%;
  font-size: 20px;
  font-family: "SDSamliphopangche_Outline";
  cursor: pointer;
  background: linear-gradient(#ffffff 80%, #abf200 20%);
}
.detailTapHeaderRight {
  float: right;
  border-top: 1px solid gray;
  border-bottom: 1px solid gray;
  padding: 1% 0;
  text-align: center;
  width: 50%;
  font-size: 20px;
  font-family: "SDSamliphopangche_Outline";
  cursor: pointer;
}
.detailTapHeaderRightIsActive {
  float: right;
  border-top: 1px solid gray;
  border-bottom: 1px solid gray;
  padding: 1% 0;
  text-align: center;
  width: 50%;
  font-size: 20px;
  font-family: "SDSamliphopangche_Outline";
  cursor: pointer;
  background: linear-gradient(#ffffff 80%, #abf200 20%);
}
.detailTapBody {
  clear: both;
}
</style>
