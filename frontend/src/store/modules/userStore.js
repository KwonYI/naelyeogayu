// import axios from "axios";
import jwtDecode from "jwt-decode";

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const userStore = {
  namespaced: true,

  state: {
    id: "",
    email: "",
    name: "",
    address: "",
    phone: "",
    point: 0,
    isLogined: false,
  },
  mutations: {
    SET_USER(state, user) {
      state.id = user.id;
      state.email = user.email;
      state.name = user.name;
      state.address = user.address;
      state.phone = user.phone;
      state.point = user.point;
      state.isLogined = true;
    },
    FREE_USER(state) {
      state.id = "";
      state.email = "";
      state.name = "";
      state.address = "";
      state.phone = "";
      state.point = 0;
      state.isLogined = false;
    },
  },
  getters: {
    id: (state) => {
      return state.id;
    },
    email: (state) => {
      return state.email;
    },
    name: (state) => {
      return state.name;
    },
    address: (state) => {
      return state.address;
    },
    phone: (state) => {
      return state.phone;
    },
    point: (state) => {
      return state.point;
    },
    isLogined: (state) => {
      return state.isLogined;
    },
    user: (state) => {
      const user = {
        id: state.id,
        email: state.email,
        name: state.name,
        address: state.address,
        phone: state.phone,
        point: state.point,
      };
      return user;
    },
  },
  actions: {
    login({ commit }, token) {
      const user = jwtDecode(token).user;
      return commit("SET_USER", user);
    },
    logout({ commit }) {
      return commit("FREE_USER");
    },
  },
};
export default userStore;
