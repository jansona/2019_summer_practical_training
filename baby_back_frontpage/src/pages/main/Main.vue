<template>
  <div>
    <div class="bg"></div>
    <div class="main-container">
      <!-- <el-header class="bottom-shadow"> -->
      <div class="m-head">
        <home-header @on-navbar-click="handleNavBarClick" @change-container-class="handleChangeClass"></home-header>
      </div>
      <!-- </el-header> -->
      <!-- <div class="bg"> -->
      <!-- <el-main> -->
      <home-content ref="content" :class="{'content-container':!isHomePage,'homepage-container':isHomePage}"></home-content>
      <!-- </el-main> -->
      <!-- </div> -->
    </div>
  </div>
</template> 
<script>
import HomeHeader from "@/pages/main/Header";
import HomeContent from "@/pages/main/Content";
import { fetch } from "@/api/api";
import URLS from "@/config/config";
export default {
  name: "Home",
  inject: ['connect'],
  components: {
    HomeHeader,
    HomeContent
  },
  data() {
    return {
      isHomePage: false,
      websocket: null
    };
  },
  computed:{
    listenedUserID() {
        return this.$store.state.userID;
    }
  },
  watch:{
    listenedUserID: function(ov,nv){
      console.log('watch start……');
      if(this.$store.state.userID){
        const wsuri = "ws://localhost:18080/websocket";
        this.websocket = new WebSocket(wsuri);
      }
    }
  },
  methods: {
    handleSelect(indexPath) {
      // console.log(indexPath);
    },
    handleHeadImgClick() {},
    handleNavBarClick() {
      // console.log("click!")
      this.$refs.content.reload();
    },
    handleChangeClass(flag){
      this.isHomePage = flag;
    }
  },
  watch: {},
  mounted() {
    console.log("main mounted",this.$route.path)
    let state = this.$store.state;
    // if(this.$route.path == '/') {
    //   this.$router.push('home');
    // }
    if(state.token && state.token != 'undefined') {
      fetch(URLS.userInfoUrl)
        .then(data => {
          console.log("已经登陆成功 ", data);
          this.$store.commit("setUserID", {
            'id': data.data.id,
            flag: false
          });
          this.$store.commit('setUserInfo',data.data)
          this.connect()
          console.log("userInfo",this.$store.state.userInfo)
        })
        .catch(error => {
					this.$store.commit('setToken',{token:'',flag:true})
					this.$store.commit('setUserID',{id:-1,flag:true})
          console.log(error);
          this.$notify({
            title: "登录超时",
            offset: 50,
            type: "info",
            duration: 1500,
          })
        });
    }
  },
  computed: {
  }
};
</script>
<style scoped>
.bottom-shadow {
  box-shadow: 0px 10px 30px #111111;
}
.bottom-shadow /deep/ .head-container {
  box-shadow: 0px 5px 10px #888888;
}
body {
  background: #333;
}
.el-header,
.el-footer {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 10px;
  padding: 0 0 0 0;
  margin: 0 0 0 0;
}
.el-main {
  background-color: #f1f3f4;
  color: #333;
  text-align: center;
  /* overflow: hidden; */
}
.content-container {
  position: absolute;
  width: 60%;
  left: 20%;
  top: 100px;
  margin-bottom: 100px;
}
.homepage-container {
  position: absolute;
  width: 100%;
  left: 0;
}
body {
  width: 100%;
  /* height: 100%; */
}
.main-container {
  width: 100%;
  /* height: 100%; */
  /* position: absolute; */
  /* overflow: hidden; */
  margin: 0 0 0 0;
  padding: 0 0 0 0;
  background-color: #f1f1f1;
  top: 0;
  left: 0;
}

@-webkit-keyframes animate-cloud {
  0% {
    background-position: 600px 100%;
  }
  to {
    background-position: 0 100%;
  }
}

.bg {
  /* display: -webkit-box;
  display: -ms-flexbox; */
  /* display: flex; */
  /* -webkit-box-align: center;
  -ms-flex-align: center; */
  /* align-items: center; */
  /* position: absolute;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4;
  -webkit-animation: animate-cloud 20s linear infinite;
  animation: animate-cloud 20s linear infinite; */

  display: table;
  width: 100%;
  height: 100%;
  padding: 100px 0;
  text-align: center;
  color: #fff;
  background: url(../../assets/bg_13.png) 0 bottom ;
  background-color: #000;
  background-size: cover;
  position: fixed;
  z-index: -1;
}
.el-main {
  padding: 0;
}

.el-menu--horizontal > .el-menu-item.is-active {
  /* border-bottom: 3px solid #409EFF; */
}

.el-header {
  background-color: transparent;
  /* background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4; */
}

.m-head {
  position: fixed;
  height: 64px;
  z-index: 100;
  width: 100%;
  background-color: transparent;
}
</style>
