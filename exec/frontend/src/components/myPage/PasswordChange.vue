<template>
  <div class="passwordCheck">
    <div class="passwordCheckTitle">비밀번호 변경</div>
    <div class="passwordCheckBody">
      <div class="passwordCheckText">변경하실 비밀번호를 입력해주세요.</div>
      <div>
        <v-form onsubmit="return false">
          <div>
            <v-text-field
              class="inputField"
              v-model="password"
              :rules="passwordRule"
              label="변경할 비밀번호"
              type="password"
              required
              @keyup.enter="changePassword"
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
              @keyup.enter="changePassword"
            ></v-text-field>
          </div>
        </v-form>
      </div>
    </div>
    <v-btn class="passwordCheckButton" @click="changePassword" color="#abf200"
      >확인</v-btn
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      password: "",
      passwordConfirm: "",
      passwordRule: [
        (value) =>
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/.test(value) || "",
      ],
      passwordConfirmationRule: [(value) => this.password === value || ""],
    };
  },
  methods: {
    changePassword() {
      const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
      if (!passwordRegex.test(this.password)) {
        return;
      }
      if (this.password !== this.passwordConfirm) {
        return;
      }
      this.$axios({
        url: "/member/password",
        method: "PUT",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          email: this.$store.getters["userStore/email"],
          password: this.password,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("비밀번호를 변경했습니다.");
            this.complete();
            this.password = "";
            this.passwordConfirm = "";
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
    complete() {
      this.$emit("complete");
    },
  },
};
</script>

<style src="@/assets/css/user/myPage/PasswordChange.css" scoped></style>
