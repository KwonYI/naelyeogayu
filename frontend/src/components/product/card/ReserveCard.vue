<template>
  <div>
    <div class="reserveCardView">
      <div class="reserveCard" @click="goDetail(item.reserve.product.status)">
        <div class="reserveBox" v-if="item.reserve.product.status == 0">
          <img
            class="reserveLabel"
            src="@/assets/label/label.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveRemain">{{ category }}</p>
            <p class="reserveDay">{{ dday }}</p>
          </div>
          <v-img
            class="reserveImg"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveBox" v-if="item.reserve.product.status == 1">
          <img
            class="reserveLabel"
            src="@/assets/label/label.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveEnd">판매 대기</p>
          </div>
          <v-img
            class="reserveImgGrayScale"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveBox" v-if="item.reserve.product.status == 2">
          <img
            class="reserveLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveSoldout">품절</p>
          </div>
          <v-img
            class="reserveImgGrayScale"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div class="reserveBox" v-if="item.reserve.product.status == 3">
          <img
            class="reserveLabel"
            src="@/assets/label/endlabel.png"
            alt="label"
          />
          <div class="reserveDday">
            <p class="reserveEnd">경매 마감</p>
          </div>
          <v-img
            class="reserveImgGrayScale"
            :src="item.reserve.product.imageUrl"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <p class="reserveTitle">{{ item.reserve.product.name }}</p>
        <div
          class="reserveInfo"
          v-if="item.reserve.status == 0 && item.reserve.product.status < 2"
        >
          <p class="reserveDueDate">만료일 : {{ item.reserve.dueDate }}</p>
          <p class="reserveRemainStock">
            수량 : {{ item.reserve.product.stock }}{{ unit }}
          </p>
          <p class="reserveStock">
            예약 수량 : {{ item.reserve.count }}{{ unit }}
          </p>
          <p class="reserveGoal">예약 : {{ item.reserve.price | comma }}원</p>
          <p class="reserveMax" v-if="item.reserve.product.status == 0">
            {{ item.reserve.product.startPrice | comma }}원
          </p>
          <p class="reserveCur">
            <span class="reserveRate" v-if="item.reserve.product.status == 0"
              >{{ item.productCurDiscountRate | fixed }}%</span
            >
            {{ item.productCurPrice | comma }}원/{{ unit }}
          </p>
          <p class="reserveDetail">상세 보기</p>
        </div>
        <div v-else>
          <div class="reserveFailInfo" v-html="message"></div>
          <p class="reserveFailDetail">상세 보기</p>
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
      if (this.item.reserve.product.category == 1) {
        return "유통 임박";
      } else if (this.item.reserve.product.category == 2) {
        return "판매중";
      }
      return "최대 할인";
    },
    dday() {
      if (this.item.productCurDday > 0) {
        return "D-" + this.item.productCurDday;
      } else if (this.item.productCurDday == 0) {
        return "D-day";
      }
      return "";
    },
    message() {
      if (this.item.reserve.status == 1) {
        return "입찰 완료했습니다!<br />구매에 성공했습니다.";
      } else if (this.item.reserve.status == 2) {
        return "예약이 만료되어<br />구매에 실패했습니다.";
      } else if (
        this.item.reserve.status == 3 ||
        this.item.reserve.product.status == 2
      ) {
        return "재고가 부족하여<br />구매에 실패했습니다.";
      } else if (this.item.reserve.status == 4) {
        return "잔액이 부족하여<br />구매에 실패했습니다.";
      }
      return "경매가 마감되어<br /> 구매에 실패했습니다.";
    },
    unit() {
      if (this.item.reserve.product.category == 2) {
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
          confirm("해당 상품은 품절되었습니다. 예약 목록에서 삭제하시겠습니까?")
        ) {
          this.cancelReserve();
        }
      } else if (status == 3) {
        if (
          confirm("해당 상품은 마감되었습니다. 예약 목록에서 삭제하시겠습니까?")
        ) {
          this.cancelReserve();
        }
      } else {
        this.$router.go(
          this.$router.push({
            name: "Detail",
            params: { productId: this.item.reserve.product.id },
          })
        );
      }
    },
    cancelReserve() {
      this.$axios({
        url:
          "/reserve/" +
          this.$store.getters["userStore/id"] +
          "/" +
          this.item.reserve.product.id,
        method: "DELETE",
        headers: {
          "x-access-token": localStorage.getItem("token"),
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("예약이 취소되었습니다.");
            window.location.reload();
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style src="@/assets/css/product/card/ReserveCard.css" scoped></style>
