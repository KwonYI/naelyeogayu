<template>
  <div class="deleteUser">
    <div class="deleteUserTitle">회원 탈퇴</div>
    <div class="deleteUserBody">
      <div class="deleteUserText">
        탈퇴를 원하신다면 비밀번호를 입력해주시기 바랍니다.
      </div>
      <v-form>
        <v-text-field
          v-model="password"
          ref="password"
          label="비밀번호"
          required
          :append-icon="showText ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showText ? 'text' : 'password'"
          @click:append="showText = !showText"
        ></v-text-field>
      </v-form>
    </div>
    <v-btn class="deleteButton" @click="deleteUser()" color="red" elevation="10"
      >탈퇴</v-btn
    >
    <v-btn
      class="deleteButton"
      @click="goMyPage()"
      color="#abf200"
      elevation="10"
      >취소</v-btn
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      password: "",
      showText: false,
      passwordRule: [
        (value) =>
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/.test(value) ||
          "소문자(a-z)와 대문자(A-Z), 숫자(0-9)를 모두 포함하고, 8자 이상이어야 합니다.",
      ],
    };
  },
  methods: {
    deleteUser: function () {
      const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
      if (!passwordRegex.test(this.password)) {
        this.$refs.password.focus();
        return;
      }
      this.$axios({
        url: "/member/delete",
        method: "DELETE",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          email: this.user.email,
          password: this.password,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert(
              "회원탈퇴에 성공했습니다. 저희 사이트를 이용해주셔서 감사합니다."
            );
            localStorage.removeItem("token");
            this.$store.dispatch("userStore/logout");
            this.$router.push({ name: "Home" });
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
    goMyPage: function () {
      this.$router.push({ name: "MyPage" });
    },
  },
};
</script>

<style>
.deleteUser {
  margin-left: 25%;
}
.deleteUserTitle {
  font-weight: bold;
  font-size: 25px;
  padding-top: 9px;
  border-bottom: solid 2px green;
  clear: both;
}
.deleteUserBody {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.deleteUserText {
  padding-top: 5%;
  padding-bottom: 2%;
  font-size: 24px;
  font-weight: bold;
}
.deleteButton {
  float: right;
  margin-right: 15px;
}
</style>
