<template>
  <div class="join">
    <div class="joinTitle">회원가입</div>
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
              @keyup.enter="signUp()"
            ></v-text-field>
          </div>
          <div>
            <v-btn
              class="emailCheckButton"
              @click="emailCheck()"
              :disabled="!isDupActive"
              >중복확인</v-btn
            >
          </div>
          <div class="clear">
            <v-text-field
              class="inputField"
              v-model="nickname"
              :rules="nameRule"
              label="닉네임"
              autocapitalize="off"
              required
              @keyup.enter="signUp()"
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
              @keyup.enter="signUp()"
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
              @keyup.enter="signUp()"
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
              @keyup.enter="signUp()"
            ></v-text-field>
          </div>
          <div>
            <v-text-field
              class="inputField"
              v-model="phone"
              :rules="phoneRule"
              label="핸드폰 번호"
              required
              @keyup.enter="signUp()"
            ></v-text-field>
          </div>
        </v-container>
      </v-form>
      <v-btn
        @click="signUp()"
        :class="{ active: isActive, signUpButton: 'signUpButton' }"
        :disabled="!isActive"
      >
        회원가입
      </v-btn>
      <v-alert :value="showError" type="error" class="error">
        {{ errorMessage }}
      </v-alert>
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
      const emailRegex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
      const nameRegex = /^[가-핳a-zA-Z]{3,8}$/;
      const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
      const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;

      if (!emailRegex.test(this.email)) {
        this.isActive = false;
      } else if (!nameRegex.test(this.nickname)) {
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
    emailFilledCheck: function () {
      const emailRegex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/;
      if (emailRegex.test(this.email)) {
        this.isDupActive = true;
      } else {
        this.isDupActive = false;
      }
    },
    emailCheck: function () {
      this.$axios({
        url: "/member/certify/" + this.email,
        method: "GET",
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("사용가능한 이메일입니다.");
            this.isValid = true;
          } else {
            alert("이미 등록된 이메일입니다.");
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
    signUp: function () {
      if (!this.isActive || !this.isValid) {
        return;
      }
      this.$axios({
        url: "/member/signup",
        method: "POST",
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
            alert("회원가입에 성공했습니다.");
            this.$router.push({ name: "Login" });
          } else {
            this.showError = true;
            this.errorMessage = "회원가입에 실패했습니다.";
            setTimeout(() => {
              this.showError = false;
            }, 5000);
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
  },
};
</script>

<style src="@/assets/css/user/Join.css" scoped></style>
