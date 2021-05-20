<template>
  <div class="myInfo">
    <div class="myInfoTitle">개인정보관리</div>
    <v-form class="ModifyInput">
      <div class="myInfoTable">
        <div class="myInfoRow">
          <div class="myInfoCell col1">
            <div class="myInfoItem">E-Mail</div>
          </div>
          <div class="myInfoCell col2">
            <div class="myInfoInfo">{{ user.email }}</div>
          </div>
        </div>
        <div class="myInfoRow">
          <div class="myInfoCell col1">
            <div class="myInfoItem">닉네임</div>
          </div>
          <div class="myInfoCell col2" v-if="!isModify">
            <div class="myInfoInfo">{{ user.nickname }}</div>
          </div>
          <div class="myInfoCell col2" v-else>
            <v-text-field
              class="inputField"
              v-model="nickname"
              :rules="nameRule"
              ref="nickname"
              label="닉네임"
              autocapitalize="off"
            ></v-text-field>
          </div>
        </div>
        <div class="myInfoRow">
          <div class="myInfoCell col1">
            <div class="myInfoItem">주소</div>
          </div>
          <div class="myInfoCell col2" v-if="!isModify">
            <div class="myInfoInfo">{{ user.address }}</div>
          </div>
          <div class="myInfoCell col2" v-if="isModify">
            <v-text-field
              class="inputField"
              v-model="address"
              label="주소"
              readonly
            ></v-text-field>
          </div>
          <div class="myInfoCell col3" v-if="isModify">
            <v-btn @click="findAddress()">검색</v-btn>
          </div>
        </div>
        <div class="myInfoRow" v-if="isModify">
          <div class="myInfoCell col1">
            <div class="myInfoItem">상세주소</div>
          </div>
          <div class="myInfoCell col3">
            <v-text-field
              class="inputField"
              v-model="addressDetail"
              label="상세주소"
            ></v-text-field>
          </div>
        </div>
        <div class="myInfoRow">
          <div class="myInfoCell col1">
            <div class="myInfoItem">핸드폰</div>
          </div>
          <div class="myInfoCell col2" v-if="!isModify">
            <div class="myInfoInfo">{{ user.phone }}</div>
          </div>
          <div class="myInfoCell col2" v-else>
            <v-text-field
              class="inputField"
              v-model="phone"
              :rules="phoneRule"
              ref="phone"
              label="핸드폰 번호"
            ></v-text-field>
          </div>
        </div>
        <div class="myInfoRow">
          <div class="myInfoCell col1">
            <div class="myInfoItem">포인트</div>
          </div>
          <div class="myInfoCell col2">
            <span class="myInfoInfo">{{ user.point }} 포인트</span>
            <v-dialog class="modal" v-model="chargeModal" max-width="600px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="myInfoPointCharge"
                  color="#fced14"
                  v-bind="attrs"
                  v-on="on"
                  >충전</v-btn
                >
              </template>
              <v-card>
                <v-card-title>
                  <span class="modalHeader">포인트 충전</span>
                </v-card-title>
                <v-card-text>
                  <v-container class="modalForm">
                    <div class="modalInput">
                      <v-select
                        :items="chargePointSelect"
                        v-model="chargePointCount"
                        label="포인트"
                      ></v-select>
                    </div>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <div class="modalButton cancel" @click="chargeModal = false">
                    취소
                  </div>
                  <div class="modalButton pass" @click="chargePoint">충전</div>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
        </div>
      </div>
    </v-form>
    <v-btn
      class="modifyButton"
      @click="changeModify"
      v-if="!isModify"
      color="#abf200"
      >수정</v-btn
    >
    <v-btn
      class="modifyButton"
      @click="isModify = !isModify"
      v-if="isModify"
      color="red"
      >취소</v-btn
    >
    <v-btn
      class="modifyButton"
      @click="modify()"
      v-if="isModify"
      color="#abf200"
      >변경</v-btn
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      isModify: "",
      nickname: "",
      address: "",
      addressDetail: "",
      phone: "",
      chargeModal: false,
      chargePointCount: 0,
      nameRule: [
        (value) =>
          /^[가-핳a-zA-Z]{3,8}$/.test(value) ||
          "한글 혹은 영문으로 3자이상 8자이하여야 합니다.",
      ],
      passwordRule: [
        (value) =>
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/.test(value) ||
          "소문자(a-z)와 대문자(A-Z), 숫자(0-9)를 모두 포함하고, 8자 이상이어야 합니다.",
      ],
      passwordConfirmationRule: [
        (value) => this.password === value || "비밀번호가 같지 않습니다.",
      ],
      phoneRule: [
        (value) =>
          /^\d{3}-\d{3,4}-\d{4}$/.test(value) ||
          "휴대폰 번호는 01x-xxxx-xxxx형태로 입력해주셔야 합니다.",
      ],
      chargePointSelect: [1000, 2000, 5000, 10000, 20000, 30000, 50000, 100000],
    };
  },
  computed: {
    isLogined() {
      return this.$store.getters["userStore/isLogined"];
    },
    user() {
      return this.$store.getters["userStore/user"];
    },
  },
  methods: {
    changeModify() {
      this.isModify = true;
      this.nickname = this.user.nickname;
      this.address = this.user.address;
      this.phone = this.user.phone;
    },
    modify() {
      const nameRegex = /^[가-핳a-zA-Z]{3,8}$/;
      const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;

      if (this.nickname == "" && this.address == "" && this.phone == "") {
        this.isModify = false;
        return;
      }
      if (this.nickname == "") this.nickname = this.user.nickname;
      else if (!nameRegex.test(this.nickname)) {
        this.$refs.nickname.focus();
        return;
      }
      if (this.address == "") this.address = this.user.address;
      if (this.phone == "") this.phone = this.user.phone;
      else if (!phoneRegex.test(this.phone)) {
        this.$refs.phone.focus();
        return;
      }
      this.$axios({
        url: "/member/modify",
        method: "PUT",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          email: this.user.email,
          nickname: this.nickname,
          address: this.address + " " + this.addressDetail,
          phone: this.phone,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("개인정보를 변경했습니다.");
            localStorage.setItem("token", response.data["x-access-token"]);
            this.$store.dispatch(
              "userStore/login",
              response.data["x-access-token"]
            );
            this.isModify = false;
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
    findAddress() {
      new window.daum.Postcode({
        onComplete: (data) => {
          if (data.userSelectedType === "R") {
            this.address = data.roadAddress;
          } else {
            this.address = data.jibunAddress;
          }
        },
      }).open();
    },
    chargePoint() {
      if (this.chargePointCount == 0 || this.chargePointCount < 1000) return;
      this.$axios({
        url: "/member/ready",
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          email: this.user.email,
          point: this.chargePointCount,
        },
      })
        .then((response) => {
          location.href = response.data.path;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    let pg_token = this.$route.query.pg_token;
    if (pg_token === "fail") {
      alert("결제에 실패하셨습니다!");
      this.$router.push("/myPage");
    } else if (pg_token === "cancel") {
      alert("결제를 취소하셨습니다!");
      this.$router.push("/myPage");
    } else if (typeof pg_token !== "undefined") {
      this.$axios({
        url: "/member/approve",
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          email: this.user.email,
          pg_token: pg_token,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            localStorage.setItem("token", response.data["x-access-token"]);
            this.$store.dispatch(
              "userStore/login",
              response.data["x-access-token"]
            );
            this.$router.push("/myPage");
          } else {
            alert("결제에 실패하셨습니다!");
            this.$router.push("/myPage");
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
  },
};
</script>

<style src="@/assets/css/user/myPage/MyInfo.css" scoped></style>
