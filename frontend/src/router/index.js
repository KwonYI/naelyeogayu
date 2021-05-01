import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Join from "../views/user/Join.vue";
import Login from "../views/user/Login.vue";
import About from "../views/About.vue";
import Upload from "../views/product/Upload.vue";

Vue.use(VueRouter);

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
