<template>
  <div class="upload-container">
    <el-upload
      class="upload-demo"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      :multiple=false
      list-type="picture"
      :data="{'id':id}"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击上传</em>
      </div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png/bmp文件</div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "PicUpload",
  props: {
      id:String,
  },
  data() {
    return {};
  },
  mounted(){
  },
  methods: {}
};
</script>

<style scoped>
.upload-container {
    margin-top: 20px;
}
</style>
