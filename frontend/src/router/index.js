import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Join from "../views/user/Join.vue";
import Login from "../views/user/Login.vue";
import MyPage from "../views/user/MyPage.vue";
import About from "../views/About.vue";
import Upload from "../views/product/Upload.vue";
import SocialJoin from "../views/user/SocialJoin.vue";

Vue.use(VueRouter);

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(() => {
    return window.location.reload();
  });
};

const requireAuth = () => (to, from, next) => {
  if (localStorage["token"] && localStorage["token"] !== "") {
    return next();
  } else {
    return next("/");
  }
};

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/socialJoin",
    name: "SocialJoin",
    component: SocialJoin,
  },
  {
    path: "/myPage",
    name: "MyPage",
    component: MyPage,
    beforeEnter: requireAuth(),
  },
  {
    path: "/about",
    name: "About",
    component: About,
  },
  {
    path: "/upload",
    name: "Upload",
    component: Upload,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
