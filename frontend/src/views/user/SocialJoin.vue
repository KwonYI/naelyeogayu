<template>
  <div class="join">
    <div class="joinTitle">추가 정보 입력</div>
    <div class="border">
      <v-form class="joinInput">
        <v-container>
          <div>
            <v-text-field
              class="inputField"
              v-model="email"
              :rules="emailRule"
              label="이메일"
              autocapitalize="off"
              required
              disabled
            ></v-text-field>
          </div>
          <div class="clear">
            <v-text-field
              class="inputField"
              v-model="nickname"
              :rules="nameRule"
              label="닉네임"
              autocapitalize="off"
              required
            ></v-text-field>
          </div>
          <div>
            <v-text-field
              class="inputField"
              v-model="password"
              :rules="passwordRule"
              label="비밀번호"
              type="password"
              required
            ></v-text-field>
          </div>
          <div>
            <v-text-field
              class="inputField"
              v-model="passwordConfirm"
              :rules="passwordConfirmationRule"
              label="비밀번호 확인"
              type="password"
              required
            ></v-text-field>
          </div>
          <div>
            <v-text-field
              class="inputField"
              v-model="address"
              label="주소"
              readonly
              required
            ></v-text-field>
          </div>
          <div>
            <v-btn class="addressFindButton" @click="findAddress()">검색</v-btn>
          </div>
          <div class="clear">
            <v-text-field
              class="inputField"
              v-model="addressDetail"
              label="상세주소"
              required
            ></v-text-field>
          </div>
          <div>
            <v-text-field
              class="inputField"
              v-model="phone"
              :rules="phoneRule"
              label="핸드폰 번호"
              required
            ></v-text-field>
          </div>
        </v-container>
      </v-form>
      <v-btn
        @click="modify()"
        :class="{ active: isActive, modifyButton: 'modifyButton' }"
        :disabled="!isActive"
      >
        추가 정보 입력
      </v-btn>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      email: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      address: "",
      addressDetail: "",
      phone: "",
      isActive: false,
      isValid: false,
      isDupActive: false,
      emailRule: [
        (value) =>
          /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(value) ||
          "이메일 형식에 맞춰 작성해주세요.",
      ],
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
      showError: false,
      errorMessage: "",
    };
  },
  watch: {
    email: function () {
      this.isValid = false;
      this.validateCheck();
      this.emailFilledCheck();
    },
    nickname: function () {
      this.validateCheck();
    },
    password: function () {
      this.validateCheck();
    },
    passwordConfirm: function () {
      this.validateCheck();
    },
    address: function () {
      this.validateCheck();
    },
    phone: function () {
      this.validateCheck();
    },
  },
  methods: {
    validateCheck: function () {
      const nameRegex = /^[가-핳a-zA-Z]{3,8}$/;
      const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
      const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;

      if (!nameRegex.test(this.nickname)) {
        this.isActive = false;
      } else if (!passwordRegex.test(this.password)) {
        this.isActive = false;
      } else if (this.password !== this.passwordConfirm) {
        this.isActive = false;
      } else if (this.address === "") {
        this.isActive = false;
      } else if (!phoneRegex.test(this.phone)) {
        this.isActive = false;
      } else this.isActive = true;
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
    modify: function () {
      if (!this.isActive || !this.isValid) {
        return;
      }
      this.$axios({
        url: "/member/modify",
        method: "PUT",
        data: {
          email: this.email,
          password: this.password,
          nickname: this.nickname,
          address: this.address + " " + this.addressDetail,
          phone: this.phone,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            this.$router.push({ name: "Home" });
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
  },
  created() {
    this.email = this.$store.getters["userStore/id"];
    this.nickname = this.$store.getters["userStore/nickname"];
  },
};
</script>

<style src="@/assets/css/user/MyPage.css" scoped></style>
