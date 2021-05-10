import axios from "axios";

// const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const productStore = {
  namespaced: true,

  state: {
    size: 0,
    detailInfo: "",
  },
  mutations: {
    setsize(state, payload) {
      state.size = payload;
    },
    setDetailInfo(state, payload) {
      state.detailInfo = payload;
    },
    setDetailDefault(state) {
      state.detailInfo = "";
    },
  },
  getters: {
    getsize(state) {
      return state.size;
    },
    getDetailInfo(state) {
      return state.detailInfo;
    },
  },
  actions: {
    upload(context, product) {
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
    productDetail(context, id) {
      context.commit("setDetailDefault");
      axios({
        method: "get",
        url: `/product/detail/${id}`,
      })
        .then((res) => {
          context.commit("setDetailInfo", res.data.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
export default productStore;
