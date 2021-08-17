<template>
  <div>
    <div class="likeCardView">
      <div class="likeCard" @click="goDetail(item.product.status)">
        <div class="likeBox" v-if="item.product.status == 0">
          <img class="likeLabel" src="@/assets/label/label.png" alt="label" />
          <div class="likeDday">
            <p class="likeRemain">{{ category }}</p>
            <p class="likeDay">{{ dday }}</p>
          </div>
          <v-img
            class="likeImg"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="likeBox" v-if="item.product.status == 1">
          <img class="likeLabel" src="@/assets/label/label.png" alt="label" />
          <div class="likeDday">
            <p class="likeEnd">판매 대기</p>
          </div>
          <v-img
            class="likeImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="likeBox" v-if="item.product.status == 2">
          <img
            class="likeLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="likeDday">
            <p class="likeSoldout">품절</p>
          </div>
          <v-img
            class="likeImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="likeBox" v-if="item.product.status == 3">
          <img
            class="likeLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="likeDday">
            <p class="likeEnd">경매 마감</p>
          </div>
          <v-img
            class="likeImgGrayScale"
            :src="item.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <p class="likeTitle">{{ item.product.name }}</p>
        <div class="likeInfo">
          <p class="likeDate">마감일 : {{ item.product.endDate }}</p>
          <p class="likeStock">수량 : {{ item.product.stock }}{{ unit }}</p>
          <p
            class="likeUnit"
            v-if="item.product.category == 2 && item.product.unit != 0"
          >
            무게 : {{ item.product.unit }}kg
          </p>
          <p class="likeMax" v-if="item.product.status == 0">
            {{ item.product.startPrice | comma }}원
          </p>
          <p class="likeCur">
            <span class="likeRate" v-if="item.product.status == 0"
              >{{ item.discountRate | fixed }}%</span
            >
            {{ item.curPrice | comma }}원/{{ unit }}
          </p>
          <p class="likeDetail">상세 보기</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import "moment/locale/ko";
export default {
  props: {
    item: Object,
  },
  computed: {
    category() {
      if (this.item.product.category == 1) {
        return "유통 임박";
      } else if (this.item.product.category == 2) {
        return "판매중";
      }
      return "최대 할인";
    },
    dday() {
      if (this.item.dday > 0) {
        return "D-" + this.item.dday;
      } else if (this.item.dday == 0) {
        return "D-day";
      }
      return "";
    },
    unit() {
      if (this.item.product.category == 2) {
        return "box";
      }
      return "개";
    },
  },
  filters: {
    calculate(date) {
      return moment(date, "YYYY-MM-DD").diff(
        moment().format("YYYY-MM-DD"),
        "days"
      );
    },
    fixed(rate) {
      return rate.toFixed(2);
    },
    comma(v) {
      return String(v).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    goDetail(status) {
      if (status == 2) {
        if (
          confirm("해당 상품은 품절되었습니다. 찜 목록에서 삭제하시겠습니까?")
        ) {
          this.disLikeCheck();
        }
      } else if (status == 3) {
        if (
          confirm("해당 상품은 마감되었습니다. 찜 목록에서 삭제하시겠습니까?")
        ) {
          this.disLikeCheck();
        }
      } else {
        this.$router.go(
          this.$router.push({
            name: "Detail",
            params: { productId: this.item.product.id },
          })
        );
      }
    },
    disLikeCheck() {
      this.$axios({
        url: "/bookmark",
        method: "DELETE",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          memberId: this.$store.getters["userStore/id"],
          productId: this.item.product.id,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("삭제되었습니다.");
            window.location.reload();
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
  created() {
    console.log(this.item);
  },
};
</script>

<style src="@/assets/css/product/card/LikeCard.css" scoped></style>
