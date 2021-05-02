<template>
  <div class="myPage">
    <div class="myPageFrame">
      <div class="sideBar">
        <SideBar />
      </div>
      <div>
        <MyInfo />
        <v-divider></v-divider>
        <Delete />
      </div>
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/myPage/SideBar.vue";
import MyInfo from "@/components/myPage/MyInfo.vue";
import Delete from "@/components/myPage/Delete.vue";
export default {
  components: { SideBar, MyInfo, Delete },
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
</style>
