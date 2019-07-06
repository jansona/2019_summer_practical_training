<template>
	<view class="content">

		<scroll-view class="scroll" scroll-y>
			<view class="scroll-content">
				<view class="introduce-section">
					<text class="title">{{article.title}}</text>
					<view class="introduce">
						<text>{{article.user.name}}</text>
						<text>{{article.date}}</text>
					</view>

					<!-- <rich-text :nodes="detailData.flow"></rich-text> -->
					<view class="content"><text space="emsp">{{article.content}}</text></view>

					<view class="text-gray text-lg text-center padding">
						<text class="cuIcon-attentionfill margin-lr-xs"></text> {{article.viewNum}}
						<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{article.likeNum}}
						<text class="cuIcon-messagefill margin-lr-xs"></text> {{article.replyNum}}
					</view>
				</view>
				<!-- <view class="container" v-show="loading === false">	 -->
				<!-- 评论 -->
				<!-- <view class="s-header">
						<text class="tit">网友评论</text>
					</view>
					<view class="evalution">
						<view  v-for="(item, index) in evaList" :key="index" class="eva-item">
							<image :src="item.src" mode="aspectFill"></image>
							<view class="eva-right">
								<text>{{item.nickname}}</text>
								<text>{{item.time}}</text> -->
				<!-- <view class="zan-box">
									<text>{{item.zan}}</text>
									<text class="yticon icon-shoucang"></text>
								</view> -->
				<!-- <text class="content">{{item.content}}</text>
							</view>
						</view>
					</view> -->
				<!-- </view> -->
				<!-- 加载图标 -->
				<!-- <mixLoading class="mix-loading" v-if="loading"></mixLoading> -->
				<view class="uni-comment" style="margin-bottom: 100upx;">
					<view class="uni-comment-list" v-for="(item, index) in commentList" :key="index" style="background-color: #FFFFFF;">
						<view class="uni-comment-face" style="margin-left: 15upx;">
							<image :src="item.userimg" mode="widthFix"></image>
						</view>
						<view class="uni-comment-body">
							<view class="uni-comment-top" style="text-align: left">
								<text>{{item.user.name}}</text>
							</view>
							<view class="uni-comment-date">
								<text>{{item.date}}</text>
							</view>
							<view class="uni-comment-content" style="text-align: left;">{{item.content}}</view>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>

		<!-- <view class="bottom">
			<view class="input-box">
				<text class="yticon icon-huifu"></text>
				<input 
					class="input"
					type="text" 
					placeholder="点评一下把.." 
					placeholder-style="color:#adb1b9;"/>
			</view>
			<text class="confirm-btn">发送</text>
		</view> -->
		<view class="cu-bar foot input" :style="[{bottom:InputBottom+'px'}]">
			<input class="solid-bottom" v-model="comment.content" placeholder="快发表评论吧~" :adjust-position="false" :focus="false" maxlength="300"
			 cursor-spacing="10" @focus="InputFocus" @blur="InputBlur"></input>
			<button class="cu-btn bg-green shadow" @click="sendComment">发送</button>
		</view>
	</view>
</template>

<script>
	import mixLoading from '@/components/mix-loading/mix-loading';
	export default {
		onReady: function() {
			//let userId=this.$store.state.userId
			let userId = 2
			this.getUser(userId)
		},
		components: {
			mixLoading
		},
		data() {
			return {
				loading: true,
				InputBottom: 0,
				commentList: [],
				article: {},
				user: {},
				comment: {
					"article": {},
					"content": "好吃",
					"date": "2019-07-05T03:34:34.444Z",
					"id": 0,
					"user": {}
				}
			}
		},
		onLoad(options) {
			//把JSON字符串转换为对象
			this.article = JSON.parse(options.data);
			this.getCommentList();
			console.log(this.article)
		},
		methods: {
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			getCommentList() {
				let _this=this;
				let articleid=this.article.id.toString();
				let url=this.URLS.commentFindUrl+'?article='+articleid;
				console.log(articleid);
				this.$api.post(url).then(data => {
					console.log(data);
					_this.commentList=data.data.data.content;
					for(let i=0;i<_this.commentList.length;i++)
					{
						_this.commentList[i].date=_this.dateFormat(_this.commentList[i].date);
					}
					console.log(_this.commentList)
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
			sendComment(){
				let _this=this;
				this.comment.date=new Date();
				this.comment.user=this.article.user;
				this.comment.article=this.article;
				this.$api.put(this.URLS.commentInsertUrl,this.comment).then(data => {
					console.log(data);
					_this.commentList=_this.commentList.concat(data.data.data);
					uni.showToast({
						title:'发送成功'
					})
					_this.comment.content=''
				}).catch(error => {
					console.log(error)
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
	}

	.content {
		margin-top: 15upx;
		display: flex;
		flex-direction: column;
		height: 100%;
		background: #fff;
	}

	.video-wrapper {
		height: 422upx;

		.video {
			width: 100%;
			height: 100%;
		}
	}

	.scroll {
		flex: 1;
		position: relative;
		background-color: #f8f8f8;
		height: 0;
	}

	.scroll-content {
		display: flex;
		flex-direction: column;
	}

	/* 简介 */
	.introduce-section {
		display: flex;
		flex-direction: column;
		padding: 20upx 30upx;
		background: #fff;
		line-height: 1.5;

		.title {
			font-size: 36upx;
			color: #303133;
			margin-bottom: 16upx;
		}

		.introduce {
			display: flex;
			font-size: 26upx;
			color: #909399;

			text {
				margin-right: 16upx;
			}
		}
	}

	/* 点赞等操作 */
	.actions {
		display: flex;
		justify-content: space-around;
		align-items: center;
		line-height: 1.3;
		padding: 10upx 40upx 20upx;

		.action-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: 24upx;
			color: #999;
		}

		.yticon {
			display: flex;
			align-items: center;
			justify-content: center;
			width: 60upx;
			height: 60upx;
			font-size: 52upx;

			&.reverse {
				position: relative;
				top: 6upx;
				transform: scaleY(-1);
			}

			&.active {
				color: #ec706b;
			}
		}

		.icon-fenxiang2 {
			font-weight: bold;
			font-size: 36upx;
		}

		.icon-shoucang {
			font-size: 44upx;
		}
	}

	.s-header {
		padding: 20upx 30upx;
		font-size: 30upx;
		color: #303133;
		background: #fff;
		margin-top: 16upx;

		&:before {
			content: '';
			width: 0;
			height: 40upx;
			margin-right: 24upx;
			border-left: 6upx solid #ec706b;
		}
	}

	/* 评论 */
	.evalution {
		display: flex;
		flex-direction: column;
		background: #fff;
		padding: 20upx 0;
	}

	.eva-item {
		display: flex;
		padding: 20upx 30upx;
		position: relative;

		image {
			width: 60upx;
			height: 60upx;
			border-radius: 50px;
			flex-shrink: 0;
			margin-right: 24upx;
		}

		&:after {
			content: '';
			position: absolute;
			left: 30upx;
			bottom: 0;
			right: 0;
			height: 0;
			border-bottom: 1px solid #eee;
			transform: translateY(50%);
		}

		&:last-child:after {
			border: 0;
		}
	}

	.eva-right {
		display: flex;
		flex-direction: column;
		flex: 1;
		font-size: 26upx;
		color: #909399;
		position: relative;

		.zan-box {
			display: flex;
			align-items: base-line;
			position: absolute;
			top: 10upx;
			right: 10upx;

			.yticon {
				margin-left: 8upx;
			}
		}

		.content {
			font-size: 28upx;
			color: #333;
			padding-top: 20upx;
		}
	}

	/* 底部 */
	.bottom {
		flex-shrink: 0;
		display: flex;
		align-items: center;
		height: 90upx;
		padding: 0 30upx;
		box-shadow: 0 -1px 3px rgba(0, 0, 0, .04);
		position: relative;
		z-index: 1;

		.input-box {
			display: flex;
			align-items: center;
			flex: 1;
			height: 60upx;
			background: #f2f3f3;
			border-radius: 100px;
			padding-left: 30upx;

			.icon-huifu {
				font-size: 28upx;
				color: #909399;
			}

			.input {
				padding: 0 20upx;
				font-size: 28upx;
				color: #303133;
			}
		}

		.confirm-btn {
			font-size: 30upx;
			padding-left: 20upx;
			color: #0d9fff;
		}
	}
</style>
