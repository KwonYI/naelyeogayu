<template>
  <div class="login">
    <div class="loginTitle">로그인</div>
    <div class="border">
      <v-form class="loginInput">
        <v-container>
          <v-row>
            <v-text-field
              v-model="email"
              label="이메일"
              required
            ></v-text-field>
          </v-row>
          <v-row>
            <v-text-field
              v-model="password"
              label="비밀번호"
              required
              :append-icon="showText ? 'mdi-eye' : 'mdi-eye-off'"
              :type="showText ? 'text' : 'password'"
              @click:append="showText = !showText"
            ></v-text-field>
          </v-row>
        </v-container>
      </v-form>

      <v-btn
        :class="{ active: isActive, loginButton: 'loginButton' }"
        height="45"
        @click="login"
        :disabled="!isActive"
        >로그인</v-btn
      >
      <v-alert :value="showError" type="error" class="error">
        {{ errorMessage }}
      </v-alert>

      <v-divider class="divider"></v-divider>

      <div class="social">
        <div class="socialTitle">다른 방법으로 로그인</div>
        <v-divider class="divider"></v-divider>
        <div class="socialLogin">
          <img
            class="socialButton"
            src="@/assets/kakao.png"
            alt="카카오로그인"
            @click="loginWithKakao"
          />
        </div>
        <div class="loginJoin">
          혹시 아이디가 없으시다면, <a @click="goJoin">회원가입</a>을
          클릭해주세요.
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: "",
      password: "",
      showText: false, // 비밀번호 보여줄지 말지 결정
      isActive: false, // 로그인 버튼 활성화 비활성화 결정
      showError: false,
      errorMessage: "",
    };
  },
  watch: {
    // email과 password값이 바뀔때마다 함수 실행
    email: function () {
      this.buttonActive();
    },
    password: function () {
      this.buttonActive();
    },
  },
  methods: {
    buttonActive: function () {
      // email과 password를 모두 입력했을 때 로그인 버튼 활성화
      if (this.email !== "" && this.password !== "") {
        this.isActive = true;
      } else this.isActive = false;
    },
    login: function () {
      this.$axios({
        url: "/member/login",
        method: "POST",
        data: {
          email: this.email,
          password: this.password,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            localStorage.setItem("token", response.data["x-access-token"]);
            localStorage.setItem("isSocial", JSON.stringify(false));
            this.$store.dispatch(
              "userStore/login",
              response.data["x-access-token"]
            );
            // 로그인 성공시 메인 페이지로 분기
            this.$router.push({ name: "Home" });
          } else {
            this.showError = true;
            this.errorMessage = "로그인에 실패했습니다.";
            setTimeout(() => {
              this.showError = false;
            }, 5000);
          }
        })
        .catch((error) => {
          console.error(error);
          alert("이메일과 비밀번호를 확인해주세요.");
        });
    },
    loginWithKakao: function () {
      window.Kakao.Auth.login({
        scope: "profile, account_email",
        success: this.getInfoWithKaKao,
      });
    },
    getInfoWithKaKao: function () {
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: async (res) => {
          const kakaoAccount = res.kakao_account;
          if (kakaoAccount.email === null) {
            alert("저희 사이트는 이메일 제공에 동의해주셔야 사용가능합니다.");
          } else {
            this.$axios({
              url: "/member/social",
              method: "POST",
              data: {
                email: kakaoAccount.email,
                nickname: kakaoAccount.profile.nickname,
                password: null,
                address: null,
                phone: null,
                point: 0,
              },
            })
              .then((response) => {
                if (response.data.success === "success") {
                  localStorage.setItem(
                    "token",
                    response.data["x-access-token"]
                  );
                  localStorage.setItem("isSocial", JSON.stringify(true));
                  this.$store.dispatch(
                    "userStore/login",
                    response.data["x-access-token"]
                  );
                  if (response.data.first === "first") {
                    this.$router.push({ name: "SocialJoin" });
                  }
                  this.$router.push({ name: "Home" });
                }
              })
              .catch((error) => {
                alert("로그인에 실패했습니다.");
                console.error(error);
              });
          }
        },
      });
    },
    goJoin: function () {
      this.$router.push({ name: "Join" });
    },
  },
};
</script>

<style src="@/assets/css/user/Login.css" scoped></style>
