<template>
  <div class="productReview">
    <div class="productReviewBody">
      <div class="productReviewWrite" v-if="verify == true && isWrite == false">
        <v-textarea
          v-model="descript"
          ref="descript"
          placeholder="후기를 입력하세요."
          @keyup.enter="writeReview"
        >
        </v-textarea>
      </div>
      <div class="productReviewList">
        <div v-for="(item, index) in list" :item="item" :key="index">
          <div
            border-variant="info"
            class="row"
            no-body
            style="
              margin-bottom: 10px;
              margin-left: 0px;
              box-shadow: 5px 5px 5px #cccccc;
              min-height: 80px;
              border-radius: 5px;
            "
          >
            <div class="col" style="padding: 0px">
              <div class="text-left">
                <div
                  class="d-flex align-items-center"
                  style="margin-top: 10px; padding: 0"
                >
                  <strong style="margin-right: 10px">
                    {{ item.member.nickname }}
                  </strong>
                  <small>({{ item.date }})</small>
                  <div v-if="memberId == item.member.id">
                    <button
                      style="margin-left: 10px"
                      @click="modifyClick(item.descript)"
                      variant="link"
                    >
                      수정
                    </button>
                    <button
                      style="margin-left: 10px"
                      @click="deleteReview()"
                      variant="link"
                    >
                      삭제
                    </button>
                  </div>
                </div>
              </div>
              <hr style="margin: 0px" />
              <div class="text-left">
                <div
                  id="viewcomment"
                  v-show="verify"
                  v-if="!(memberId == item.member.id && isModify == true)"
                  v-html="item.descript.replace(/(?:\r\n|\r|\n)/g, '<br>')"
                  style="
                    margin-top: 10px;
                    margin-right: 30px;
                    margin-left: 0px;
                    margin-bottom: 10px;
                    font-size: 10pt;
                  "
                >
                  {{ item.descript }}
                </div>
                <div
                  id="modifyinput"
                  v-if="memberId == item.member.id && isModify == true"
                >
                  <form @submit="modifyReview">
                    <div class="m3 mt-2">
                      <div cols="11">
                        <v-textarea
                          id="modicontents"
                          v-model="modicontents"
                          placeholder="내용을 입력하세요."
                          rows="2"
                          @keyup.enter="modifyReview"
                        >
                        </v-textarea>
                      </div>
                      <div class="p-0">
                        <button type="submit" variant="dark">수정</button>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
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
      list: [],
      contents: "",
      modicontents: "",
      descript: "",
      memberId: this.$store.getters["userStore/id"],
      verify: false,
      review: {},
      isModify: false,
      isWrite: false,
    };
  },
  created() {
    this.getReviewList();
    this.verifyBuy();
  },
  methods: {
    modifyClick(descript) {
      this.modicontents = descript;
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
          } else {
            alert("상품 후기를 받아오는데 실패했습니다.");
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
      console.log(this.memberId);
      this.$axios({
        url: "/review/" + this.memberId,
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          descript: this.descript,
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
    modifyReview(event) {
      event.preventDefault();
      if (this.modicontents == "" || this.modicontents.trim() == "") {
        alert("작성된 내용이 존재하지 않습니다.");
      } else {
        this.$axios({
          url: "/review",
          method: "PUT",
          headers: { "x-access-token": localStorage.getItem("token") },
          data: {
            id: this.review.id,
            descript: this.modicontents,
          },
        })
          .then((response) => {
            if (response.data.success) {
              this.$router.go(this.$router.currentRoute);
            } else {
              alert("수정에 실패했습니다.");
            }
          })
          .catch((err) => {
            console.log(err);
            alert("수정에 실패했습니다.");
          });
      }
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

<style></style>
