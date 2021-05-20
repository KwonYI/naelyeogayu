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
      <div class="detailTapBody" v-else>
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

<style src="@/assets/css/product/detail/DetailTap.css" scoped></style>
