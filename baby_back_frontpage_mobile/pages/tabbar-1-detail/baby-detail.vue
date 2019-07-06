<template>
	<view>
		<view class="padding bg-white">
			<view class="avatar">
				<image :src="imgsrc" mode="aspectFit">
				</image>
			</view>
		</view>
		<view class="cu-form-group">
			<view class="title">姓名</view>
			<input disabled="true" style="margin-left: 58upx;" :placeholder="babyDetail.name"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">性别</view>
			<input disabled="true" style="margin-left: 58upx;" :placeholder="babyDetail.sex"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">籍贯</view>
			<input disabled="true" style="margin-left: 58upx;" :placeholder="babyDetail.nativePlace"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">身高</view>
			<input disabled="true" style="margin-left: 58upx;" :placeholder="babyDetail.height"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">特征</view>
			<input disabled="true" style="margin-left: 58upx;" :placeholder="babyDetail.babyDescription"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">失踪地点</view>
			<input disabled="true" :placeholder="babyDetail.sex"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">失踪过程</view>
			<input disabled="true" :placeholder="babyDetail.missDescription"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">其他描述</view>
			<input disabled="true" :placeholder="babyDetail.otherDescription"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">其他说明</view>
			<input disabled="true" :placeholder="babyDetail.otherExplain"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">联系人</view>
			<input disabled="true" :placeholder="babyDetail.contactName"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">手机号</view>
			<input disabled="true" :placeholder="babyDetail.contactPhone"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">邮箱</view>
			<input disabled="true" :placeholder="babyDetail.contactEmail"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">其他联系方式</view>
			<input disabled="true" :placeholder="babyDetail.otherContactMethod"></input>
		</view>
	</view>
</template>

<script>
	export default{
		onLoad(options) {
			//把JSON字符串转换为对象
			this.babyDetail = JSON.parse(options.data);
			this.imgsrc = options.src;
			console.log(this.babyDetail);
		},
		data(){
			return{
				babyDetail:[],
				imgsrc:''
			}
		}
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
		background-color: #ccc;
		color: #ffffff;
		width: 100%;
		height: 400upx;
		vertical-align: middle;
		justify-content:center; 
	}
</style>
