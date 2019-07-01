<template>
<div>
  <el-card>
  <div slot="header" class="clearfix">
      <PicUpload :type="2" @return-data="getData">
      </PicUpload>
    </div>
    <div class="text item">
      <div class="block">
      </div>
    </div> 
  </el-card>
  
  </div>
</template>

<script>
import PicUpload from "@/pages/findChildren/PicUpload";
export default {
  name: 'FsatMatch',
  data () {
    return {
    }
  },
  components: {
    PicUpload
  },
  methods: {
    getData(data){
      console.log("waimian:",data);
    }
  }
}
</script>

<style scoped>
</style>
