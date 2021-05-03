<template>
  <div id="list">
    <table class="listTable">
      <colgroup>
        <col width="10%" />
        <col width="50%" />
        <col width="7.5%" />
        <col width="7.5%" />
        <col width="10%" />
        <col width="15%" />
      </colgroup>
      <thead>
        <tr>
          <th class="tableHeader">이미지</th>
          <th class="tableHeader">제목</th>
          <th class="tableHeader">경매단위</th>
          <th class="tableHeader">하락률</th>
          <th class="tableHeader">현재가</th>
          <th class="tableHeader">등록시간</th>
        </tr>
      </thead>
      <tbody>
        <tr class="tableRow" v-for="n in 10" :key="n" @click="goDetail(n)">
          <td class="tableData">
            <img
              class="tableImg"
              src="https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80"
              alt="경매 사진"
            />
          </td>
          <td class="tableData">기타</td>
          <td class="tableData">4시간</td>
          <td class="tableData">1%</td>
          <td class="tableData">100,000원</td>
          <td class="tableData">2021/05/03 00시</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {};
</script>

<style>
#list {
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
}
.listTable {
  width: 100%;
  text-align: center;
  align-items: center;
  border-collapse: collapse;
}
.tableHeader {
  font-size: 20px;
  font-weight: bold;
  height: 40px;
  border-top: solid 1px black;
  font-family: "SDSamliphopangche_Outline";
}
.tableImg {
  margin: 0px;
  padding: 0px;
  border-collapse: collapse;
  max-width: 100%;
  max-height: 100%;
}
td img {
  display: block;
}
.tableRow {
  box-shadow: 6px 5px lightgray;
}
.tableRow:hover {
  box-shadow: 6px 6px 6px 6px grey;
  cursor: pointer;
}
.tableData {
  border: solid 1px;
}
</style>
