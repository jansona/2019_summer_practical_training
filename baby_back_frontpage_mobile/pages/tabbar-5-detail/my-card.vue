<template>
	<view>
		<!-- 选项卡 -->
		<scroll-view id="nav-bar" scroll-x="true" class="bg-white nav text-center" scroll-left="0" @scroll="scroll">
			<view class="cu-item" :class="0==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="0">
				我的帖子
			</view>
			<view class="cu-item" :class="1==tabCurrentIndex?'text-blue cur':''" @click="tabSelect" data-id="1">
				我的评论
			</view>
		</scroll-view>
		
		<!--帖子列表-->
		<view v-show="articleShow" v-for="(item, index) in articleList" :key="index" class="cu-card dynamic">
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg"></view>
						<view class="content flex-sub" style="margin-top:30upx;">
							<view>{{item.user.name}}</view>
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
					<view class="desc"><view class="text-content">{{item.content}}</view></view>
				</view>
				<view class="text-gray text-sm text-right padding">
					<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.viewNum}}
					<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{item.likeNum}}
					<text class="cuIcon-messagefill margin-lr-xs"></text> {{item.replyNum}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		mounted:function() {
			uni.setNavigationBarTitle({
				title: '我的帖子'
			});
			this.getData();
		},
		data() {
			return {
				tabCurrentIndex: 0, //当前选项卡索引
				scrollLeft: 0, //顶部选项卡左滑距离
				articlList:[],
				commentList:[],
				user:{},
				articleShow:true,
				commentShow:false
			}
		},
		methods: {
			tabSelect(e) {
				this.tabCurrentIndex = e.currentTarget.dataset.id;
				if (this.tabCurrentIndex == 0) {
					this.lostShow = true;
					this.findShow = false
				} else {
					this.findShow = true;
					this.lostShow = false
				}
				// this.refreshData();
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			/**
			 * @param {Object} pageNUm
			 * size需要更改成为正确的大小
			 */
			getData() {
				let _this=this;
				
					let articleurl =this.URLS.lostBabyFindByUserUrl +'?user='+this.user;
					console.log(url);
					this.$api.post(url)
						.then(data => {
							_this.lostList = data.data.data.content
							for (let i = 0; i < _this.lostList.length; i++) {
								let id = _this.lostList[i].id;
								_this.lostPicUrls.push(_this.yieldPicUrl(id));
							}
						}).catch(error => {
							console.log(error)
						})
				
					let commenturl =this.URLS.matchBabyFindByUserUrl +'?user='+this.user
					this.$api.post(url)
						.then(data => {
							_this.findList = data.data.data.content;
							for (let i = 0; i < _this.findList.length; i++) {
								let id = _this.findList[i].id;
								_this.findPicUrls.push(_this.yieldPicUrl(id));
							}
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
			}
		},
	}
</script>

<style>
</style>
