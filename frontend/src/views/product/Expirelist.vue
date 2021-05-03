<template>
  <div>
    <div id="explain">
      <p id="question">유통임박 상품이란?</p>
      <div id="photo"></div>
      <p id="listtitle">상품 목록</p>
      <expirecard />
    </div>
  </div>
</template>

<script>
import expirecard from "@/components/product/card/expirecard.vue";
export default {
  components: {
    expirecard,
  },
};
</script>

<style>
@font-face {
  font-family: "NEXON Lv1 Gothic OTF Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF Bold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#explain {
  width: 1290px;
  margin: 0 auto;
}
#question {
  max-height: 40px;
  margin: 5px 0px 5px 150px;
  font-size: 18px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
  color: rgb(95, 95, 95);
}
#photo {
  margin: 0 auto;
  height: 170px;
  width: 1050px;
  border: solid 2px rgb(95, 95, 95);
}
#listtitle {
  text-align: center;
  font-size: 24px;
  font-family: "NEXON Lv1 Gothic OTF Bold";
  color: rgb(0, 0, 0);
  max-height: 20px;
  margin-top: 20px;
}
</style>
