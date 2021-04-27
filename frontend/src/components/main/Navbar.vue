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
      <div id="join">
        <router-link to="/join" style="color: black; text-decoration: none"
          >회원가입</router-link
        >
      </div>
      <div id="login">
        <router-link to="/login" style="color: black; text-decoration: none"
          >로그인</router-link
        >
      </div>
    </div>
    <div id="nav">
      <div id="menu">
        <ul>
          <li>
            <img
              src="@/assets/alarm.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link to="/" style="color: black; text-decoration: none"
              >유통임박 상품</router-link
            >
          </li>
          <li>
            <img
              src="@/assets/harvest.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link to="/" style="color: black; text-decoration: none"
              >못난이 상품</router-link
            >
          </li>
          <li>
            <img
              src="@/assets/desktop.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link to="/" style="color: black; text-decoration: none"
              >리퍼브 상품</router-link
            >
          </li>
          <li>
            <img
              src="@/assets/product.png"
              alt="logo"
              width="17px"
              height="17px"
            />
            <router-link to="/" style="color: black; text-decoration: none"
              >상품 등록</router-link
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
@font-face {
  font-family: "SDSamliphopangche_Outline";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Outline.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "NEXON Lv1 Gothic OTF";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

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

#login {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 0;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}
#about {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 200px;
  font-size: 14px;
  font-family: "NEXON Lv1 Gothic OTF";
}
#join {
  position: absolute;
  top: 50%;
  bottom: 0;
  right: 100px;
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
