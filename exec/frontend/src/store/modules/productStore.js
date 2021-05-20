import axios from "axios";

const productStore = {
  namespaced: true,

  state: {
    size: 0,
    detailInfo: "",
    searchList: [],
    word: "",
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
    setSearchResult(state, payload) {
      for (let key in payload) {
        state.searchList.push(payload[key]);
      }
    },
    setListDefault(state) {
      state.searchList = [];
      state.word = "";
    },
    setSearch(state, payload) {
      state.word = payload;
    },
  },
  getters: {
    getsize(state) {
      return state.size;
    },
    getDetailInfo(state) {
      return state.detailInfo;
    },
    getResultList(state) {
      return state.searchList;
    },
    getSearhItem(state) {
      return state.word;
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
    getSearchResult(context, item) {
      context.commit("setListDefault");
      axios({
        method: "get",
        url: `/product/search/${item.category}/${item.word}/0`,
      }).then((res) => {
        context.commit("setSearchResult", res.data.data);
      });
    },
  },
};
export default productStore;
