import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Join from "../views/user/Join.vue";
import Login from "../views/user/Login.vue";
import About from "../views/About.vue";
import Upload from "../views/product/Upload.vue";
import SocialJoin from "../views/user/SocialJoin.vue";
import Expire from "../views/product/Expirelist.vue";
import Ugly from "../views/product/Uglyfoodlist.vue";
import Refurb from "../views/product/Refurblist.vue";

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
    path: "/socialJoin",
    name: "SocialJoin",
    component: SocialJoin,
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
  {
    path: "/expire",
    name: "Expire",
    component: Expire,
  },
  {
    path: "/uglyfood",
    name: "Uglyfood",
    component: Ugly,
  },
  {
    path: "/refurb",
    name: "Refurb",
    component: Refurb,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
