<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <PicUpload :type="2">

      </PicUpload>
    </div>
    <div class="text item">
      <div class="block">
      </div>
    </div>
  </el-card>
</template>

<script>
import PicUpload from "@/pages/findChildren/PicUpload";
export default {
  name: 'FsatMatch',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  components: {
    PicUpload
  },
  methods: {
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
