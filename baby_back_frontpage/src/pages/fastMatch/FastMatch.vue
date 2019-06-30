<template>
<div>
  <el-card>
  <div slot="header" class="clearfix">
      <PicUpload :type="2">

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
  }
}
</script>

<style scoped>
</style>
