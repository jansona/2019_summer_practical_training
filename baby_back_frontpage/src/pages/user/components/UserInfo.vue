<template>
  <div>
    <el-card>
      <div slot="header" class="clearfix">
        <el-row v-if="isMe">
          <span style="float: left; font-size: 15px;margin-top: 5px;">提醒：点击头像即可上传或更新头像</span>
          <el-button
            type="primary"
            size="small"
            icon="el-icon-edit"
            style="float: right;"
            @click="edit"
          >编辑</el-button>
        </el-row>
      </div>
      <div>
        <div @click="toggleShow">
          <el-card
            :body-style="{ padding: '3px'}"
            style="float: left; padding: 1px"
            class="pic-container"
          >
            <el-avatar shape="square" :size="100" fit="contain" :src="picUrl"></el-avatar>
          </el-card>
        </div>
        <el-table
          :data="tableData"
          stripe
          style="width: 70%; float: right; padding: 1px"
          :show-header="false"
          size="small"
        >
          <el-table-column prop="name" width="180"></el-table-column>
          <el-table-column prop="content"></el-table-column>
        </el-table>
      </div>
    </el-card>

    <el-dialog title="编辑用户信息" :visible.sync="dialogFormVisible">
      <el-form :model="user">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="user.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="user.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
          <el-input v-model="user.tel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="家庭地址" :label-width="formLabelWidth">
          <el-input v-model="user.location" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="commit">确 定</el-button>
      </div>
    </el-dialog>
    <div id="app">
	<my-upload field="file"
        @crop-success="cropSuccess"
        @crop-upload-success="cropUploadSuccess"
        @crop-upload-fail="cropUploadFail"
        v-model="show"
		:width="300"
		:height="300"
    :noRotate="noRotate"
    :url="url"
		:params="params"
		:headers="headers"
		img-format="jpg"></my-upload>
	<!-- <img :src="imgDataUrl"> -->
</div>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";
import axios from "axios";
import myUpload from 'vue-image-crop-upload';
export default {
  name: "UserInfo",
  inject : ['reload'],
  props: {
    picUrl: String,
    tableData: Array,
    user: Object
  },
  components: {
			'my-upload': myUpload
    },
    mounted:function(){
      this.isMe=(this.$store.state.userID == parseInt(this.$route.query.id))
    },
  data() {
    return {
      isMe : true,
      noRotate:false,
      url : '',
      dialogFormVisible: false,
      formLabelWidth: "120px",
      show: false,
			params: {
			},
			headers: {
        smail: '*_~',
        Authorization: 'Bearer '+this.$store.state.token
			},
			imgDataUrl: '' // the datebase64 url of created image
    };
  },
  methods: {
    cropSuccess() {
      console.log("success")
    },
    showImg(url) {
      if (url === null || url === "") {
        // console.log('待加载的图片地址为:' + url)
        return require("@/assets/house.jpg");
      } else {
        // console.log('待加载的图片地址为:' + url)
        return url;
      }
    },
    edit() {
      this.dialogFormVisible = true;
    },
    commit() {
      let _this=this
      let url=URLS.userUpdateUrl
      axios
        .post(url, this.user)
        .then(function(response) {
          if(response.data.rtnCode==200){
            _this.$notify({
            message: "更新成功",
            type: "success",
            duration: 1500,
            offset: 50
          });
          _this.reload();
          }else{
             _this.$notify({
            message: "网络不稳定",
            type: "warning",
            duration: 1500,
            offset: 50
          });
          }
        })
        .catch(function(error) {
          console.log(error);
          _this.$notify({
            message: "网络不稳定",
            type: "warning",
            duration: 1500,
            offset: 50
          });
        });
        this.dialogFormVisible = false;
    },
    toggleShow() {
      if(!this.isMe){
        return;
      }
        this.show = !this.show;
        this.url = URLS.uploadPictureUrl+'?id='+this.user.id+'&action=AS_PROFILE'
       
			},
            /**
			 * crop success
			 *
			 * [param] imgDataUrl
			 * [param] field
			 */
			cropSuccess(imgDataUrl, field){
				console.log('-------- crop success --------');
				this.imgDataUrl = imgDataUrl;
			},
			/**
			 * upload success
			 *
			 * [param] jsonData  server api return data, already json encode
			 * [param] field
			 */
			cropUploadSuccess(jsonData, field){
				console.log('-------- upload success --------');
				console.log(jsonData);
				console.log('field: ' + field);
			},
			/**
			 * upload fail
			 *
			 * [param] status    server api return error status, like 500
			 * [param] field
			 */
			cropUploadFail(status, field){
				console.log('-------- upload fail --------');
				console.log(status);
				console.log('field: ' + field);
			}
    
  }
};
</script>

<style scoped>
</style>
