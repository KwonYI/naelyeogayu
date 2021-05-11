<template>
  <div class="DetailTap">
    <div class="DetailTapHeader">
      <div
        :class="[
          leftActive ? 'DetailTapHeaderLeftIsActive' : 'DetailTapHeaderLeft',
        ]"
        @click="leftActive = true"
      >
        상세설명
      </div>
      <div
        :class="[
          leftActive ? 'DetailTapHeaderRight' : 'DetailTapHeaderRightIsActive',
        ]"
        @click="leftActive = false"
      >
        구매후기
      </div>
    </div>
    <div v-if="leftActive">
      <Descript :item="item"></Descript>
    </div>
    <div v-else>
      <Review :item="item"></Review>
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
.DetailTapHeaderRight,
.DetailTapHeaderLeft {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.DetailTapHeader {
  width: 100%;
}
.DetailTapHeaderLeft {
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
.DetailTapHeaderLeftIsActive {
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
.DetailTapHeaderRight {
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
.DetailTapHeaderRightIsActive {
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
</style>
