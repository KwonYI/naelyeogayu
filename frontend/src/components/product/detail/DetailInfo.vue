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
      <v-dialog class="modal" v-model="sellModal" max-width="600px">
        <template v-slot:activator="{ on: sell, attrs }">
          <div class="productSellInfo" v-bind="attrs" v-on="{ ...sell }">
            입찰 기록
          </div>
        </template>
        <v-card>
          <v-card-title>
            <span class="sellModalHeader">입찰 기록</span>
          </v-card-title>
          <v-card-text>
            <v-container v-if="sellInfoes.length != 0">
              <div class="sellModalTable">
                <colgroup>
                  <col width="70%" />
                  <col width="20%" />
                  <col width="10%" />
                </colgroup>
                <thead class="sellModalRow">
                  <th>날짜</th>
                  <th>가격</th>
                  <th>수량</th>
                </thead>
                <tbody>
                  <tr
                    class="sellModalRow"
                    v-for="(sellInfo, index) in sellInfoes"
                    v-bind:key="index"
                  >
                    <td>{{ sellInfo.buyDate | replace }}</td>
                    <td>{{ sellInfo.price }}</td>
                    <td>{{ sellInfo.count }}</td>
                  </tr>
                </tbody>
              </div>
            </v-container>
            <v-container v-else>
              <div class="noSellInfo">입찰 기록이 없습니다.</div>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <div class="modalButton" @click="sellModal = false">확인</div>
          </v-card-actions>
        </v-card>
      </v-dialog>
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
        <span class="productDetailRate">{{ item.discountRate | fixed }}% </span>
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
        <v-dialog
          class="modal"
          v-model="reserveModal"
          max-width="600px"
          v-if="!isReserved"
        >
          <template v-slot:activator="{ on: reserve, attrs }">
            <div
              class="productDetailReserveButton"
              v-bind="attrs"
              v-on="{ ...reserve }"
            >
              예약하기
            </div>
          </template>
          <v-card>
            <v-card-title>
              <span class="modalHeader">상품 예약</span>
            </v-card-title>
            <v-card-subtitle>
              <div class="modalSubHeader">수량과 가격을 설정해주세요.</div>
              <div class="modalSubHeader">
                예약 가격이 현재 가격보다 높을 경우, 바로 구매가 진행되니
                유의해주시길 바랍니다.
              </div>
            </v-card-subtitle>
            <v-card-text>
              <v-container class="modalForm">
                <div class="modalInput">
                  <v-select
                    :items="reserveCountSelect"
                    v-model="reserveCount"
                    label="수량"
                  ></v-select>
                </div>
                <div class="modalInput">
                  <v-text-field
                    v-model="reservePrice"
                    label="예약 가격"
                    type="number"
                  ></v-text-field>
                </div>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <div class="modalButton cancel" @click="closeModal">취소</div>
              <div
                :class="isFilled ? 'modalButton pass' : 'modalButtonDisabled'"
                @click="reserve"
              >
                예약
              </div>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <div
          class="productDetailReserveButton"
          v-if="isReserved"
          @click="cancelReserve"
        >
          예약취소
        </div>
        <div class="productDetailBuyButton" @click="buy" v-if="!isSeller">
          입찰하기
        </div>
        <div class="productDetailBuyButton" @click="cancelSell" v-if="isSeller">
          경매 취소
        </div>
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
      isFilled: false,
      isReserved: false,
      sellModal: false,
      reserveModal: false,
      reserveCount: 0,
      reservePrice: "",
      reserveCountSelect: [],
      sellInfoes: [],
    };
  },
  created() {
    for (var i = 1; i <= this.item.product.stock; i++) {
      this.reserveCountSelect.push(i);
    }
    this.likeStatus();
    this.reserveStatus();
    this.getSellInfo();
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
    isSeller() {
      if (this.$store.getters["userStore/id"] == this.item.product.sellerId) {
        return true;
      }
      return false;
    },
  },
  filters: {
    comma(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    fixed(rate) {
      return rate.toFixed(2);
    },
    replace(time) {
      return time.replace("T", " ");
    },
  },
  watch: {
    reserveCount() {
      this.checkForm();
    },
    reservePrice() {
      this.checkForm();
    },
  },
  methods: {
    goCategory() {
      if (this.item.product.category == 1) {
        this.$router.push({ name: "Expire" });
        return;
      } else if (this.item.product.category == 2) {
        this.$router.push({ name: "Uglyfood" });
        return;
      }
      this.$router.push({ name: "Refurb" });
    },
    goMain() {
      this.$router.push({ name: "Home" });
    },
    closeModal() {
      this.reserveModal = false;
      this.ReserveCount = 0;
      this.ReservePrice = 0;
    },
    checkForm() {
      if (this.reserveCount == 0 || this.reservePrice == "") {
        this.isFilled = false;
        return;
      }
      this.isFilled = true;
    },
    reserve() {
      if (!this.isFilled) {
        return;
      }
      if (
        this.item.product.status == 0 &&
        this.reservePrice >= this.item.curPrice
      ) {
        this.$axios({
          url: "/buy/" + this.item.product.id,
          method: "POST",
          headers: { "x-access-token": localStorage.getItem("token") },
          data: {
            price: this.reservePrice,
            count: this.reserveCount,
            memberId: this.$store.getters["userStore/id"],
            product: this.item.product,
          },
        })
          .then((response) => {
            if (response.data.success === "success") {
              this.$store.dispatch("userStore/buy", this.price);
              alert("구매에 성공하셨습니다.");
              this.reserveAxios(1);
              localStorage.setItem("token", response.data["x-access-token"]);
              this.$store.dispatch(
                "userStore/login",
                response.data["x-access-token"]
              );
            } else {
              this.reserveAxios(response.data.resultType);
            }
            window.location.reload();
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        this.reserveAxios(0);
      }
    },
    reserveAxios(status) {
      this.$axios({
        url: "/reserve/" + this.item.product.id,
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          memberId: this.$store.getters["userStore/id"],
          product: this.item.product,
          price: this.reservePrice,
          count: this.reserveCount,
          status: status,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("예약에 성공하셨습니다.");
          } else {
            alert("이미 예약했습니다.");
          }
        })
        .catch((error) => {
          console.error(error);
        });
      this.isReserved = true;
      this.reserveModal = false;
    },
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
            localStorage.setItem("token", response.data["x-access-token"]);
            this.$store.dispatch(
              "userStore/login",
              response.data["x-access-token"]
            );
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
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    likeStatus() {
      this.$axios({
        url:
          "/bookmark/list/" +
          this.$store.getters["userStore/id"] +
          "/" +
          this.item.product.id,
        method: "GET",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.isLike = true;
          } else {
            this.isLike = false;
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    reserveStatus() {
      this.$axios({
        url:
          "/reserve/my/" +
          this.$store.getters["userStore/id"] +
          "/" +
          this.item.product.id,
        method: "GET",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.isReserved = true;
          } else {
            this.isReserved = false;
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    getSellInfo() {
      this.$axios({
        url: "/buy/productlist/" + this.item.product.id,
        method: "GET",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.sellInfoes = response.data.data;
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
    cancelReserve() {
      if (confirm("예약을 취소하시겠습니까?")) {
        this.$axios({
          url: "/reserve",
          method: "DELETE",
          headers: {
            "x-access-token": localStorage.getItem("token"),
            memberId: this.$store.getters["userStore/id"],
            productId: this.item.product.id,
          },
        })
          .then((response) => {
            if (response.data.success === "success") {
              alert("예약이 취소되었습니다.");
              this.isReserved = false;
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
    cancelSell() {
      if (confirm("경매를 취소하시겠습니까?")) {
        this.$axios({
          url: "/product",
          method: "DELETE",
          headers: {
            "x-access-token": localStorage.getItem("token"),
            productId: this.item.product.id,
          },
        })
          .then((response) => {
            if (response.data.success === "success") {
              alert("경매가 취소되었습니다.");
              this.$router.push({ name: "Home" });
            }
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },
  },
};
</script>

<style>
.productDetailTitle,
.productDetailRate,
.productDetailCur,
.productDetailPrice,
.productDetailDday,
.modalHeader,
.productSellInfo,
.noSellInfo,
.sellModalHeader {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
.productDetailInfoHeader,
.productDetailMax,
.productDetailCountName,
.productDetailCount,
.productDetailRemain,
.productDetailPriceName,
.productDetailEndDate,
.modalSubHeader,
.modalButton,
.sellModalRow {
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
.productSellInfo {
  float: right;
  margin-top: 1%;
  font-size: 17px;
  padding: 2px 10px;
  border: 2px solid black;
  cursor: pointer;
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
.modalHeader {
  width: 100%;
  border-bottom: solid 2px green;
  margin-bottom: 4%;
}
.sellModalHeader {
  width: 100%;
  border-bottom: solid 2px green;
}
.sellModalTable {
  width: 100%;
  align-items: center;
  justify-content: center;
  border-spacing: 50px 10px;
}
.noSellInfo {
  font-size: 18px;
  padding-top: 3%;
  text-align: center;
}
.sellModalRow {
  font-size: 16px;
  text-align: center;
}
.modalInput {
  width: 50%;
  margin-left: auto;
  margin-right: auto;
}
.modalButton {
  float: right;
  border: 2px solid black;
  padding: 5px 15px;
  margin-right: 10px;
  text-align: center;
  cursor: pointer;
}
.modalButtonDisabled {
  float: right;
  border: 2px solid black;
  padding: 5px 15px;
  text-align: center;
  color: gray;
  background: lightgray;
  margin-right: 10px;
  opacity: 40%;
}
.cancel {
  background: rgb(226, 50, 50);
  color: white;
}
.pass {
  background: #abf200;
}
.modal {
  z-index: 10000;
}
</style>
