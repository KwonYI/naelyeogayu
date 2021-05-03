<template>
  <div>
    <div id="cardview">
      <div id="card">
        <div id="box">
          <div id="dday">
            <p id="remain">유통 임박</p>
            <p id="day">D-10</p>
          </div>
          <v-img
            id="img"
            src="@/assets/apple.jpg"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div id="info">
          <p id="title">사과</p>
          <p id="stock">수량 : 10개</p>
          <p id="rate">하락율 : <span style="color: red"> - 50%</span></p>
          <p id="max">4000원</p>
          <p id="cur">2000원/개</p>
        </div>
      </div>
    </div>
    <div id="cardview">
      <div id="card">
        <div id="box">
          <div id="dday">
            <p id="remain">유통 임박</p>
            <p id="day">D-10</p>
          </div>
          <v-img
            id="img"
            src="@/assets/banana.jpg"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div id="info">
          <p id="title">바나나바나나바나나바나나바나나바나나</p>
          <p id="stock">수량 : 10개</p>
          <p id="rate">하락율 : <span style="color: red"> - 50%</span></p>
          <p id="max">4000원</p>
          <p id="cur">2000원/개</p>
        </div>
      </div>
    </div>
    <div id="cardview">
      <div id="card">
        <div id="box">
          <div id="dday">
            <p id="remain">유통 임박</p>
            <p id="day">D-10</p>
          </div>
          <v-img
            id="img"
            src="@/assets/orange.jpg"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div id="info">
          <p id="title">사과</p>
          <p id="stock">수량 : 10개</p>
          <p id="rate">하락율 : <span style="color: red"> - 50%</span></p>
          <p id="max">4000원</p>
          <p id="cur">2000원/개</p>
        </div>
      </div>
    </div>
    <div id="cardview">
      <div id="card">
        <div id="box">
          <div id="dday">
            <p id="remain">유통 임박</p>
            <p id="day">D-10</p>
          </div>
          <v-img
            id="img"
            src="@/assets/banana.jpg"
            height="270px"
            max-height="270px"
            :aspect-ratio="1 / 1"
          />
        </div>
        <div id="info">
          <p id="title">사과</p>
          <p id="stock">수량 : 10개</p>
          <p id="rate">하락율 : <span style="color: red"> - 50%</span></p>
          <p id="max">4000원</p>
          <p id="cur">2000원/개</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
@font-face {
  font-family: "NEXON Lv1 Gothic OTF Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF Bold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "NEXON Lv1 Gothic OTF";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
#title,
#cur {
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
#stock,
#max,
#date,
#rate {
  font-family: "NEXON Lv1 Gothic OTF";
}
#cardview {
  width: 430px;
  display: inline-block;
}
#card {
  position: relative;
  border: solid 1px rgb(179, 178, 178);
  margin: 30px 60px;
  cursor: pointer;
}
#box {
  overflow: hidden;
}
#img:hover {
  transform: scale(1.15);
  transition: 0.3s;
}
#info {
  height: 130px;
}
#info p {
  position: absolute;
}
#dday {
  z-index: 1000;
  display: inline-block;
  position: absolute;
  top: 0;
  right: 10px;
  background-color: rgb(208, 119, 134);
  text-align: center;
}
#remain {
  font-size: 12px;
  margin: 0 7px;
  color: white;
  font-family: "NEXON Lv1 Gothic OTF";
}
#day {
  font-size: 17px;
  margin-top: 5px;
  margin-bottom: 5px;
  color: white;
  font-weight: bold;
  font-family: "NEXON Lv1 Gothic OTF Bold";
}
#title {
  width: 180px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  bottom: 70px;
  left: 40px;
  font-size: 20px;
}
#stock {
  font-size: 15px;
  bottom: 50px;
  right: 20px;
}
#cur {
  font-size: 20px;
  bottom: 0px;
  left: 70px;
  margin-bottom: 10px;
}
#max {
  bottom: 0px;
  color: rgb(154, 150, 154);
  right: 20px;
  text-decoration: line-through;
}
#rate {
  font-size: 15px;
  bottom: 25px;
  right: 20px;
}
#date {
  font-size: 15px;
  right: 20px;
  bottom: 0px;
}
</style>
