<template>
  <div class="head-container" :style="style">
    <el-col :span="3">
      <img style="width: 199px;height:64px;float:left" :src="require('@/assets/logo_2.png')" />
    </el-col>
    <el-col :span="12" :offset="2">
      <el-menu
        :default-active="activeMenuIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        router
        background-color="transparent"
      >
        <el-menu-item index="/home">首页</el-menu-item>
        <!-- <el-submenu index="2">
          <template slot="title"  @click="handleSelect('2','2')">寻亲登记</template>
          <el-menu-item index="2-1">家寻宝贝</el-menu-item>
          <el-menu-item index="2-2">宝贝寻家</el-menu-item>
          <el-menu-item index="2-3">选项3</el-menu-item>
        </el-submenu>-->
        <el-menu-item index="/findRegist">寻亲登记</el-menu-item>
        <el-menu-item index="/fastMatch">快速匹配</el-menu-item>
        <el-menu-item index="/faceWall">面孔墙</el-menu-item>
        <el-menu-item index="/bbsHome">论坛</el-menu-item>
        <el-menu-item index="/more">更多</el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="7" class="person-container">
      <el-badge
        :is-dot="true"
        :hidden="!hasMessage"
        v-if="this.$store.state.hasLogin"
        class="message-style"
      >
        <el-popover placement="top-start" width="400" trigger="hover">
          <el-table :data="$store.state.messageList">
            <el-table-column width="100" property="name" label="姓名"></el-table-column>
            <el-table-column width="150" property="date" label="失踪时间"></el-table-column>
            <el-table-column width="300" property="place" label="失踪地点"></el-table-column>
          </el-table>
          <el-button slot="reference" icon="el-icon-message" circle @click="openMessage"></el-button>
        </el-popover>
        <!-- <el-button icon="el-icon-message" circle @click="openMessage"></el-button> -->
      </el-badge>
      <template v-if="this.$store.state.hasLogin">
        <el-popover placement="bottom" trigger="hover" width="50" style="text-aligin:right">
          <a class="a-style">
            <i class="el-icon-user-solid" @click="personalHome">个人主页</i>
          </a>
          <br />
          <a class="a-style">
            <i class="el-icon-switch-button" @click="logout">退出登录</i>
          </a>
          <el-image :src="$store.state.userInfo.profileUrl" slot="reference" class="head-icon">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-popover>
      </template>
      <template v-else>
        <el-button type="primary" round class="login-btn" @click="gotoLoginOrRegist">登录/注册</el-button>
      </template>
    </el-col>
  </div>
</template>
<script>
import URLS from "@/config/config";
import { fetch } from "@/api/api";
import axios from "axios";
export default {
  name: "Header",
  inject: ["reload"],
  created() {
    this.$nextTick(() => {
      this._initBody();
    });
  },
  props: {
    imgUrl: String
  },
  data() {
    return {
      activeIndex: "1",
      hasMessage: true,
      hasLogin: this.$store.state.hasLogin,
      style: {},
      opacity: 0
    };
  },
  methods: {
    
    
    loadData(id) {
      let url = URLS.lostBabyFindUrl
      let _this= this
      request(url, { id: id })
        .then(data => {
          if (data.rtnCode == 200) {
            _this.gridData.concat(data.data.content);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
      this.$emit("on-navbar-click");
      if (key == 2) {
        this.$route;
      }
    },
    personalHome() {
      console.log("enter personal home");
      this.$router.push({
        path: "userDetail",
        query: { id: this.$store.state.userID }
      });
    },
    logout() {
      axios
        .delete(URLS.logoutUrl)
        .then(data => {
          console.log("登出成功!", data);
        })
        .catch(error => {
          console.log(error);
        });
      console.log("logout");
      this.$store.commit("setUserID", { id: -1, flag: true });
      this.$store.commit("delToken");
      this.$router.push("home");
      this.reload();
    },
    openMessage() {
      console.log("open message");
    },
    dateFormat: function(time) {
      var date = new Date(time);
      var year = date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      var hours =
        date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      var minutes =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      var seconds =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },
    gotoLoginOrRegist() {
      this.$router.push("loginOrRegist");
    },
    gotoMore() {
      this.$router.push("more");
    },
    _initBody() {
      window.onscroll = () => {
        this.opacity = window.pageYOffset / 250;
        this.style = {
          background: `rgba(255,255,255,${this.opacity})`,
          "box-shadow": `0px 0px 5px 5px rgba(0,0,0,${this.opacity * 0.05})`
        };
      };
    }
  },
  computed: {
    activeMenuIndex() {
      console.log("/" + this.$route.path.split("/").reverse()[0]);
      let path = "/" + this.$route.path.split("/").reverse()[0];
      if (path == "/home" || path == "/more") {
        this.$emit("change-container-class", true);
      } else {
        this.$emit("change-container-class", false);
      }
      return "/" + this.$route.path.split("/").reverse()[0];
    }
  }
};
</script>
<style lang="less" scoped>
.person-container {
  text-align: right;
}
.login-btn {
  margin-top: 10px;
  position: relative;
  right: 20px;
}
.message-style {
  font-size: 1.1em;
  color: #777777;
  position: relative;
  margin-top: -9px;
}
.message-style :hover {
  color: #409eff;
  cursor: pointer;
}
.head-icon {
  margin: 0 20px;
  position: relative;
  width: 40px;
  height: 40px;
  border-radius: 100px;
  top: 10px;
}
.head-icon :hover {
  cursor: pointer;
}
.red {
  background-color: red;
}
@media screen and (min-width: 100px) {
  .head-container {
    overflow: hidden;
    .head-img {
      width: 10px;
      height: 10px;
    }
  }
}
.head-container {
  color: transparent;
  height: 100%;
  // background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4;
  /* margin: 10px */
}
.head-img {
  position: relative;
  top: 0;
  left: 0;
}
.head-right-icon {
  width: 30px;
  height: 30px;
  margin-top: 15px;
  margin-right: 10px;
}
.el-menu--horizontal {
  border-bottom-width: 0px !important ;
}
.el-menu-item {
  background-color: transparent !important;
}
</style>