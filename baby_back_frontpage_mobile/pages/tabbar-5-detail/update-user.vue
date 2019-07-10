<template>
	<view>
		<view class="padding bg-white">
			<view class="avatar" @click="ChooseImage">
				<image :src="user.profileUrl" style="width: 250upx;height: 250upx;" mode="aspectFill"></image>
			</view>
		</view>
		<form @submit="formSubmit">
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">昵称</view>
				<input :value="user.username" @input="nickChange" name="nick"></input>
			</view>
			<view class="cu-form-group" @click="showMulLinkageThreePicker">
				<view class="title" style="width: 150upx;">活动范围</view>
				<input disabled="true" :value="user.location"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">手机号</view>
				<input disabled="true" :placeholder="user.tel"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">邮箱</view>
				<input :value="user.email" @input="emailChange"></input>
			</view>
			<view class="cu-form-group">
				<button class="cu-btn bg-blue round" style="width:200upx;margin: 0 auto;"
				 form-type="submit">确定</button>
			</view>
		</form>
		<mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault" @onConfirm="onConfirm">
		</mpvue-city-picker>
	</view>
</template>

<script>
	var graceChecker = require("../../common/graceChecker.js");
	import mpvueCityPicker from '@/components/mpvue-citypicker/mpvueCityPicker.vue';
	import cityData from '@/common/city.data.js';
	export default {
		components: {
		    mpvueCityPicker
		},
		data() {
			return {
				user: {},
				cityPickerValueDefault: [0, 0, 1],
				themeColor: '#007AFF',
				pickerText: '',
				detail:''
			}
		},
		onLoad(options) {
			//把JSON字符串转换为对象
			console.log(options.data);
			this.user = JSON.parse(options.data);
			console.log(this.user)
		},
		methods: {
			nickChange(e){
				this.user.username=e.detail.value;
				console.log(this.user.username);
			},
			emailChange(e){
				this.user.email=e.detail.value;
				console.log(this.user.email);
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + this.user.id;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data
				}).catch(error => {
					console.log(error)
				})
			},
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						console.log('res.tempFilePaths:'+res.tempFilePaths);
						this.user.profileUrl = res.tempFilePaths[0];
						console.log(this.user.profileUrl);
					}
				});
			},
			showMulLinkageThreePicker() {
			    this.$refs.mpvueCityPicker.show()
			},
			onConfirm(e) {
			    this.pickerText = JSON.stringify(e);
				this.user.location=e.label;
			},
			formSubmit: function(e) {
				//将下列代码加入到对应的检查位置
				//定义表单规则
				var rule = [
					{
						name: "nick",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请填写昵称"
					}
				];
				//进行表单检查
				var formData = e.detail.value;
				var checkRes = graceChecker.check(formData, rule);
				let _this=this;
				if (checkRes) {
					uni.showToast({
						title: "验证通过!",
						icon: "none"
					});
				} else {
					uni.showToast({
						title: graceChecker.error,
						icon: "none"
					});
					return;
				}
				console.log(this.user)
				//let url = this.URLS.userUpdateUrl + '?user=' + this.user;
				this.$api.post(this.URLS.userUpdateUrl,this.user).then(data => {
					console.log('开始上传图片...');
					if(this.user.profileUrl!=''){
						uni.uploadFile({
							url: this.URLS.uploadPictureUrl + "?action=AS_PROFILE", 
							filePath: _this.user.profileUrl,
							name: 'file',
							formData: {
								'id': _this.user.id
							},
							header: {
								'Authorization': 'Bearer '+ this.$store.state.token
							},
							success: (uploadFileRes) => {
								console.log(uploadFileRes);
								_this.myToast('更新成功');
								return;
							},
							fail: (uploadFileRes) => {
								console.log(uploadFileRes);
								_this.myToast('头像上传失败')
								return;
							}
						});
					}
					_this.myToast('更新成功')
				}).catch(error => {
					_this.myToast('更新失败')
					console.log(error)
				})
				
			},
		}
	}
</script>

<style>
	.avatar{
		margin: 0 auto;
		border-radius: 50%;
		border-color: #0081ff;
		border-style: solid;
		border-width: 8upx;
		overflow: hidden;
		align-items: center;
		background-color: #0081ff;
		width: 250upx;
		height: 250upx;
		vertical-align: middle;
		justify-content:center; 
	}
</style>
