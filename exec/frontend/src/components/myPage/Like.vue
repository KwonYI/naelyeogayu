<template>
  <div class="likeInfoForm">
    <div class="likeInfoTitle">찜한 목록</div>
    <div class="likeGrid" v-if="hasLiked">
      <Like
        v-for="(item, index) in items"
        v-bind:item="item"
        v-bind:key="index"
      />
    </div>
    <div class="likeBody" v-else>
      <div class="likeText">찜한 상품이 없습니다.</div>
    </div>
  </div>
</template>

<script>
import Like from "@/components/product/card/LikeCard.vue";
export default {
  components: {
    Like,
  },
  data() {
    return {
      items: [],
      hasLiked: true,
    };
  },
  methods: {
    async getLike() {
      await this.$axios({
        url: "/bookmark/list/" + this.$store.getters["userStore/id"],
        method: "GET",
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.items = response.data.data;
          } else {
            this.hasLiked = false;
          }
        })
        .catch((error) => {
          console.error(error);
          alert("찜한 정보를 받아오는 중 에러가 발생했습니다.");
        });
    },
  },
  created() {
    this.getLike();
  },
};
</script>

<style src="@/assets/css/user/myPage/Like.css" scoped></style>
