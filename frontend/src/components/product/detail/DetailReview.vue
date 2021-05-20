<template>
  <div class="productReview">
    <div class="productReviewBody">
      <div class="productReviewWrite" v-if="verify == true && isWrite == false">
        <v-text-field
          v-model="reviewContent"
          ref="reviewContent"
          placeholder="후기를 입력하세요."
          @keyup.enter="writeReview"
          @focus="isFocus = true"
        >
        </v-text-field>
        <div class="reviewWriteButton" v-if="isFocus">
          <v-btn @click="cancel">취소</v-btn>
          <v-btn @click="writeReview">작성</v-btn>
        </div>
      </div>
      <div
        class="productReviewList"
        v-for="(item, index) in list"
        :item="item"
        :key="index"
        v-show="list.length != 0"
      >
        <div class="productReviewHeader" style="padding: 0px">
          <span class="productReviewWriter">
            {{ item.member.nickname }}
          </span>
          <span class="productReviewDate"
            >{{ item.date | time }} ( {{ item.date | diff }} )</span
          >
          <div
            class="productReviewModifyButtons"
            v-if="memberId == item.member.id"
          >
            <div
              class="productReviewModifyButtonLeft"
              @click="modifyClick(item.descript)"
            >
              수정
            </div>
            <div class="productReviewModifyButtonRight" @click="deleteReview()">
              삭제
            </div>
          </div>
        </div>
        <div class="productReviewContent">
          <div
            class="productReview"
            v-if="memberId != item.member.id || isModify == false"
            v-html="item.descript.replace(/(?:\r\n|\r|\n)/g, '<br>')"
          ></div>
          <div
            id="modifyinput"
            v-if="memberId == item.member.id && isModify == true"
          >
            <div class="productReviewWrite">
              <v-text-field
                v-model="modifyContents"
                placeholder="내용을 입력하세요."
                @keyup.enter="modifyReview"
              >
              </v-text-field>
              <div class="reviewModifyButton">
                <div class="reviewModify" @click="modifyReview">수정</div>
                <div class="reviewModifyCancel" @click="isModify = false">
                  취소
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="productNoReview" v-show="list.length == 0">
        해당 상품의 리뷰가 없습니다.
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
      list: [],
      contents: "",
      modifycontents: "",
      reviewContent: "",
      memberId: this.$store.getters["userStore/id"],
      verify: false,
      review: {},
      isModify: false,
      isWrite: false,
      isFocus: false,
    };
  },
  created() {
    this.getReviewList();
    this.verifyBuy();
  },
  filters: {
    time(date) {
      return date.replace("T", " ");
    },
    diff(date) {
      return moment(date).fromNow();
    },
  },
  methods: {
    cancel() {
      this.reviewContent = "";
      this.isFocus = false;
    },
    modifyClick(descript) {
      this.modifyContents = descript;
      this.isModify = true;
    },
    getReviewList() {
      this.$axios({
        url: "/review/list/" + this.item.product.id,
        method: "GET",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.list = response.data.data;
            this.checkWrite();
          }
        })
        .catch((error) => {
          console.error(error);
          alert("상품 후기를 받아오는데 실패했습니다.");
        });
    },
    checkWrite() {
      for (var review in this.list) {
        if (this.memberId == this.list[review].member.id) {
          this.isWrite = true;
          this.review = this.list[review];
        }
      }
    },
    writeReview() {
      if (this.reviewContent == "" || this.reviewContent.trim() == "") {
        alert("작성된 내용이 존재하지 않습니다.");
        return;
      }
      this.$axios({
        url: "/review/" + this.memberId,
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          descript: this.reviewContent,
          productId: this.item.product.id,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            window.location.reload();
          }
        })
        .catch((error) => {
          console.error(error);
          alert("상품 후기를 등록에 실패했습니다.");
        });
    },
    deleteReview() {
      this.$axios({
        url: "/review/" + this.review.id,
        method: "DELETE",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            window.location.reload();
          }
        })
        .catch((error) => {
          console.error(error);
          alert("상품 후기 삭제에 실패했습니다.");
        });
    },
    modifyReview() {
      if (this.modifyContents == "" || this.modifyContents.trim() == "") {
        alert("작성된 내용이 존재하지 않습니다.");
        return;
      }
      this.$axios({
        url: "/review",
        method: "PUT",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          id: this.review.id,
          descript: this.modifyContents,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.$router.go(this.$router.currentRoute);
          } else {
            alert("수정에 실패했습니다.");
          }
        })
        .catch((error) => {
          console.log(error);
          alert("수정에 실패했습니다.");
        });
    },
    verifyBuy() {
      this.$axios({
        url:
          "/buy/verify/" +
          this.$store.getters["userStore/id"] +
          "/" +
          this.item.product.id,
        method: "GET",
        headers: { "x-access-token": localStorage.getItem("token") },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.verify = true;
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style src="@/assets/css/product/detail/DetailReview.css" scoped></style>
