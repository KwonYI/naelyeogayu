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
          :rules="passwordRule"
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
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/.test(value) || "",
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
          id: this.$store.getters["userStore/id"],
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

<style src="@/assets/css/user/myPage/Buy.css" scoped></style>
