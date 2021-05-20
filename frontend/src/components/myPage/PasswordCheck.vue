<template>
  <div class="passwordCheck">
    <div class="passwordCheckTitle">비밀번호 변경</div>
    <div class="passwordCheckBody">
      <div class="passwordCheckText">현재 비밀번호를 입력해주세요.</div>
      <div>
        <v-form onsubmit="return false">
          <div>
            <v-text-field
              class="inputField"
              v-model="password"
              :rules="passwordRule"
              label="현재 비밀번호"
              type="password"
              required
              @keyup.enter="checkPassword()"
            ></v-text-field>
          </div>
        </v-form>
      </div>
    </div>
    <v-btn class="passwordCheckButton" @click="checkPassword" color="#abf200"
      >확인</v-btn
    >
  </div>
</template>

<script>
export default {
  data() {
    return {
      password: "",
      passwordRule: [
        (value) =>
          /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/.test(value) || "",
      ],
    };
  },
  methods: {
    checkPassword() {
      const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
      if (!passwordRegex.test(this.password)) {
        return;
      }
      this.$axios({
        url: "/member/password",
        method: "POST",
        headers: { "x-access-token": localStorage.getItem("token") },
        data: {
          email: this.$store.getters["userStore/email"],
          password: this.password,
        },
      })
        .then((response) => {
          if (response.data.success === "success") {
            alert("비밀번호를 확인했습니다.");
            this.change();
            this.password = "";
          } else {
            alert("비밀번호가 다릅니다. 다시 입력해주세요.");
          }
        })
        .catch((error) => {
          console.error(error);
          alert("입력 정보를 다시 확인해주세요.");
        });
    },
    change() {
      this.$emit("change");
    },
  },
};
</script>

<style src="@/assets/css/user/myPage/PasswordCheck.css" scoped></style>
