<template>
	<view>
		<form method="post">
			<view class="cu-bar bg-white margin-top">
				<view class="action">
					图片上传
				</view>
				<view class="action">
					{{imgList.length}}/1
				</view>
			</view>
			<view class="cu-form-group">
				<view class="grid col-1 grid-square flex-sub">
					<view class="bg-img" @tap="ViewImage" v-if="imgList.length>=1">
						<image :src="imgList[0]" mode="aspectFill"></image>
						<view class="cu-tag bg-red" @tap.stop="DelImg">
							<text class='cuIcon-close'></text>
						</view>
					</view>
					<view class="solids" @tap="ChooseImage" v-if="imgList.length<1">
						<text class='cuIcon-cameraadd'></text>
					</view>
				</view>
			</view>
			<view class="cu-form-group align-start" style="margin-top: 30upx;">
				<view class="my-content"><text>{{content}}</text></view>
			</view>
			<view class="cu-form-group align-start" style="margin-top: 30upx;">
				<view class="title">照片说明</view>
				<textarea maxlength="-1" @input="photoInput" placeholder="请输入其他的说明"></textarea>
			</view>

			<view class="cu-form-group" style="background-color: #FFFFFF;margin-top: 30upx;">
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;" @click="submit" :loading="isMatching" :disabled="isMatching">确认上传</button>
			</view>
		</form>

		<view class="grid col-2 grid-square" style="z-index: 10;margin-top: 50upx;">
			<view class="bg-white" style="margin-top:20upx;margin-left: 12upx;margin-right: 12upx;width: 350upx;height: 350upx;"
			 v-for="(item,index) in matchData" :key="index">
				<img :src="item.picUrl" mode="aspectFill" @click="goToDetail(item)" style="width:250upx;height:250upx;margin-top:15upx;margin-left:50upx;margin-right: 50upx;border-radius: 10upx;overflow: hidden;"></img>
				<view class="my-tag"><text style="color: #FFFFFF;font-size: 25upx;padding: 0 20upx;">{{ item.name }}</text></view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgList: [],
				photoInfo: '',
				content: '请选择上传面容清晰的近期正面照，\n若有其余衣着外貌特征等照片，请适当添加照片说明。',
				matchData: [],
				isMatching: false
			};
		},
		methods: {
			photoInput(e) {
				this.photoInfo = e.detail.value
			},
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {

						} else {
							this.imgList = res.tempFilePaths
						}
					}
				});
			},
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			DelImg(e) {
				uni.showModal({
					title: '寻子者',
					content: '确定要删除这张照片吗？',
					cancelText: '再看看',
					confirmText: '再见',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			submit(e) {
				let _this = this
				this.isMatching = true
				uni.uploadFile({
					url: this.URLS.uploadPictureUrl + "?action=RECOGNITION",
					filePath: _this.imgList[0],
					name: 'file',
					formData: {
						'id': -1
					},
					header: {
						'Authorization': 'Bearer ' + this.$store.state.token
					},
					success: (uploadFileRes) => {
						console.log(uploadFileRes);
						let data1 = JSON.stringify(uploadFileRes.data)
						let data2 = JSON.parse(JSON.parse(data1))
						// let data2 = uploadFileRes.data
						console.log(typeof(data2))
						console.log(data2.data,data2["data"],data2.rtnCode,data2.msg)
						if (uploadFileRes.statusCode == 401) {
							_this.myToast("验证过期，请重新登录")
							uni.reLaunch({
								url: "/pages/login"
							})
							return
						}
						_this.matchData = data2.data
						_this.matchData.push({})
						_this.matchData.pop()
						_this.isMatching = false
					},
					fail: (uploadFileRes) => {
						console.log(uploadFileRes);
						if (uploadFileRes.statusCode == 401) {
							_this.myToast("验证过期，请重新登录")
							uni.reLaunch({
								url: "/pages/login"
							})
						}
						_this.isMatching = false
					}
				});
			},
			goToDetail(item, flag) {
				uni.navigateTo({
					url: '/pages/tabbar-1-detail/baby-detail?data=' + JSON.stringify(item)
				})
			}
		}
	};
</script>

<style>
	.my-content {
		text-align: center;
		height: 120upx;
		margin-top: 50upx;
		margin-bottom: 50upx;
	}

	.my-tag {
		margin-left: 100upx;
		vertical-align: middle;
		position: relative;
		display: inline-flex;
		align-items: center;
		justify-content: center;
		box-sizing: border-box;
		padding: 0upx 16upx;
		height: 35upx;
		font-family: Helvetica Neue, Helvetica, sans-serif;
		white-space: nowrap;
		opacity: 0.3;
		max-width: 150upx;
		max-height: 35upx;
		border-radius: 20upx;
		overflow: hidden;
		background-color: #0D9FFF;
	}
</style>
