<template>
  <div id="app">
    <transition name="fade">
      <router-view v-if="isRouterAlive" />
    </transition>
  </div>
</template>

<script>
import URLS from "@/config/config";
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
      reload: this.reload
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
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    },
    onMessage(data){
      console.log(data);
      if(data.data === "Who are you?"){
        this.$socket.send(this.$store.state.userID);
      }
    },
    // initWebSocket(){ //初始化weosocket 
    //   const wsuri = "ws://localhost:18080/websocket";//ws地址
    //   this.websock = new WebSocket(wsuri);
    //   this.websocket.onopen = this.websocketonopen;
    //   this.websocket.onerror = this.websocketonerror;
    //   this.websock.onmessage = this.websocketonmessage; 
    //   this.websock.onclose = this.websocketclose;
    // }, 
    websocketonopen() {
      console.log("WebSocket连接成功");
      this.websocket.send(this.$store.state.userID);
    },
    // websocketonerror(e) { //错误
    //   console.log("WebSocket连接发生错误");
    // },
    // websocketonmessage(e){ //数据接收 
    //   const redata = JSON.parse(e.data);
    //   console.log(redata.value); 
    // }, 
    // websocketsend(agentData){//数据发送 
    //   this.websock.send(agentData);   
    // }, 
    // websocketclose(e){ //关闭 
    //   console.log("connection closed (" + e.code + ")"); 
    // }
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
