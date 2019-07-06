<template>
  <div class="hello">
    <!-- <el-button @click="show">asd</el-button> -->
    <span class="title1">What we do</span>
    <span class="title2">宝贝回家</span>
    <span class="title3">
      将人脸识别技术哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
      哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
      哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
    </span>
    <!-- <div class="box-container"> -->
    <el-row :gutter="50" class="box-container">
      <el-col :span="8">
        <div class="box">
          <div class="image-container image1" style></div>
          <span class="text1">登记信息</span>
          <span class="text2">adfdfgsdfgjkdshgsdjhfjasgdhfahjsdgfjhaghdfasgdfgasdgfjagsdjh</span>
          <el-button plain class="btn" @click="gotoFindChild">
            &nbsp了解更多
            <i class="el-icon-right"></i>&nbsp&nbsp
          </el-button>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="box">
          <div class="image-container image2" style></div>
          <span class="text1">人脸识别</span>
          <span class="text2">adfdfgsdfgjkdshgsdjhfjasgdhfahjsdgfjhaghdfasgdfgasdgfjagsdjh</span>
          <el-button plain class="btn" @click="gotoFastMatch">
            &nbsp了解更多
            <i class="el-icon-right"></i>&nbsp&nbsp
          </el-button>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="box">
          <div class="image-container image3" style></div>
          <span class="text1">大数据匹配</span>
          <span class="text2">adfdfgsdfgjkdshgsdjhfjasgdhfahjsdgfjhaghdfasgdfgasdgfjagsdjh</span>
          <el-button plain class="btn">
            &nbsp了解更多
            <i class="el-icon-right"></i>&nbsp&nbsp
          </el-button>
        </div>
      </el-col>
    </el-row>
    <!-- </div> -->
    <el-row class="bottom-text">
      <el-col :span="8">
        <span class="bottom-text1">{{data1}}&nbsp+</span>
        <br />
        <span class="bottom-text2">走失登记</span>
      </el-col>
      <el-col :span="8">
        <span class="bottom-text1">{{data2}}&nbsp+</span>
        <br />
        <span class="bottom-text2">寻家登记</span>
      </el-col>
      <el-col :span="8">
        <span class="bottom-text1">{{data3}}&nbsp+</span>
        <br />
        <span class="bottom-text2">成功匹配</span>
      </el-col>
    </el-row>
    <el-button @click="show" style="margin-top:50px;">test</el-button>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";
import axios from 'axios';
export default {
  name: "ParentFindChildren",
  data() {
    return {
      data1: 123,
      data2: 456,
      data3: 78
    };
  },
  methods: {
    show() {
      // request('http://127.0.0.1:9999/api/auth/oauth/token',{
      //   username:'13212341234',password:'tycoding',grant_type:'password'
      // }).then(data => {
      //   console.log(data)
      // }).catch(error => {
      //   console.log(error)
      // })
      // let state = this.$store.state;
      // if (state.token) {
      //   console.log("enter");
      // }
      // console.log("state:", state.userID, state.hasLogin, state.token);
      // fetch({url:URLS.logoutUrl})
      //   .then(data => {
      //     console.log("已经登陆成功 ", data);
      //   })
      //   .catch(error => {
      //     console.log(error);
      //   });
      this.$store.commit('setUserID',{id:1,flag:true})
    },
    gotoFindChild() {
      this.$router.push("FindRegist");
    },
    gotoFastMatch() {
      this.$router.push("FastMatch");
    },
    gotoMore() {}
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello {
  /* height: 1800px; */
  /* margin-top: -100px; */
  background-image: url(../../assets/bg.jpg);
  background-size: 100% auto;
  padding-bottom: 100%;
  position: relative;
}
.title1 {
  color: aliceblue;
  font-size: 1.7em;
  position: absolute;
  left: 16%;
  top: 15%;
}

.title2 {
  color: blue;
  font-size: 3em;
  position: absolute;
  left: 15%;
  top: 17%;
}

.title3 {
  color: #262262;
  font-size: 1.3em;
  position: absolute;
  left: 14%;
  top: 25%;
  width: 20%;
  word-wrap: break-word;
  text-align: left;
}

.box-container {
  width: 72%;
  left: 15%;
  position: absolute;
  top: 53%;
}

.div-inline {
  display: inline-block;
}
.box {
  width: 100%;
  height: 0;
  padding-bottom: 100%;
  -moz-background-size: 100% 100%;
  background-size: 100% 100%;
  box-shadow: 5px 5px 15px 5px rgba(43, 30, 205, 0.6);
  border-radius: 5px;
  background-color: white;
  position: relative;
}
@-webkit-keyframes animate-big {
  0% {
    background-color: white;
    transform: scale(1);
  }
  to {
    background-color: rgba(43, 30, 205, 0.9);
    transform: scale(1.06);
  }
}
.box:hover {
  position: relative;
  /* width: 106%;
  padding-bottom: 106%;
  left: -3%;
  margin-top: -3%; */
  -webkit-animation: animate-big 0.2s linear forwards;
}

.image-container {
  width: 16%;
  height: 0;
  padding-bottom: 16%;
  -moz-background-size: 100% 100%;
  background-size: 100% 100%;
  position: absolute;
  left: 9%;
  top: 9%;
}

.image1 {
  background-image: url(../../assets/home/icon_1.png);
}
.box:hover .image1 {
  background-image: url(../../assets/home/icon_1_selected.png);
  background-color: transparent;
}

.image2 {
  background-image: url(../../assets/home/icon_2.png);
}
.box:hover .image2 {
  background-image: url(../../assets/home/icon_2_selected.png);
  background-color: transparent;
}

.image3 {
  background-image: url(../../assets/home/icon_3.png);
}
.box:hover .image3 {
  background-image: url(../../assets/home/icon_3_selected.png);
  background-color: transparent;
}

.box:hover span {
  color: white;
}
.text1 {
  position: absolute;
  font-size: 1.5em;
  top: 30%;
  left: 10%;
  color: #4b4b4b;
}
.text2 {
  position: absolute;
  font-size: 1.2em;
  top: 45%;
  left: 10%;
  width: 80%;
  word-wrap: break-word;
  text-align: left;
  color: #aeaeae;
}

.btn {
  position: absolute;
  left: 10%;
  top: 80%;
}

.bottom-text {
  position: absolute;
  width: 50%;
  left: 25%;
  position: absolute;
  top: 82%;
}

.bottom-text1 {
  color: rgba(43, 30, 205, 1);
  font-size: 3em;
}

.bottom-text2 {
  color: rgba(43, 30, 205, 1);
  font-size: 1.5em;
}
</style>
