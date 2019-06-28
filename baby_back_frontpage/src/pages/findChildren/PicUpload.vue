<template>
  <div class="upload-container">
    <el-upload
      class="upload-demo"
      drag
      :multiple="false"
      list-type="picture"
      :data="{'id':id}"
      accept=".png, .jpg"
      :auto-upload="false"
      :limit="1"
      ref="uploader"
      :on-success="uploadOnSuccess"
      :on-exceed="handleExceed"
      :before-upload="beforeFileUpload"
      :http-request="handleHttpRequest"
      :action="uploadUrl"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击上传</em>
      </div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
    </el-upload>

    <el-row class="ctrl-row">
      <template v-if="type == 1">
        <el-col :span="7" :offset="5">
          <el-button type="success" @click="gotoPriorStep">上一步</el-button>
        </el-col>
        <el-col :span="7">
          <el-button type="primary" @click="uploadPic">下一步</el-button>
        </el-col>
      </template>
      <template v-else>
        
      </template>
    </el-row>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { importRequest } from "@/api/api";
export default {
  name: "PicUpload",
  props: {
    id: String,
    type: Number
  },
  data() {
    return {
      uploadUrl: "",
      uploadFile: null
    };
  },
  mounted() {
    this.uploadUrl = URLS.uploadPictureUrl + "?action=AS_PICS";
  },
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
    },
    handleExceed() {
      this.$notify({
        title: "提示",
        message: "只能上传一张图片",
        type: "warning"
      });
    },
    beforeFileUpload(file) {
      if (file == null || file == undefined) {
        return false;
      }
      this.uploadFile = file;
    },
    handleHttpRequest() {
      // console.log("http");
      let fd = new FormData();
      fd.append("file", this.uploadFile);
      fd.append("id", this.$store.state.imageId);
      importRequest(this.uploadUrl, fd).then(data => {
        console.log(data);
        if (data.rtnCode == 0) {
          this.$notify({
            type: "success",
            message: data.msg,
            title: "import successful!"
          });
        }
      });
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
