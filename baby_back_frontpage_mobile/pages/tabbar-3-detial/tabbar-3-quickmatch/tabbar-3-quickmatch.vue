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
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;" @click="submit">确认上传</button>
			</view>
		</form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgList: [],
				photoInfo:'',
				content:'请选择上传面容清晰的近期正面照，\n若有其余衣着外貌特征等照片，请适当添加照片说明。'
			};
		},
		methods:{
			photoInput(e){
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
			submit(e){
				let _this=this;
				if (_this.imgList != null && _this.imgList.length > 0) {
					console.log('开始上传图片...');
					console.log(_this.imgList[0])
					uni.uploadFile({
						url: this.URLS.uploadPictureUrl + "?action=RECOGNITION", 
						filePath: _this.imgList[0],
						name: 'file',
						formData: {
							'id': 0
						},
						header: {
							'Authorization': 'Bearer '+ this.$store.state.token
						},
						success: (uploadFileRes) => {
							console.log(uploadFileRes);
							_this.myToast('成功');
						},
						fail: (uploadFileRes) => {
							console.log(uploadFileRes);
							_this.myToast('失败');
							return;
						}
					});
				}
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
</style>
