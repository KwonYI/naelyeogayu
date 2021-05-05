import axios from "axios";

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const productStore = {
  namespaced: true,

  state: {
    size: 0,
  },
  mutations: {
    setsize(state, payload) {
      state.size = payload;
    },
  },
  getters: {
    getsize(state) {
      return state.size;
    },
  },
  actions: {
    upload(context, product) {
      context.commit("setdefault");
      axios({
        method: "post",
        url: `/product`,
        data: product,
      })
        .then(() => {})
        .catch((error) => {
          console.log(error);
        });
    },
    getListSize(context, category) {
      axios({
        method: "get",
        url: `/product/size/${category}`,
      })
        .then((res) => {
          if (res.data.success == "success") {
            context.commit("setsize", res.data.data);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
export default productStore;
