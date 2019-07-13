<template>
  <div id="app">
    <transition name="fade">
      <router-view v-if="isRouterAlive" />
    </transition>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";
export default {
  name: "App",
  data() {
    return {
      isRouterAlive: true,
      websocket: null
    };
  },
  provide() {
    return {
      reload: this.reload,
      connect: this.connect
    };
  },
  destroyed() {
    this.websocketclose();
  },
  methods: {
    connect() {
      console.log("connected!!!!!!!!");
      this.initWebSocket();
    },
    loadData(id) {
      let url = URLS.lostBabyFindUrl;
      let _this = this;
      request(url, { id: id })
        .then(data => {
          if (data.rtnCode == 200) {
            data.data.content[0].date = _this.dateFormat(
              data.data.content[0].date
            );
            _this.$store.commit("setMessageList", data.data.content);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    },
    initWebSocket() {
      var ws = null;
      //判断当前浏览器是否支持WebSocket
      if ("WebSocket" in window) {
        let baseUrl = URLS.baseUrl
        baseUrl = baseUrl.split(':')[1].substring(2)
        ws = new WebSocket("ws://"+baseUrl+":18080/websocket");
      } else {
        alert("当前浏览器 Not support websocket");
      }

      //连接发生错误的回调方法
      ws.onerror = this.websocketonerror;

      //连接成功建立的回调方法
      ws.onopen = this.websocketonopen;

      //接收到消息的回调方法
      ws.onmessage = this.websocketonmessage;

      //连接关闭的回调方法
      ws.onclose = this.websocketclose;

      this.websocket = ws;
    },
    websocketonopen() {
      console.log("WebSocket连接成功");
      this.websocket.send(this.$store.state.userID);
    },
    websocketonerror(e) {
      //错误
      console.log("WebSocket连接发生错误");
    },
    websocketonmessage(e) {
      //数据接收
      console.log(e.data);
      if (e.data === "Who are you?") {
        this.websocket.send(this.$store.state.userID);
        return;
      }
      let listString = e.data.split(":");
      if (listString[0] == "LOST") {
        this.$store.commit('setHasMessage',true)
        let idList = listString[1].split(",");
        for (let id in idList) {
          this.loadData(idList[id]);
        }
      } else if (listString[0] == "MATCH") {
        this.$store.commit('setHasMessage',true)
        let idList = listString[1].split(",");
        for (let Mid in idList) {
          this.loadMatchData(idList[Mid]);
        }
      }
    },
    websocketclose(e) {
      //关闭
      console.log("connection closed (" + e.code + ")");
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
        day
      );
    },
    loadMatchData(id) {
      let url = URLS.matchBabyFindUrl;
      let _this = this;
      request(url, { id: id })
        .then(data => {
          if (data.rtnCode == 200) {
            data.data.content[0].date = _this.dateFormat(
              data.data.content[0].date
            );
            _this.$store.commit("setMatchMessageList", data.data.content);
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
  left: 0;
  top: 0;
  /* position: relative; */
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

body {
  margin: 0;
}

::-webkit-scrollbar {
  width: 7px;
  height: 7px;
  border-radius: 10px;
}
::-webkit-scrollbar-thumb {
  border-radius: 5px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}
</style>
