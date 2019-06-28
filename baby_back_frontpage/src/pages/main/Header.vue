<template>
  <div class="head-container">
    <el-col :span="3">
      <img
        style="width: 14.24rem;height:4rem;max-width:100%;float:left"
        :src="require('@/assets/house.jpg')"
      >
    </el-col>
    <el-col :span="12" :offset="2">
      <el-menu
        :default-active="activeMenuIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        router=""
      >
        <el-menu-item index="/home">首页</el-menu-item>
        <!-- <el-submenu index="2">
          <template slot="title"  @click="handleSelect('2','2')">寻亲登记</template>
          <el-menu-item index="2-1">家寻宝贝</el-menu-item>
          <el-menu-item index="2-2">宝贝寻家</el-menu-item>
          <el-menu-item index="2-3">选项3</el-menu-item>
        </el-submenu> -->
        <el-menu-item index="/findRegist">寻亲登记</el-menu-item>
        <el-menu-item index="/fastMatch">快速匹配</el-menu-item>
        <el-menu-item index="/faceWall">面孔墙</el-menu-item>
        <el-menu-item index="/bbsHome">论坛{{this.$store.state.userID}}{{this.$store.state.hasLogin}}</el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="7" class="person-container">
      <el-badge
        :is-dot=true
        :hidden="!hasMessage"
        v-if="hasLogin"
        class="message-style"
      >
        <el-button icon="el-icon-message" circle @click="openMessage"></el-button>
      </el-badge>
      <template v-if="hasLogin">
        <el-popover placement="bottom" trigger="hover" width="50" style="text-aligin:right">
          <a class="a-style"><i class="el-icon-user-solid" @click="personalHome">个人主页</i></a><br>
          <a class="a-style"><i class="el-icon-switch-button" @click="logout">退出登录</i></a>
          <el-image :src="require('@/assets/house.jpg')" slot="reference" class="head-icon">
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
export default {
  name: "Header",
  inject: ['reload'],
  data() {
    return {
      activeIndex: "1",
      hasMessage: true,
      hasLogin: this.$store.state.hasLogin
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
      this.$emit("on-navbar-click");
      if(key == 2) {
        this.$route
      }
    },
    personalHome(){
      console.log("enter personal home");
    },
    logout() {
      console.log("logout");
      this.$store.commit('setUserID', {id:-1,flag:true});
      this.$router.push('home');
      this.reload();
    },
    openMessage() {
      console.log("open message");
    },
    gotoLoginOrRegist(){
      this.$router.push('loginOrRegist')
    }
  },
  computed: {
    activeMenuIndex() {
      // console.log("/" + this.$route.path.split("/").reverse()[0])
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
  color: white;
  height: 100%;
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
</style>
