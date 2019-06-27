<template>
  <div class="hello">
     首页
     <el-button @click="show">asd</el-button>
  </div>
</template>

<script>
export default {
  name: 'ParentFindChildren',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods: {
    show(){
      let id = localStorage.getItem('userID') 
      let flag = localStorage.getItem('hasLogin') 
      let f2 = this.$store.state.hasLogin
      console.log(id,flag,typeof(flag),f2,typeof(f2));
      let ss = 'false'  
      console.log(ss == 'false')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
