<template>
  <div class="productDetailInfo">
    <div class="productDetailInfoImg">
      <v-img :src="item.product.imageUrl" max-height="650px"></v-img>
    </div>
    <div class="productDetailInfoBody">
      <div class="productDetailInfoHeader">
        <span class="productDetailInfoCategory" @click="goCategory">
          {{ category }}
        </span>
        <span class="productDetailInfoSearch" @click="goMain">
          <v-icon>mdi-home-outline</v-icon>
          {{ item.product.name }}
        </span>
        <span class="productDetailEndDate"
          >종료일: {{ item.product.endDate }}</span
        >
      </div>
      <div class="productDetailDday">{{ dday }}</div>
      <div class="productDetailTitle">
        {{ item.product.name }}
        <span class="productLikeCheck">
          <v-hover v-slot="{ hover }">
            <v-icon
              x-large
              :color="hover ? 'green' : 'gray'"
              v-if="!isLike"
              @click="likeCheck"
              >mdi-star-outline</v-icon
            >
          </v-hover>
          <v-icon x-large v-if="isLike" color="green" @click="disLikeCheck"
            >mdi-star</v-icon
          >
        </span>
      </div>
      <div class="productDetailRemain">
        남은 수량 {{ item.product.stock }}개
      </div>
      <div class="productPriceDetail">
        <span class="productDetailRate">{{ item.discountRate }}% </span>
        <span class="productPriceDetailInfo">
          <div class="productDetailMax">
            {{ item.product.startPrice | comma }}원
          </div>
          <div class="productDetailCur">{{ item.curPrice | comma }}원</div>
        </span>
      </div>
      <div class="productDetailCountName">수량</div>
      <div class="productDetailCount">
        <v-btn class="countButtonLeft" tile @click="addCount"
          ><v-icon>mdi-plus</v-icon></v-btn
        >
        <span class="countNumber">{{ count }}</span>
        <v-btn class="countButtonRight" tile @click="subCount"
          ><v-icon>mdi-minus</v-icon></v-btn
        >
      </div>
      <div class="productDetailPriceName">입찰금액</div>
      <div class="productDetailPrice">{{ price | comma }}원</div>
      <div class="productDetailButton">
        <div class="productDetailReserveButton">예약하기</div>
        <div class="productDetailBuyButton" @click="buy">입찰하기</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
  },
  data() {
    return {
      count: 0,
      isLike: false,
    };
  },
  computed: {
    price() {
      return this.count * this.item.curPrice;
    },
    category() {
      if (this.item.product.category == 1) {
        return "유통임박 상품";
      } else if (this.item.product.category == 2) {
        return "못난이 농산물";
      }
      return "리퍼브 상품";
    },
    dday() {
      if (this.item.dday >= 0) {
        return "D-" + this.item.dday;
      } else {
        return "경매가 마감되었습니다.";
      }
    },
  },
  filters: {
    comma(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
  methods: {
    goCategory() {
      if (this.item.product.category == 1) {
        this.$router.push({ name: "Expire" });
      } else if (this.item.product.category == 2) {
        this.$router.push({ name: "Uglyfood" });
      }
      this.$router.push({ name: "Refurb" });
    },
    goMain() {
      this.$router.push({ name: "Home" });
    },
    reserve() {},
    buy() {
      if (this.count == 0 || this.item.product.status != 0) return;
      this.$axios({
        url: "/buy/" + this.item.product.id,
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          price: this.price,
          count: this.count,
          memberId: this.$store.getters["userStore/id"],
          product: this.item.product,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.$store.dispatch("userStore/buy", this.price);
            alert("구매에 성공하셨습니다.");
          } else {
            alert(response.data.message);
          }
          window.location.reload();
        })
        .catch((error) => {
          console.error(error);
        });
    },
    addCount() {
      if (this.item.product.status != 0) return;
      if (this.count == this.item.product.stock) return;
      this.count += 1;
    },
    subCount() {
      if (this.item.product.status != 0) return;
      if (this.count == 0) return;
      this.count -= 1;
    },
    likeCheck() {
      this.$axios({
        url: "/bookmark",
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          memberId: this.$store.getters["userStore/id"],
          productId: this.item.product.id,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.isLike = true;
          } else {
            window.location.reload();
          }
        })
        .catch((error) => {
          console.error(error);
        });
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
            this.isLike = false;
          } else {
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

<style>
.productDetailTitle,
.productDetailRate,
.productDetailCur,
.productDetailPrice,
.productDetailDday {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.productDetailInfoHeader,
.productDetailMax,
.productDetailCountName,
.productDetailCount,
.productDetailRemain,
.productDetailPriceName,
.productDetailEndDate {
  font-family: "NEXON Lv1 Gothic OTF";
}
.productDetailInfo {
  display: flex;
  flex-direction: row;
  margin-bottom: 5%;
}
.productDetailInfoImg {
  float: left;
  width: 60%;
  padding-top: 3%;
  object-fit: cover;
}
.productDetailInfoBody {
  margin-left: 5%;
  width: 40%;
}
.productDetailInfoHeader {
  font-size: 13px;
  border-bottom: solid 1px lightgray;
  margin-bottom: 5%;
}
.productDetailInfoCategory {
  padding-right: 2%;
  border-right: 1px solid gray;
  cursor: pointer;
}
.productDetailInfoSearch {
  padding-left: 1%;
  cursor: pointer;
}
.productDetailEndDate {
  float: right;
}
.productDetailDday {
  font-size: 38px;
  color: red;
}
.productDetailTitle {
  font-size: 30px;
  margin-top: 1%;
}
.productDetailRemain {
  font-size: 15px;
  color: gray;
  margin-bottom: 8%;
}
.productPriceDetail {
  margin-bottom: 10%;
}
.productDetailRate {
  font-size: 52px;
  float: left;
  color: red;
  padding-right: 15px;
}
.productDetailMax {
  font-size: 17px;
  color: gray;
  text-decoration: line-through;
}
.productLikeCheck {
  float: right;
  cursor: pointer;
}
.productDetailCur {
  font-size: 30px;
  padding-bottom: 10px;
}
.productDetailCountName {
  font-size: 20px;
  border-bottom: solid 1px gray;
}
.productDetailCount {
  margin-top: 3%;
  margin-bottom: 10%;
  align-items: center;
  float: right;
  width: 40%;
}
.countButtonLeft {
  float: left;
}
.countButtonRight {
  float: right;
}
.countNumber {
  font-size: 25px;
  text-align: center;
  padding-left: 9%;
}
.productDetailPriceName {
  clear: both;
  font-size: 20px;
  border-bottom: solid 1px gray;
}
.productDetailPrice {
  margin-top: 3%;
  margin-bottom: 15%;
  font-size: 30px;
  float: right;
}
.productDetailButton {
  clear: both;
}
.productDetailReserveButton {
  font-size: 20px;
  font-weight: bold;
  float: left;
  text-align: center;
  padding-top: 2%;
  padding-bottom: 2%;
  border: 2px solid black;
  width: 50%;
  cursor: pointer;
}
.productDetailBuyButton {
  font-size: 20px;
  font-weight: bold;
  float: right;
  text-align: center;
  padding-top: 2%;
  padding-bottom: 2%;
  background-color: #abf200;
  border: 2px solid #abf200;
  width: 50%;
  cursor: pointer;
}
</style>
