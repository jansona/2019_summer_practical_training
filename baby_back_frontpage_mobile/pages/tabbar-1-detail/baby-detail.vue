<template>
	<view style="margin:50upx 50upx;width: 650upx;border-radius: 20upx;overflow: hidden;">
		<view class="padding bg-white">
			<view class="avatar">
				<image :src="babyDetail.picUrl" mode="aspectFit">
				</image>
			</view>
		</view>
		<form>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">姓名</view>
				<input disabled="true" :placeholder="babyDetail.name"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">性别</view>
				<input disabled="true" :placeholder="babyDetail.sex"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">出生日期</view>
				<input disabled="true" :placeholder="dateFormat(babyDetail.birthday)"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">籍贯</view>
				<input disabled="true" :placeholder="babyDetail.nativePlace"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">身高</view>
				<input disabled="true" :placeholder="babyDetail.height+''"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">特征</view>
				<input disabled="true" :placeholder="babyDetail.babyDescription"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">失踪日期</view>
				<input disabled="true" :placeholder="dateFormat(babyDetail.date)"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">失踪地点</view>
				<input disabled="true" :placeholder="babyDetail.place"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">失踪过程</view>
				<input disabled="true" :placeholder="babyDetail.missDescription"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">其他描述</view>
				<input disabled="true" :placeholder="babyDetail.otherDescription"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">其他说明</view>
				<input disabled="true" :placeholder="babyDetail.otherExplain"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">联系人</view>
				<input disabled="true" :placeholder="babyDetail.contactName"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">手机号</view>
				<input disabled="true" :placeholder="babyDetail.contactPhone"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">邮箱</view>
				<input disabled="true" :placeholder="babyDetail.contactEmail"></input>
			</view>
			<view class="cu-form-group">
				<view class="title" style="width: 150upx;">其他方式</view>
				<input disabled="true" :placeholder="babyDetail.otherContactMethod"></input>
			</view>
		</form>
		
	</view>
</template>

<script>
	export default{
		onLoad(options) {
			//把JSON字符串转换为对象
			this.babyDetail = JSON.parse(options.data);
			console.log(this.babyDetail);
			console.log(this.imgsrc)
		},
		data(){
			return{
				babyDetail:[],
				imgsrc:''
			}
		},
		methods: {
			dateFormat(time) {
			 	var date = new Date(time);
			 	var year = date.getFullYear();
			 	/* 在日期格式中，月份是从0开始的，因此要加0
			 	 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
			 	 * */
			 	var month =
			 		date.getMonth() + 1 < 10 ?
			 		"0" + (date.getMonth() + 1) :
			 		date.getMonth() + 1;
			 	var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
			 	var hours =
			 		date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
			 	var minutes =
			 		date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
			 	var seconds =
			 		date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
			 	// 拼接
			 	return (
			 		year +
			 		"-" +
			 		month +
			 		"-" +
			 		day +
			 		" " +
			 		hours +
			 		":" +
			 		minutes +
			 		":" +
			 		seconds
			 	);
			 }
		},
	}
</script>

<style>
	.content{
		border-radius: 20upx;
		overflow: hidden;
		background-color:#E6E6E6;
		width: 100%;
		height: 100%;
		margin: 20 20upx;
	}
	.avatar{
		border-radius: 20upx;
		overflow: hidden;
		display: inline-flex;
		align-items: center;
		background-color: #FFFFFF;
		color: #ffffff;
		width: 100%;
		height: 400upx;
		vertical-align: middle;
		justify-content:center; 
	}
</style>
