import axios from "axios";

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const productStore = {
  namespaced: true,

  state: {
    movecategory: 0,
  },
  mutations: {
    setdefault(state) {
      state.movecategory = 0;
    },
    setcategory(state, payload) {
      state.movecategory = payload.category;
    },
  },
  getters: {
    getCategory(state) {
      return state.movecategory;
    },
  },
  actions: {
    async upload(context, product) {
      context.commit("setdefault");
      await axios({
        method: "post",
        url: `/product`,
        data: product,
      })
        .then((res) => {
          if (res.data.success == "success") {
            context.commit("setcategory", product);
            console.log("axios");
            console.log(product.category);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
export default productStore;
