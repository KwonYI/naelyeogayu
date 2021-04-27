import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import axios from "axios";
import firebase from "firebase";

const firebaseConfig = {
  apiKey: "AIzaSyCYdabRAeRbdWfDLZriezsnRrXPpQqpdW4",
  authDomain: "dutch-auction-2413a.firebaseapp.com",
  projectId: "dutch-auction-2413a",
  storageBucket: "dutch-auction-2413a.appspot.com",
  messagingSenderId: "248691682022",
  appId: "1:248691682022:web:6a3b46f32d0c15e55f2927",
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

window.Kakao.init("32cc5853b0dbe0c495fd7ae49428f991");

Vue.config.productionTip = false;

Vue.prototype.$axios = axios;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
