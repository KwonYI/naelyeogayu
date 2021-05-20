<template>
  <div class="sideBar">
    <v-navigation-drawer permanent>
      <v-list-item>
        <v-list-item-content class="divider">
          <v-list-item-title class="sideBarTitle">
            마이페이지
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list nav>
        <v-list-item link @click="move(0)">
          <v-list-item-icon>
            <v-icon>mdi-text-box-multiple</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>전체관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="move(1)">
          <v-list-item-icon>
            <v-icon>mdi-account</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>개인정보관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link v-if="!isSocial" @click="move(2)">
          <v-list-item-icon>
            <v-icon>mdi-lock</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>비밀번호 변경</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="move(7)">
          <v-list-item-icon>
            <v-icon>mdi-star</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>내가 찜한 목록</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="move(3)">
          <v-list-item-icon>
            <v-icon>mdi-cart</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>내가 예약한 목록</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="move(4)">
          <v-list-item-icon>
            <v-icon>mdi-expand-all</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>내가 구매한 목록</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="move(5)">
          <v-list-item-icon>
            <v-icon>mdi-currency-usd</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>내가 판매한 목록</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link @click="move(6)">
          <v-list-item-icon>
            <v-icon>mdi-door-open</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>회원탈퇴</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
export default {
  methods: {
    move(num) {
      this.$emit("move", num);
    },
  },
  computed: {
    isSocial: function () {
      return JSON.parse(localStorage.getItem("isSocial"));
    },
  },
};
</script>

<style src="@/assets/css/user/myPage/SideBar.css" scoped></style>
