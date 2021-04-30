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
            src="@/assets/google.png"
            alt="구글로그인"
            @click="loginWithGoogle"
          />
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
import firebase from "firebase";
import "@firebase/auth";
import "@firebase/firestore";

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
            localStorage.setItem("isSocial", false);
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
    loginWithGoogle: function () {
      const provider = new firebase.auth.GoogleAuthProvider();
      firebase.auth().languageCode = "ko";
      firebase
        .auth()
        .signInWithPopup(provider)
        .then((result) => {
          this.$axios({
            url: "/member/social",
            method: "POST",
            data: {
              email: result.user.email,
              nickname: result.user.displayName,
              password: null,
              address: null,
              phone: null,
              point: 0,
            },
          })
            .then((response) => {
              if (response.data.success === "success") {
                localStorage.setItem("token", response.data["x-access-token"]);
                localStorage.setItem("isSocial", true);
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
                  localStorage.setItem("isSocial", true);
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

<style>
.login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  margin-top: 52px;
}
.loginTitle {
  width: 600px;
  float: left;
  font-size: 30px;
}
.loginInput {
  width: 500px;
  align-items: center;
  margin: 0px;
}
.border {
  border: solid 2px;
  border-color: chartreuse;
  border-style: inset;
  padding: 40px;
}
.loginButton {
  width: 25%;
  margin-top: 5px;
  font-weight: bold;
  font-size: 20px;
  border-radius: 8px;
  float: right;
}
.active {
  background: linear-gradient(
    109.63deg,
    rgb(200, 245, 192) 1.79%,
    rgb(4, 195, 4) 101.38%
  );
}
.divider {
  font-size: 10px;
}
.social {
  margin-top: 90px;
}
.socialTitle {
  padding: 0 0 1px 0;
  border-bottom: solid 1px;
  font-size: 18px;
}
.error {
  position: absolute;
  right: 30px;
  bottom: 50px;
}
.socialButton {
  padding-top: 10px;
  width: 10%;
  height: 10%;
  margin: 15px;
  cursor: pointer;
  float: right;
}
.loginJoin {
  float: right;
}
</style>
