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
        <span class="productDetailInfoSearch" @click="goSearch">
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
        남은 수량 {{ item.product.stock }}{{ unit }}
        <span v-if="item.product.category == 2 && item.product.unit != 0"
          >/{{ item.product.unit }}kg</span
        >
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
      <div class="productDetailCountName">
        수량<br /><span v-if="item.product.status == 1" id="caution"
          >해당 상품은 판매 대기 상품으로 예약 또는 찜하기만 가능합니다.</span
        >
      </div>
      <div class="productDetailCount">
        <v-btn
          class="countButtonLeft"
          tile
          @click="addCount"
          disabled
          v-if="item.product.status == 1"
          ><v-icon>mdi-plus</v-icon></v-btn
        >
        <v-btn class="countButtonLeft" tile @click="addCount" v-else
          ><v-icon>mdi-plus</v-icon></v-btn
        >
        <span class="countNumber">{{ count }}</span>
        <v-btn
          class="countButtonRight"
          tile
          @click="subCount"
          disabled
          v-if="item.product.status == 1"
          ><v-icon>mdi-minus</v-icon></v-btn
        >
        <v-btn class="countButtonRight" tile @click="subCount" v-else
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
                <div class="modalInput">
                  <v-menu
                    ref="endDateMenu"
                    v-model="endDateMenu"
                    :close-on-content-click="false"
                    :return-value.sync="item.product.endDate"
                    transition="scale-transition"
                    offset-y
                    min-width="auto"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        v-model="reserveEndDate"
                        label="예약 마감일"
                        append-icon="mdi-calendar"
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      v-model="reserveEndDate"
                      :min="today"
                      :max="item.product.endDate"
                      no-title
                      scrollable
                    >
                      <v-spacer></v-spacer>
                      <v-btn text color="primary" @click="endDateMenu = false">
                        취소
                      </v-btn>
                      <v-btn
                        text
                        color="primary"
                        @click="$refs.endDateMenu.save(reserveEndDate)"
                      >
                        확인
                      </v-btn>
                    </v-date-picker>
                  </v-menu>
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
        <div
          class="productDetailBuyButton"
          @click="buy"
          v-if="!isSeller && !isClick"
        >
          입찰하기
        </div>
        <div class="productDetailBuyButton" v-if="!isSeller && isClick">
          <v-progress-circular
            indeterminate
            color="white"
          ></v-progress-circular>
        </div>
        <div class="productDetailBuyButton" @click="cancelSell" v-if="isSeller">
          판매 취소
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
  data() {
    return {
      count: 0,
      isLike: false,
      isClick: false,
      isFilled: false,
      isReserved: false,
      sellModal: false,
      reserveModal: false,
      endDateMenu: false,
      reserveCount: 0,
      reservePrice: "",
      reserveEndDate: "",
      reserveCountSelect: [],
      sellInfoes: [],
      search: "",
    };
  },
  created() {
    for (var i = 1; i <= this.item.product.stock; i++) {
      this.reserveCountSelect.push(i);
    }
    this.likeStatus();
    this.reserveStatus();
    this.getSellInfo();
    this.watchLog();
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
      if (this.item.dday > 0) {
        return "D-" + this.item.dday;
      } else if (this.item.dday == 0) {
        return "D-day";
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
    unit() {
      if (this.item.product.category == 2) {
        return "box";
      }
      return "개";
    },
    today() {
      return moment().format("YYYY-MM-DD");
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
    reserveEndDate() {
      this.checkForm();
    },
  },
  methods: {
    goCategory() {
      if (this.item.product.category == 1) {
        this.$router.push({ name: "Expire" });
      } else if (this.item.product.category == 2) {
        this.$router.push({ name: "Uglyfood" });
      } else {
        this.$router.push({ name: "Refurb" });
      }
    },
    goSearch() {
      this.search = this.item.product.name;
      this.$store.commit("productStore/setSearch", this.search);

      if (this.item.product.category == 1) {
        this.$router.push({ name: "Expire" });
      } else if (this.item.product.category == 2) {
        this.$router.push({ name: "Uglyfood" });
      } else {
        this.$router.push({ name: "Refurb" });
      }
    },
    closeModal() {
      this.reserveModal = false;
      this.ReserveCount = 0;
      this.ReservePrice = 0;
      this.reserveEndDate = "";
    },
    checkForm() {
      if (
        this.reserveCount == 0 ||
        this.reservePrice == "" ||
        this.reserveEndDate == ""
      ) {
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
              this.$store.dispatch("userStore/buy", this.item.curPrice);
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
          dueDate: this.reserveEndDate,
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
      this.isClick = true;
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
          this.isClick = false;
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
          url:
            "/reserve/" +
            this.$store.getters["userStore/id"] +
            "/" +
            this.item.product.id,
          method: "DELETE",
          headers: {
            "x-access-token": localStorage.getItem("token"),
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
          url: "/product/" + this.item.product.id,
          method: "DELETE",
          headers: {
            "x-access-token": localStorage.getItem("token"),
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
    watchLog() {
      this.$axios({
        url: "/watch",
        method: "POST",
        headers: {
          "x-access-token": localStorage.getItem("token"),
        },
        data: {
          productId: this.item.product.id,
          memberId: this.$store.getters["userStore/id"],
        },
      }).catch((error) => {
        console.error(error);
      });
    },
  },
};
</script>

<style src="@/assets/css/product/detail/DetailInfo.css" scoped></style>
