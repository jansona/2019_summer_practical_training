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
  created() {
    // const wsuri = "ws://localhost:18080/websocket";
    // this.websock = new WebSocket(wsuri, ["91b04edd-70b8-4a4d-8570-05cc5cb8f96d"]);
    // this.websocket = new WebSocket(wsuri);
    // this.websocket.onopen = this.websocketonopen;
  },
  mounted(){
    // this.initWebSocket();
    // this.$options.sockets.onmessage = this.onMessage;
  },
  destroyed(){
    // this.websocketclose();
  },
  methods: {
    connect(){
        console.log("connected!!!!!!!!");
        // const wsuri = "ws://localhost:18080/websocket";
        // this.websocket = new WebSocket(wsuri);
        // this.websocket.onopen = this.websocketonopen;
        // this.websock.onmessage = this.websocketonmessage; 
        this.initWebSocket();
    },
    loadData(id) {
      let url = URLS.lostBabyFindUrl
      let _this= this
      request(url, { id: id })
        .then(data => {
          if (data.rtnCode == 200) {
            data.data.content[0].date=_this.dateFormat(data.data.content[0].date)
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
    initWebSocket(){ //初始化weosocket 
      // const wsuri = "ws://localhost:18080/websocket";//ws地址
      // this.websock = new WebSocket(wsuri);
      // this.websocket.onopen = this.websocketonopen;
      // this.websocket.onerror = this.websocketonerror;
      // this.websock.onmessage = this.websocketonmessage; 
      // this.websock.onclose = this.websocketclose;
      var ws = null;
     //判断当前浏览器是否支持WebSocket
     if ('WebSocket' in window) {
         ws = new WebSocket("ws://localhost:18080/websocket");
     }
     else {
         alert('当前浏览器 Not support websocket')
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
    websocketonerror(e) { //错误
      console.log("WebSocket连接发生错误");
    },
    websocketonmessage(e){ //数据接收 
      console.log(e.data);
      if(e.data === "Who are you?"){
        this.websocket.send(this.$store.state.userID);
        return;
      }
      this.loadData(e.data)

    }, 
    websocketclose(e){ //关闭 
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
        day +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },

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
