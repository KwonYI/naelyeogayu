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
            <div class="myInfoInfo">{{ user.point }} 포인트</div>
          </div>
        </div>
      </div>
    </v-form>
    <v-btn
      class="modifyButton"
      @click="isModify = !isModify"
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
      point: "",
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
    modify: function () {
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
      console.log(localStorage.getItem("token"));
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
            this.isModify = false;
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
    findAddress: function () {
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
  },
};
</script>

<style>
.myInfo {
  margin-left: 25%;
}
.myInfoTitle {
  font-weight: bold;
  font-size: 25px;
  padding-top: 9px;
  border-bottom: solid 2px green;
}
.myInfoTable {
  display: table;
  width: 100%;
}
.myInfoRow {
  display: table-row;
}
.myInfoCell {
  display: table-cell;
  padding: 50px 0px 0px 80px;
}
.myInfoItem {
  font-weight: bold;
  font-size: 28px;
  background: linear-gradient(to top, #abf200 50%, transparent 50%);
}
.myInfoInfo {
  font-size: 20px;
}
.col1 {
  width: 20%;
}
.col2 {
  width: 60%;
}
.col3 {
  width: 10%;
}
.modifyButton {
  padding-top: 50px;
  margin-right: 2%;
  max-width: 40px;
}
</style>
