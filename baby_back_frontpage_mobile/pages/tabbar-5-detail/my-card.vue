<template>
	<view>
		<view class="cu-modal" :class="modalName=='Modal'?'show':''">
			<view class="cu-dialog">
				<view class="cu-bar bg-white justify-end">
					<view class="content">提示</view>
					<view class="action" @tap="hideModal">
						<text class="cuIcon-close text-red"></text>
					</view>
				</view>
				<view class="padding-xl">
					{{modalContent}}
				</view>
				<view class="cu-bar bg-white justify-end">
					<view class="action">
						<button class="cu-btn line-blue text-blue" @tap="hideModal">取消</button>
						<button class="cu-btn bg-blue margin-left" @tap="deleteConfirm">确定</button>
		
					</view>
				</view>
			</view>
		</view>
		<!-- 选项卡 -->
		<!-- <scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				我的帖子
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				我的评论
			</view>
		</scroll-view> -->

		<!--帖子列表-->
		<view v-show="articleShow" v-for="(item, index) in articleList" :key="index" class="cu-card dynamic">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg"></view>
						<view class="content flex-sub" style="margin-top:5upx;">
							<view>{{item.user.username}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{item.date}}
							</view>
						</view>
					</view>
				</view>
				<view class="title" @click="navToDetails(item)">
					<view class="text-cut" style="font-weight: 700;margin-left: 15upx;margin-top: 15upx;">{{item.title}}</view>
				</view>
				<view class="passage-content" style="margin-top: 5upx;" @click="navToDetails(item)">
					<!-- <view class="bg-img" v-for="(item1,index1) in item.imgList" :key="index1">
						<image :src="item1.value" style="max-height:200upx;max-width: 200upx;"></image>
					</view> -->
					<view class="desc">
						<view class="text-content">{{item.content}}</view>
					</view>
				</view>
				<view class="text-gray text-sm text-right padding">
					<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.viewNum}}
					<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{item.likeNum}}
					<text class="cuIcon-messagefill margin-lr-xs"></text> {{item.replyNum}}
					<text class="cuIcon-deletefill margin-lr-xs" @click="deleteArticle(item)"></text> 
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		mounted: function() {
			uni.setNavigationBarTitle({
				title: '我的帖子'
			});
			this.getData();
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				articleList: [],
				commentList: [],
				userid: this.$store.state.userId,
				articleShow: true,
				modalName:null,
				modalContent:null,
				deleteid:-1
			}
		},
		methods: {
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			getData() {
				let _this = this;

				let articleurl = this.URLS.articleFindByUserUrl + '?user=' + this.userid;
				console.log(articleurl);
				this.$api.post(articleurl)
					.then(data => {
						console.log(data);
						_this.articleList = data.data.data.content;
						console.log(_this.articleList)
					}).catch(error => {
						console.log(error)
					})

				// let commenturl =this.URLS.commentFindByUserUrl +'?user='+this.user
				// this.$api.post(url)
				// 	.then(data => {
				// 		_this.commentList = data.data.data.content;
				// 	}).catch(error => {
				// 		console.log(error)
				// 	})


			},
			hideModal(e) {
				this.modalName = null
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
			deleteArticle(item){
				this.modalName='Modal';
				this.modalContent='您确定删除这篇帖子吗？';
				console.log(item)
				this.deleteid=item.id
			},
			deleteConfirm(){
				let url = this.URLS.articleDeleteUrl + '?id=' + this.deleteid;
				let _this = this
				this.$api._delete(url).then(data => {
					this.modalName=null
					this.myToast('删除成功')
					this.getData()
				}).catch(error => {
					this.modalName=null
					this.myToast('删除失败')
					console.log(error)
				})
			}
		},
	}
</script>

<style>
	.content {
		text-align: left;
		margin-top: 20upx;
	},
	.passage-content {
		text-align: left;
		height: 30upx;
		margin-top: 0;
	},
	.text-content{
		text-align: left;
		margin-left: 8upx;
		padding-left: 5upx;
		padding-right: 5upx;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-line-clamp:1;
		-webkit-box-orient:vertical;
	}
</style>
