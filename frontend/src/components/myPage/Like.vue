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

<style>
.likeInfoTitle {
  clear: both;
  font-weight: bold;
  font-size: 25px;
  border-bottom: solid 2px green;
}
.likeGrid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.5%;
  width: 100% !important;
  margin-top: 15px;
  align-items: center;
}
.likeBody {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.likeText {
  padding-top: 5%;
  padding-bottom: 2%;
  font-size: 24px;
  font-weight: bold;
}
</style>
