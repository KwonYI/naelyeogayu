<template>
  <div class="myPage">
    <div class="myPageFrame">
      <div class="sideBar">
        <SideBar @move="move" />
      </div>
      <div class="myPageContents">
        <MyInfo v-show="number == 0 || number == 1" />
        <PasswordCheck v-show="checkStatus && number == 2" @change="change" />
        <PasswordChange
          v-show="checkStatus == false && number == 2"
          @complete="complete"
        />
        <Like v-show="number == 0 || number == 7" />
        <Reserved v-show="number == 0 || number == 3" />
        <Buy v-show="number == 0 || number == 4" />
        <Sell v-show="number == 0 || number == 5" />
        <Delete v-show="number == 6" />
      </div>
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/myPage/SideBar.vue";
import MyInfo from "@/components/myPage/MyInfo.vue";
import Reserved from "@/components/myPage/Reserved.vue";
import Buy from "@/components/myPage/Buy.vue";
import Sell from "@/components/myPage/Sell.vue";
import Delete from "@/components/myPage/Delete.vue";
import PasswordCheck from "@/components/myPage/PasswordCheck.vue";
import PasswordChange from "@/components/myPage/PasswordChange.vue";
import Like from "@/components/myPage/Like.vue";

export default {
  components: {
    SideBar,
    MyInfo,
    Delete,
    Reserved,
    Buy,
    Sell,
    PasswordCheck,
    PasswordChange,
    Like,
  },
  data() {
    return {
      number: 0,
      checkStatus: true,
    };
  },
  methods: {
    move(num) {
      this.number = num;
    },
    change() {
      this.checkStatus = false;
    },
    complete() {
      this.checkStatus = true;
      this.number = 0;
    },
  },
};
</script>

<style src="@/assets/css/user/MyPage.css" scoped></style>
