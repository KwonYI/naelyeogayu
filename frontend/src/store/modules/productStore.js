import axios from "axios";

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const productStore = {
  namespaced: true,

  state: {},
  mutations: {},
  getters: {},
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
  },
};
export default productStore;
