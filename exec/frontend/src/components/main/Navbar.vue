<template>
  <div id="header">
    <div id="intro">
      <h1 id="logo">
        <router-link to="/" style="color: black; text-decoration: none">
          <img src="@/assets/logo.png" alt="logo" />
        </router-link>
      </h1>
      <div id="about">
        <router-link to="/about" style="color: black; text-decoration: none"
          >ABOUT</router-link
        >
      </div>
      <div id="join" v-show="!isLogined">
        <router-link to="/join" style="color: black; text-decoration: none"
          >회원가입</router-link
        >
      </div>
      <div id="login" v-show="!isLogined">
        <router-link to="/login" style="color: black; text-decoration: none"
          >로그인</router-link
        >
      </div>
      <div id="myPage" v-show="isLogined">
        <router-link to="/myPage" style="color: black; text-decoration: none"
          >마이페이지</router-link
        >
      </div>
      <div id="logout" v-show="isLogined">
        <a @click="logout()" style="color: black; text-decoration: none"
          >로그아웃</a
        >
      </div>
    </div>
    <div id="nav">
      <div id="menu">
        <ul>
          <li>
            <img
              src="@/assets/main/alarm.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link
              to="/expire"
              style="color: black; text-decoration: none"
              >유통임박 상품</router-link
            >
          </li>
          <li>
            <img
              src="@/assets/main/harvest.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link
              to="/uglyfood"
              style="color: black; text-decoration: none"
              >못난이 농산물</router-link
            >
          </li>
          <li>
            <img
              src="@/assets/main/desktop.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link
              to="/refurb"
              style="color: black; text-decoration: none"
              >리퍼브 상품</router-link
            >
          </li>
          <li>
            <img
              src="@/assets/main/product.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link
              to="/upload"
              style="color: black; text-decoration: none"
              >상품 등록</router-link
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    isLogined() {
      return this.$store.getters["userStore/isLogined"];
    },
    nickname() {
      return this.$store.getters["userStore/nickname"];
    },
  },
  methods: {
    logout: function () {
      localStorage.removeItem("token");
      this.$store.dispatch("userStore/logout");
      window.location.reload();
    },
  },
};
</script>

<style>
#header {
  position: relative;
  z-index: 4000;
  margin-top: 40px;
  margin-bottom: 10px;
}

#intro {
  position: relative;
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}

#logo {
  text-align: center;
}
#search {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 300px;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}
#login {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 0;
  font-size: 14px;
  width: 60px;
  font-family: "NEXON Lv1 Gothic OTF";
}

#about {
  position: absolute;
  top: 50%;
  bottom: 0;
  width: 60px;
  right: 200px;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}

#join {
  position: absolute;
  top: 50%;
  bottom: 0;
  width: 60px;
  right: 100px;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}

#logout {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 0;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}
#myPage {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 95px;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}
#nav {
  position: relative;
}
#menu {
  width: 1280px;
  height: 60px;
  line-height: 60px;
  margin: auto;
  border-top: 1px solid rgb(179, 179, 171);
  border-bottom: 1px solid rgb(179, 179, 171);
}

#menu ul li {
  display: block;
  width: 310px;
  list-style: none;
  line-height: 60px;
  font-size: 18px;
  float: left;
  text-align: center;
  font-family: "SDSamliphopangche_Outline";
}
li img {
  margin-right: 8px;
}
</style>
