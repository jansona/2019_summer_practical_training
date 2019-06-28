<template>
	<view style="background-color: rgba(100,30,40,0.1)">
		<view class="user-head-image">
				<image src="../../../static/img/tabbar/hgsz.jpg"></image>
		</view>
		<view class="usernick-text">牛咕咕</view>
		<view class="cu-list menu">
			<!-- <view class="cu-item" @click="goToPage('/pages/tabbar-5-detail/tabbar-5-regionselect/tabbar-5-regionselect')"> -->
			<view class="cu-item" style="border-radius: 600upx;margin-left: 10upx;margin-right: 10upx;" @click="showMulLinkageThreePicker">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置活动范围</text>
				</view>
			</view>
			<mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault" @onConfirm="onConfirm"></mpvue-city-picker>
			<view class="cu-item" style="border-radius: 600upx;margin-left: 10upx;margin-right: 10upx;margin-top: 20upx;">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item" style="border-radius: 600upx;margin-left: 10upx;margin-right: 10upx;margin-top: 20upx;">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<!-- <view class="cu-item">
				<view class="cu-form-group">
					<view class="title">地址选择</view>
					<picker mode="region" @change="RegionChange" :value="region">
						<view class="picker">
							{{region[0]}}，{{region[1]}}，{{region[2]}}
						</view>
					</picker>
				</view>
			</view> -->
			<!-- <view class="cu-item" @click="SetArea">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">我的帖子</text>
				</view>
			</view>
			<view class="cu-item" @click="SetArea">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item" @click="SetArea">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item" @click="SetArea">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item" @click="SetArea">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item" @click="SetArea">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">我的回复</text>
				</view>
			</view> -->
			<view class="cu-item" style="border-radius: 600upx;margin-left: 10upx;margin-right: 10upx;margin-top: 20upx;">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
			<view class="cu-item" style="border-radius: 600upx;margin-left: 10upx;margin-right: 10upx;margin-top: 20upx;">
				<view class="content">
					<image src="/static/logo.png" class="png" mode="aspectFit"></image>
					<text class="text-grey">设置</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import mpvuePicker from '@/components/mpvue-picker/mpvuePicker.vue';
	import mpvueCityPicker from '@/components/mpvue-citypicker/mpvueCityPicker.vue'
	import cityData from '@/common/city.data.js';
	export default {
		  components: {
		    mpvuePicker,
		    mpvueCityPicker
		},
		data() {
			return {
				title: 'Hello',
				cityPickerValueDefault: [0, 0, 1],
				themeColor: '#007AFF',
				pickerText: '',
				detail:''
			}
		},
		onLoad() {

		},
		methods: {
			// 三级联动选择
			showMulLinkageThreePicker() {
			    this.$refs.mpvueCityPicker.show()
			},
			onConfirm(e) {
			    this.pickerText = JSON.stringify(e);
				this.detail=e.label;
			},
			goToPage(url) {
				if (!url) return;
				uni.navigateTo({
					url
				});
			}
		},
		 onBackPress() {
		  if (this.$refs.mpvuePicker.showPicker) {
		    this.$refs.mpvuePicker.pickerCancel();
		    return true;
		  }
		  if (this.$refs.mpvueCityPicker.showPicker) {
		    this.$refs.mpvueCityPicker.pickerCancel();
		    return true;
		  }
		}
		// () {
		//     if (this.$refs.mpvuePicker.showPicker) {
		//         this.$refs.mpvuePicker.pickerCancel()
		//     }
		//     if (this.$refs.mpvueCityPicker.showPicker) {
		//         this.$refs.mpvueCityPicker.pickerCancel()
		//     }
		// }
	}
</script>

<style>
page {
	height: auto;
	min-height: 100%;
}
.content {
	text-align: left;
	line-height: 40upx;
	margin-top: 10upx;
}
.user-head-image{
	max-width: 200upx;
	overflow: hidden;
	margin: 0 auto;
	padding-top: 35upx;
}
.user-head-image image {
	max-width: 200upx;
	max-height: 200upx;
	border-radius: 50%;
}
.usernick-text{
	text-align: center;
	font-size: 18px;
}
.text {
	margin: 15upx 10upx;
	padding: 0 20upx;
	background-color: #ebebeb;
	height: 70upx;
	line-height: 70upx;
	text-align: center;
	color: #777;
	font-size: 26upx;
}
</style>
