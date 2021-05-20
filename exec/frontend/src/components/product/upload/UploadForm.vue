<template>
  <div id="form">
    <div id="categorys">
      <h4>카테고리 선택</h4>
      <div id="radioform">
        <v-radio-group
          id="radio"
          dense
          row
          style="margin: 0px 100px"
          v-model="product.category"
        >
          <v-radio class="rdo" label="유통임박 상품" value="1"></v-radio>
          <v-radio class="rdo" label="못난이 농산물" value="2"></v-radio>
          <v-radio class="rdo" label="리퍼브 상품" value="3"></v-radio>
        </v-radio-group>
      </div>
    </div>
    <div id="productinfo">
      <h4>상품 정보</h4>
      <v-row>
        <div id="name">
          <v-text-field
            autocomplete="off"
            v-model="product.name"
            :counter="20"
            label="상품 이름"
            :rules="nameRule"
          ></v-text-field>
          <div v-if="product.category == 1" class="selected">
            <v-row>
              <div id="calendar">
                <v-menu
                  ref="expireMenu"
                  v-model="expireMenu"
                  :close-on-content-click="false"
                  :return-value.sync="product.expirationDate"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="product.expirationDate"
                      label="유통 기간 입력"
                      prepend-icon="mdi-calendar"
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="product.expirationDate"
                    :min="product.startDate"
                    no-title
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="expireMenu = false">
                      Cancel
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.expireMenu.save(product.expirationDate)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
                <input
                  ref="expireImageInput"
                  type="file"
                  hidden
                  @change="onCheckExpire"
                />
                <v-btn depressed @click="onClickCheckExpire"
                  >사진으로 유통기한 확인하기</v-btn
                >
              </div>

              <div class="stock">
                <v-text-field
                  autocomplete="off"
                  suffix="개"
                  v-model="product.stock"
                  :rules="stockRule"
                  label="수량"
                ></v-text-field>
              </div>
            </v-row>
          </div>
          <div v-if="product.category == 2" class="selected">
            <v-row>
              <div class="kg">
                <v-text-field
                  autocomplete="off"
                  suffix="Kg/박스"
                  v-model="product.unit"
                  label="무게"
                ></v-text-field>
              </div>

              <div class="stock">
                <v-text-field
                  autocomplete="off"
                  suffix="박스"
                  v-model="product.stock"
                  :rules="stockRule"
                  label="수량"
                ></v-text-field>
              </div>
            </v-row>
          </div>
          <div v-if="product.category == 3" class="selected">
            <v-row>
              <div class="unit">
                <v-text-field
                  v-model="product.stock"
                  :rules="stockRule"
                  suffix="개"
                  label="수량"
                ></v-text-field>
              </div>
            </v-row>
          </div>
          <v-textarea
            autocomplete="off"
            v-model="product.descript"
            outlined
            label="상품 상세 설명"
            height="150px"
          ></v-textarea>
        </div>
        <div id="file">
          <p id="imagetitle">상품 이미지 등록</p>
          <div id="uploadimg">
            <v-img
              v-if="imageBinary"
              :src="imageBinary"
              height="180px"
              contain
              :aspect-ratio="1.4"
              alt="img"
              @click="onClickImageUpload"
            />
            <img
              v-else-if="product.category != 0"
              src="@/assets/fileupload.png"
              width="250px"
              alt="img"
              @click="onClickImageUpload"
            />
            <img v-else src="@/assets/fileupload.png" width="250px" alt="img" />
          </div>
          <div id="uploadbtn" v-if="product.category != 0">
            <v-spacer></v-spacer>
            <input
              ref="imageInput"
              type="file"
              hidden
              @change="onChangeImages"
            />
            <v-btn width="250px" depressed @click="onClickImageUpload"
              >등록하기</v-btn
            >
            <v-spacer></v-spacer>
            <p id="caution">*상품관련 이미지를 업로드 해주세요</p>
          </div>
        </div>
      </v-row>
    </div>
    <div id="auctioninfo">
      <h4>경매 정보</h4>
      <div>
        <v-row>
          <div id="price">
            <div class="startPrice">
              <v-text-field
                autocomplete="off"
                suffix="원"
                v-model="startPrice"
                :rules="startPriceRules"
                @blur="startPriceCommas(startPrice)"
                label="경매 시작가"
              ></v-text-field>
            </div>
            <div class="endPrice">
              <v-text-field
                autocomplete="off"
                suffix="원"
                v-model="endPrice"
                :rules="minPriceRules"
                @blur="minPriceCommas(endPrice)"
                label="경매 최저가"
              ></v-text-field>
            </div>
          </div>
          <div id="date">
            <div id="dateform">
              <div>
                <v-menu
                  ref="endDateMenu"
                  v-model="endDateMenu"
                  :close-on-content-click="false"
                  :return-value.sync="product.endDate"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="product.endDate"
                      label="경매 마감일"
                      prepend-icon="mdi-calendar"
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-if="product.category == 1"
                    v-model="product.endDate"
                    :min="product.startDate"
                    :max="deadLine"
                    no-title
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="endDateMenu = false">
                      Cancel
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.endDateMenu.save(product.endDate)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                  <v-date-picker
                    v-else
                    v-model="product.endDate"
                    :min="product.startDate"
                    no-title
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="endDateMenu = false">
                      Cancel
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.endDateMenu.save(product.endDate)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </div>
              <div>
                <v-menu
                  ref="releaseMenu"
                  v-model="releaseMenu"
                  :close-on-content-click="false"
                  :return-value.sync="product.releaseDate"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="product.releaseDate"
                      label="상품 출고일"
                      prepend-icon="mdi-calendar"
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="product.releaseDate"
                    no-title
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="releaseMenu = false">
                      Cancel
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.releaseMenu.save(product.releaseDate)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </div>
            </div>
          </div>
          <div id="discount">
            <p>가격 하락율</p>
            <div>
              <span>총 하락율 : </span
              ><span v-if="totalflag">- {{ totalDiscount }}%</span>
            </div>
            <div>
              <span>일별 평균 하락율 : </span><span>{{ dayDiscount }}%</span>
            </div>
            <div>
              <span>경매 기간 : </span>
              <span> {{ product.startDate }} ~ {{ product.endDate }}</span>
            </div>
            <div>
              <span style="color: rgb(247, 96, 96); font-size: 15px"
                >* 경매시작일은 상품 등록 다음날로 지정됩니다</span
              >
            </div>
          </div>
        </v-row>
      </div>
    </div>
    <div id="submit">
      <v-btn depressed @click="backpage()">이전</v-btn>
      <v-btn color="info" @click="submit()">등록</v-btn>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import "moment/locale/ko";
const KakaoAK = process.env.VUE_APP_KAKAOAK;

export default {
  data() {
    return {
      imageBinary: "",
      expireMenu: false,
      endDateMenu: false,
      releaseMenu: false,
      totalflag: false,
      startPrice: "",
      endPrice: "",
      product: {
        category: 0,
        name: "",
        descript: "",
        imageUrl: "",
        unit: 0,
        stock: 0,
        startPrice: 0,
        minPrice: 0,
        startDate: moment().add(1, "days").format("YYYY-MM-DD"),
        endDate: moment().add(7, "days").format("YYYY-MM-DD"),
        releaseDate: moment().format("YYYY-MM-DD"),
        expirationDate: moment().format("YYYY-MM-DD"),
        status: 1,
        sellerId: 0,
      },
      nameRule: [
        (v) => v.length >= 2 || "2자 이상 입력해주세요",
        (v) => (v && v.length <= 20) || "20자 이내로 작성해주세요",
      ],
      stockRule: [(v) => Number(v) >= 1 || "1개 이상 입력해주세요"],
      startPriceRules: [
        (v) =>
          (v.length > 0 && /^[0-9]*$/.test(v.replace(/[^0-9]/g, ""))) ||
          "숫자만 입력해주세요!",
      ],
      minPriceRules: [
        (v) =>
          (v.length > 0 && /^[0-9]*$/.test(v.replace(/[^0-9]/g, ""))) ||
          "숫자만 입력해주세요!",
        (v) =>
          Number(v.replace(/[^0-9]/g, "")) <
            Number(this.startPrice.replace(/[^0-9]/g, "")) ||
          "최저가가 시작가보다 높을 수 없습니다!",
      ],
      dateRules: [
        (v) =>
          moment(v, "YYYY-MM-DD").isAfter(
            moment(this.product.startDate, "YYYY-MM-DD")
          ) || "경매 시작날짜보다 빠를수 없습니다",
      ],
    };
  },
  watch: {
    endPrice: function () {
      this.totalflag = true;
    },
  },
  computed: {
    totalDiscount: function () {
      let start = Number(this.startPrice.replace(/[^0-9]/g, ""));
      let end = Number(this.endPrice.replace(/[^0-9]/g, ""));
      return (((start - end) / start) * 100).toFixed(2);
    },
    dayDiscount: function () {
      const start = moment(this.product.startDate, "YYYY-MM-DD");
      const end = moment(this.product.endDate, "YYYY-MM-DD");
      if (isNaN(this.totalDiscount)) {
        return 0;
      }
      return -(this.totalDiscount / end.diff(start, "days")).toFixed(2);
    },
    deadLine: function () {
      return moment(this.product.expirationDate)
        .subtract(7, "d")
        .format("YYYY-MM-DD");
    },
  },
  methods: {
    startPriceCommas(x) {
      x = x.replace(/[^0-9]/g, ""); // 입력값이 숫자가 아니면 공백
      x = x.replace(/,/g, ""); // ,값 공백처리
      this.startPrice = x.replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 정규식을 이용해서 3자리 마다 , 추가
    },
    minPriceCommas(x) {
      x = x.replace(/[^0-9]/g, ""); // 입력값이 숫자가 아니면 공백
      x = x.replace(/,/g, ""); // ,값 공백처리
      this.endPrice = x.replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 정규식을 이용해서 3자리 마다 , 추가
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      var binaryData = [];
      binaryData.push(e.target.files[0]);
      this.imageBinary = URL.createObjectURL(new Blob(binaryData), {
        type: "image/png",
      });
      this.getUrl(e.target.files);
    },
    getUrl(file) {
      let formData = new FormData();
      formData.append("files", file[0]);
      axios
        .post(`/uploadImage`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          this.product.imageUrl = res.data["data"];
        })
        .catch((error) => {
          console.log(error);
        });
    },
    submit() {
      if (this.product.category == 0) {
        alert("카테고리를 선택해주세요");
        return;
      }
      this.product.startPrice = Number(this.startPrice.replace(/[^0-9]/g, ""));
      this.product.minPrice = Number(this.endPrice.replace(/[^0-9]/g, ""));
      this.$emit("upload", this.product);
    },
    backpage() {
      this.$emit("back", true);
    },
    onClickCheckExpire() {
      this.$refs.expireImageInput.click();
    },
    onCheckExpire(e) {
      let formData = new FormData();
      formData.append("image", e.target.files[0]);
      axios
        .post(`https://dapi.kakao.com/v2/vision/text/ocr`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `KakaoAK ${KakaoAK}`,
          },
        })
        .then((res) => {
          let result = res.data.result;
          let flag = false;
          let date;
          for (let i = 0; i < result.length; i++) {
            let word = result[i]["recognition_words"][0].replace(/(\s*)/g, "");
            let count = (word.match(/\./g) || []).length;
            if (count >= 2) {
              let cur_date = moment(word);
              if (cur_date.isValid()) {
                date = cur_date.format("YYYY-MM-DD");
                flag = true;
                break;
              }
            }
          }
          if (flag) {
            this.product.expirationDate = date;
          } else {
            alert("유통기한을 인식할 수 없습니다. 다른 사진으로 시도해주세요.");
          }
        })
        .catch((error) => {
          if (error.response.status === 413) {
            alert("사진 용량이 너무 큽니다. 다시 시도해주세요.");
          } else {
            alert("서버 오류입니다. 잠시 후 다시 시도해주세요.");
          }
        });
    },
  },
};
</script>

<style src="@/assets/css/UploadForm.css" scoped></style>
