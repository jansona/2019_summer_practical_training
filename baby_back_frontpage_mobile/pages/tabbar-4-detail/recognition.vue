<template>
	<form @submit="formSubmit" @reset="formReset">
		<view class="cu-form-group">
			<view class="title">姓名</view>
			<input placeholder="请输入姓名" name="name" v-model="recogForm.name"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">身份证号</view>
			<input placeholder="请输入身份证号码" name="idcard" type="idcard" v-model="recogForm.idCard"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">手机号码</view>
			<input placeholder="请输入手机号码" type="number" v-model="recogForm.phone"></input>
			<view class="cu-capsule radius">
				<view class='cu-tag bg-blue '>
					+86
				</view>
				<view class="cu-tag line-blue">
					中国大陆
				</view>
			</view>
		</view>
		<view class="cu-form-group">
			<view class="title">验证码</view>
			<input placeholder="请输入验证码" name="check" type="number" v-model="recogForm.checkNum"></input>
			<button class="cu-btn bg-blue" @click="SendCheckMessage">发送验证码</button>
		</view>
		<view class="padding text-center" style="margin-top: 80upx;">
			<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;" form-type="submit">完成</button>
		</view>
	</form>
</template>

<script>
	var graceChecker = require("../../common/graceChecker.js");
	export default {
		data() {
			return {
				recogForm: {
					name: '',
					idCard:'',
					phone:'',
					checkNum:''  //验证码
				},
			}
		},
		methods: {
			formSubmit: function(e) {
				//将下列代码加入到对应的检查位置
				//定义表单规则
				var rule = [{
						name: "name",
						checkType: "string",
						checkRule: "2,15",
						errorMsg: "姓名应为2-15个字符"
					},
					{
						name: "idcard",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请填写身份证码号"
					},
					{
						name: "phone",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请填写手机号码"
					},
					{
						name: "check",
						checkType: "notnull",
						checkRule: "",
						errorMsg: "请填写验证码"
					}
					//{name:"img", checkType : "notnull", checkRule:"",  errorMsg:"请上传照片"}
				];
				//进行表单检查
				var formData = e.detail.value;
				var checkRes = graceChecker.check(formData, rule);
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
				}
			},
			SendCheckMessage(){
			}
		},
	}
</script>

<style>
</style>
