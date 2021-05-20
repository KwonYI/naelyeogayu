import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import axios from "axios";

window.Kakao.init("32cc5853b0dbe0c495fd7ae49428f991");

Vue.config.productionTip = false;

Vue.prototype.$axios = axios;

axios.defaults.baseURL = "http://k4a103.p.ssafy.io:9000/a103";
// axios.defaults.baseURL = "http://localhost:9000/a103";
axios.defaults.headers.post["Content-Type"] = "application/json";

new Vue({
  router,
  store,
  vuetify,
  beforeCreate() {
    if (localStorage.getItem("token") !== null) {
      this.$store.dispatch("userStore/login", localStorage.getItem("token"));
    }
  },
  render: (h) => h(App),
}).$mount("#app");
