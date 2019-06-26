<template>
  <div class="upload-container">
    <el-upload
      class="upload-demo"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      :multiple="false"
      list-type="picture"
      :data="{'id':id}"
      accept=".png, .jpg"
      :auto-upload="false"
      :limit="1"
      ref="uploader"
      :on-success="uploadOnSuccess"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击上传</em>
      </div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
    </el-upload>

    <el-row class="ctrl-row">
      <el-col :span="7" :offset="5">
        <el-button type="success" @click="gotoPriorStep">上一步</el-button>
      </el-col>
      <el-col :span="7">
        <el-button type="primary" @click="uploadPic">下一步</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "PicUpload",
  props: {
    id: String
  },
  data() {
    return {};
  },
  mounted() {},
  methods: {
    gotoPriorStep() {
      this.$emit("on-prior-step-click");
    },
    uploadPic() {
      this.$refs.uploader.submit();
    },
    uploadOnSuccess() {
      //图片上传成功
      this.$emit("on-next-step-click");
    }
  }
};
</script>

<style scoped>
.upload-container {
  margin-top: 20px;
  width: 60%;
  left: 20%;
  position: relative;
}
.ctrl-row {
  margin-top: 30px;
}
</style>
