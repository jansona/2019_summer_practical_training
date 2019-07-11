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
		<view class="cu-card dynamic" v-for="(item,index) in commentList" :key=index>
			<view class="cu-item shadow">
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg" style="overflow: hidden;">
							<image :src="item.user.profileUrl" mode="aspectFit"></image>
						</view>
						<view class="content flex-sub" style="margin-top:5upx;">
							<view>{{item.user.username}}</view>
							<view class="text-gray text-sm flex justify-between">
								{{dateFormat(item.date)}}
							</view>
						</view>
					</view>
					<view style="margin-left: 25upx;margin-top: 15upx;">回复：{{item.content}}
					</view>
					<text class="cuIcon-deletefill" style="margin-left: 600upx;color: #999999;" @click="deleteComment(item)"></text>
				</view>
				<view class="cu-card dynamic" @click="navToDetails(item)" style="margin: 20upx 20upx;background-color: #EAEAEA;border-radius: 20upx;overflow: hidden;">
					<view class="title">
						<view class="text-cut" style="font-weight: 700;margin-left: 15upx;margin-top: 15upx;">{{item.article.title}}</view>
					</view>
					<view class="passage-content" style="margin-top: 5upx;">
						<view class="desc">
							<view class="text-content">{{item.article.content}}</view>
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
				userid: this.$store.state.userId,
				user:{},
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
				let commenturl =this.URLS.commentFindByUserUrl +'?user='+this.userid
				this.$api.post(commenturl)
					.then(data => {
						_this.commentList = data.data.data.content;
					}).catch(error => {
						console.log(error)
					})
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
				let data = item.article;
				uni.navigateTo({
					url: '/pages/tabbar-2-detail/passage-detail?data='+JSON.stringify(data)
				})
			},
			deleteComment(item){
				this.modalName='Modal';
				this.modalContent='您确定要删除这条评论吗？'
				this.deleteid=item.id
			},
			deleteConfirm(){
				let url = this.URLS.commentDeleteUrl + '?id=' + this.deleteid;
				let _this = this
				this.$api._delete(url).then(data => {
					this.modalName=null;
					this.myToast('删除成功')
					this.getData()
				}).catch(error => {
					this.modalName=null;
					this.myToast('删除失败')
					console.log(error)
				})
			},
			dateFormat(isostr) {
				var parts = isostr.match(/\d+/g);
				return (parts[0] + '-' + parts[1] + '-' + parts[2] + ' ' + parts[3] + ':' + parts[4] + ':' + parts[5]);
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
