import axios from "axios";

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const productStore = {
  namespaced: true,

  state: {},
  mutations: {},
  getters: {},
  actions: {
    upload(context, product) {
      console.log(product);
      axios({
        method: "post",
        url: `http://k4a103.p.ssafy.io:9000/a103/product`,
        data: product,
      })
        .then((res) => {
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
export default productStore;
