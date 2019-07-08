<template>
	<view>
		<view class="cu-card dynamic">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg"></view>
						<view class="content flex-sub" style="margin-top:5upx;">
							<view>gugugu</view>
							<view class="text-gray text-sm flex justify-between">
								2019-6-24
							</view>
						</view>
					</view>
					<view style="margin-left: 25upx;margin-top: 15upx;">回复：啊真好啊啊啊啊啊啊真好啊啊啊啊啊啊真好啊啊啊啊啊啊真好啊啊啊啊啊啊真好啊啊啊啊啊
					</view>
					<text class="cuIcon-deletefill" style="margin-left: 570upx;" @click="deleteComment(item)">删除</text>
				</view>
				<view class="cu-card dynamic" style="margin: 20upx 20upx;background-color: #EAEAEA;border-radius: 20upx;overflow: hidden;">
					<view class="title">
						<view class="text-cut" style="font-weight: 700;margin-left: 15upx;margin-top: 15upx;">好好学习，天天向上</view>
					</view>
					<view class="passage-content" style="margin-top: 5upx;">
						<view class="desc">
							<view class="text-content">好好学习，天天向上，啦啦啦啦啦啦啦</view>
						</view>
					</view>
					<view class="text-gray text-sm text-right padding">
						<text class="cuIcon-attentionfill margin-lr-xs"></text> 9
						<text class="cuIcon-appreciatefill margin-lr-xs"></text> 2
						<text class="cuIcon-messagefill margin-lr-xs"></text> 5
					</view>
				</view>
				
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		mounted: function() {
			uni.setNavigationBarTitle({
				title: '我的评论'
			});
			this.getData();
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				commentList: [],
				userid: this.$store.state.userId
			}
		},
		methods: {
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			getData() {
				let _this = this;
	
				let commenturl =this.URLS.commentFindByUserUrl +'?user='+this.user.id
				this.$api.post(url)
					.then(data => {
						_this.commentList = data.data.data.content;
					}).catch(error => {
						console.log(error)
					})
			},
			getUser(userId) {
				let url = this.URLS.userFindByIdUrl + '?id=' + userId;
				let _this = this
				this.$api.post(url).then(data => {
					_this.user = data.data.data
				}).catch(error => {
					console.log(error)
				})
			},
			navToDetails(item){
				console.log(item);
				let data = item;
				uni.navigateTo({
					url: '/pages/tabbar-2-detail/passage-detail?data='+JSON.stringify(data)
				})
			},
			deleteComment(item){
				
			}
		},
	}
</script>

<style>
	.content {
		text-align: center;
		margin-top: 150upx;
	},
	.passage-content {
		text-align: center;
		height: 30upx;
		margin-top: 0;
	},
	.text-content{
		text-align: center;
		padding-left: 5upx;
		padding-right: 5upx;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-line-clamp:1;
		-webkit-box-orient:vertical;
	}
</style>
