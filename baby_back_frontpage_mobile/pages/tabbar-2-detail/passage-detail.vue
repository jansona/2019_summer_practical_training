<template>
	<view class="content">
	
		<scroll-view class="scroll" scroll-y>
			<view class="scroll-content">
				<view class="introduce-section">
					<text class="title">{{article.title}}</text>
					<view class="introduce">
						<text>{{article.author}}</text>
						<text>{{article.time}}</text>
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
						<view class="uni-comment-face" style="margin-left: 15upx;"><image :src="item.userimg" mode="widthFix"></image></view>
						<view class="uni-comment-body">
							<view class="uni-comment-top" style="text-align: left">
								<text>{{item.usernick}}</text>
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
			<input class="solid-bottom" placeholder="快发表评论吧~" :adjust-position="false" :focus="false" maxlength="300" cursor-spacing="10"
			 @focus="InputFocus" @blur="InputBlur"></input>
			<view class="action">
				<text class="cuIcon-emojifill text-grey"></text>
			</view>
			<button class="cu-btn bg-green shadow">发送</button>
		</view>
	</view>
</template>

<script>
	import mixLoading from '@/components/mix-loading/mix-loading';
	export default {
		components: {
			mixLoading
		},
		data() {
			return {
				loading: true,
				article: {
					id:0,
					title:'用心耕耘 深情护送宝贝回家 志愿者小梅寻亲成功案例的启示',
					content:'  朋友，当大家合家团圆、安享幸福的生活的时候，你有没有想到，这个世界上还有这样的一个群体。他们像浮萍一样漂浮地生活在这个世界上，脑海中残留的是童年心酸的灰色回忆，过的是度日如年的思亲生活。\n  他们有自己的名字，却并不知道自己真正姓甚名谁。和小梅一样，米楼妈妈也是一位热心的志愿者。她曾在去年12月份接到过一位寻亲人的登记信息，这是一位来自云南宣威龙潭乡大坡村的求助者，想请志愿者帮助寻找与自己母亲一同被拐卖的妹妹朱粉莲。当米楼妈妈转头联系求助人时，信息中唯一留下的手机号却总是无人接听，发出的短信也收不到回复，随后手机便停机了。接下来的几个月，米楼妈妈一直不断的联系这个号码，最终竟变成了一个空号。她一度想要放弃这个任务，然而作为志愿者，大家都清楚每一个求助背后必然都有寻亲人痛苦的经历，既然母亲和妹妹被一起拐卖，为什么寻亲人却只找妹妹？想到这些，米楼妈妈最终没有忍心放弃。在云南志愿者群里，米楼妈妈向大伙求助，希望有人能认识宣威龙潭乡大坡村的人，看看当地是否有这么一家人。很快，志愿者维C通过自己在当地一所学校工作的同学打听到了朱粉莲哥哥的电话。\n  此刻，没有语言可以描绘我们的心情，只是祝福和期待，祝福风雨不再，期待永世安康！',
					author:'hg爹',
					time:'2019-6-28 19:0:0',
					likeNum:19,
					viewNum:100,
					replyNum:4},
				commentList: [{usernick:'小明',date:'2019-6-28 19:10:20',content:'人贩子千刀万剐！',userimg:'../../static/user/face.jpg'},{usernick:'小李',date:'2019-6-28 19:10:20',content:'心疼！',userimg:"../../static/user/face.jpg"},{usernick:'小花',date:'2019-6-28 19:10:20',content:'希望孩子们能早日找到！',userimg:"../../static/user/face.jpg"},{usernick:'小桃',date:'2019-6-28 19:10:20',content:'人贩子千刀万剐！人贩子千刀万剐！人贩子千刀万剐！人贩子千刀万剐！',userimg:"../../static/user/face.jpg"}],
				InputBottom:0,
				comment:{
					id:0,
					userimg:'',
					usernick:'',
					article:'',
					content:'',
					date:''
				}
			}
		},
		onLoad(options){
			//把JSON字符串转换为对象
			//this.detailData = JSON.parse(options.data);
		},
		methods: {
			InputFocus(e) {
				this.InputBottom = e.detail.height
			},
			InputBlur(e) {
				this.InputBottom = 0
			},
			getCommentList(){
				
			}
		}
	}
</script>

<style lang="scss">
	page{
		height: 100%;
	}
	.content{
		margin-top: 15upx;
		display: flex;
		flex-direction: column;
		height: 100%;
		background: #fff;
	}
	.video-wrapper{
		height: 422upx;
		
		.video{
			width: 100%;
			height: 100%;
		}
	}
	.scroll{
		flex: 1;
		position: relative;
		background-color: #f8f8f8;
		height: 0;
	}
	.scroll-content{
		display: flex;
		flex-direction: column;
	}
	/* 简介 */
	.introduce-section{
		display: flex;
		flex-direction: column;
		padding: 20upx 30upx;
		background: #fff;
		line-height: 1.5;
		
		.title{
			font-size: 36upx;
			color: #303133;
			margin-bottom: 16upx;
		}
		.introduce{
			display: flex;
			font-size: 26upx;
			color: #909399;
			text{
				margin-right: 16upx;
			}
		}
	}
	/* 点赞等操作 */
	.actions{
		display: flex;
		justify-content: space-around;
		align-items: center;
		line-height: 1.3;
		padding: 10upx 40upx 20upx;
	
		.action-item{
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: 24upx;
			color: #999;
		}
		.yticon{
			display: flex;
			align-items: center;
			justify-content: center;
			width: 60upx;
			height: 60upx;
			font-size: 52upx;
			
			&.reverse{
				position: relative;
				top: 6upx;
				transform: scaleY(-1);
			}
			&.active{
				color: #ec706b;
			}
		}
		.icon-fenxiang2{
			font-weight: bold;
			font-size: 36upx;
		}
		.icon-shoucang{
			font-size: 44upx;
		}
	}

	.s-header{
		padding: 20upx 30upx;
		font-size: 30upx;
		color: #303133;
		background: #fff;
		margin-top: 16upx;
		
		&:before{
			content: '';
			width: 0;
			height: 40upx;
			margin-right: 24upx;
			border-left: 6upx solid #ec706b;
		}
	}
	/* 评论 */
	.evalution{
		display:flex;
		flex-direction:column;
		background: #fff;
		padding: 20upx 0;
	}
	
	.eva-item{
		display:flex;
		padding: 20upx 30upx;
		position: relative;
		image{
			width: 60upx;
			height: 60upx;
			border-radius: 50px;
			flex-shrink: 0;
			margin-right: 24upx;
		}
		&:after{
			content: '';
			position: absolute;
			left: 30upx;
			bottom: 0;
			right: 0;
			height: 0;
			border-bottom: 1px solid #eee;
			transform: translateY(50%);
		}
		&:last-child:after{
			border: 0;
		}
	}
	.eva-right{
		display:flex;
		flex-direction:column;
		flex: 1;
		font-size: 26upx;
		color: #909399;
		position:relative;
		.zan-box{
			display:flex;
			align-items:base-line;
			position:absolute;
			top: 10upx;
			right: 10upx;
			.yticon{
				margin-left: 8upx; 
			}
		}
		.content{
			font-size: 28upx;
			color: #333;
			padding-top:20upx;
		}
	}
	
	/* 底部 */
	.bottom{
		flex-shrink: 0;
		display: flex;
		align-items: center;
		height: 90upx;
		padding: 0 30upx;
		box-shadow: 0 -1px 3px rgba(0,0,0,.04); 
		position: relative;
		z-index: 1;
		
		.input-box{
			display: flex;
			align-items: center;
			flex: 1;
			height: 60upx;
			background: #f2f3f3;
			border-radius: 100px;
			padding-left: 30upx;
			
			.icon-huifu{
				font-size: 28upx;
				color: #909399;
			}
			.input{
				padding: 0 20upx;
				font-size: 28upx;
				color: #303133;
			}
		}
		.confirm-btn{
			font-size: 30upx;
			padding-left: 20upx;
			color: #0d9fff;
		}
	}
</style>

