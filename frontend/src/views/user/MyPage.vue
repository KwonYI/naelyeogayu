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

<style>
.myPage {
  flex-direction: row;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}
.myPageFrame {
  position: relative;
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}
.sideBar {
  float: left;
  height: 100%;
}
.myPageContents {
  float: left;
  width: 80%;
}
.myPageContents > * {
  padding-left: 1%;
  padding-top: 0.6%;
}
</style>
