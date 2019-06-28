<template>
  <div class="upload-container">
    <el-upload
      class="upload-demo"
      drag
      :multiple="false"
      list-type="picture"
      :data="{'id':id}"
      accept=".png, .jpg, .jpeg"
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
        将照片(jpg/jpeg/png)拖到此处，或
        <em>点击上传</em>
      </div>
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
        <el-col :span="15" :offset="5">
          <el-button type="primary" @click="uploadAndRecog">上传照片并识别</el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="danger" icon="el-icon-delete" circle></el-button>
        </el-col>
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
    type: {
      type:Number,
      default: 1,
    }
  },
  data() {
    return {
      uploadUrl: "",
      uploadFile: null
    };
  },
  mounted() {
    if(this.type === 1){
      this.uploadUrl = URLS.uploadPictureUrl + "?action=AS_LOST_PICS";
    }else{
      this.uploadUrl = URLS.uploadPictureUrl + "?action=RECOGNITION";
    }
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
      console.log("上传成功！")
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
      const isFormatCorr =
        file.type === "image/jpg" ||
        file.type === "image/png" ||
        file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isFormatCorr) {
        this.$notify({
          type: "error",
          message: "图片格式请使用jpg/png",
          title: "格式错误"
        });
      }
      if (!isLt2M) {
        this.$notify({
          type: "error",
          message: "图片大小大于2M",
          title: "图片过大"
        });
      }
      if(!isFormatCorr||!isLt2M) return false
      this.uploadFile = file;
    },
    handleHttpRequest() {
      // console.log("http");
      let fd = new FormData();
      fd.append("file", this.uploadFile);
      fd.append("id", this.$store.state.imageId);
      importRequest(this.uploadUrl, fd).then(data => {
        console.log(data);
        if (data.rtnCode == 200) {
          this.$notify({
            type: "success",
            message: data.msg,
            title: "上传成功"
          });
          this.$emit('on-next-step-click')
        }
      });
    },
    uploadAndRecog(){
      this.$refs.uploader.submit();
    },
    beforeImgUpload(file) {
      console.log(file);
      const isFormatCorr =
        file.type === "image/jpg" ||
        file.type === "image/png" ||
        file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isFormatCorr) {
        this.$notify({
          type: "error",
          message: "图片格式请使用jpg/png",
          title: "格式错误"
        });
      }
      if (!isLt2M) {
        this.$notify({
          type: "error",
          message: "图片大小大于2M",
          title: "图片过大"
        });
      }
      return isFormatCorr && isLt2M;
    },
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
