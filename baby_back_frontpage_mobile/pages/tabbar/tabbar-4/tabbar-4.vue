<template>
	<view>
		<view class="topView"></view>
		<swiper class="card-swiper" :class="dotStyle?'square-dot':'round-dot'" :indicator-dots="true" :circular="true"
		 :autoplay="true" interval="5000" duration="500" @change="cardSwiper" indicator-color="#8799a3"
		 indicator-active-color="#0081ff">
			<swiper-item v-for="(item,index) in swiperList" :key="index" :class="cardCur==index?'cur':''">
				<view class="swiper-item" style="height: 420upx;width: 610upx;">
					<image :src="item.url" style="height: 420upx;width: 610upx;" mode="aspectFit" v-if="item.type=='image'"></image>
					<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover" v-if="item.type=='video'"></video>
				</view>
			</swiper-item>
		</swiper>
		<view style="margin-top: 50upx;">
			<view class="padding text-center">
				<view class="padding-lr bg-white">
					<view class="solid-bottom padding">
						<text>{{urgentHint}}</text>
					</view>
					<view class="uni-text" style="font-weight: 800;">失踪时间超过24小时请报警</view>
					<view class="padding">
						<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;margin-bottom: 20upx;" @click="ReleaseUrgence">发布紧急寻人</button>
					</view>
				</view>
			</view>
			<view class="padding text-center">
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:400upx;margin: 0 auto;margin-top: 20upx;" @click="JoinClick">加入紧急寻人志愿者</button>
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:400upx;margin: 0 auto;margin-top: 50upx;" @click="Recognition">实名认证</button>
			
			</view>
			<view class="text-gray" style="text-align: center;">必须实名认证后才可发布紧急寻人</view>
		</view>
		<view class="cu-tabbar-height"></view>
	</view>
</template>

<script>
	export default {
		onLoad() {
			this.TowerSwiper('swiperList');
			// 初始化towerSwiper 传已有的数组名即可
		},
		data() {
			return {
				title: 'Hello',
				urgentHint: '正确输入必要信息并提交，系统审核后将按照2小时，8小时，24小时，大于3天等时间分布，分别向不同位置客户端推送消息，以帮助建立信息网。',
				cardCur: 0,
				swiperList: [{
					id: 0,
					type: 'image',
					url: "../../static/img/swipper/swipper1.png"
				}, {
					id: 1,
					type: 'image',
					url: '../../static/img/swipper/swipper2.png',
				}],
				dotStyle: false,
				towerStart: 0,
				direction: '',
			}
		},
		onLoad() {

		},
		methods: {
			ReleaseUrgence() {
				// this.$store.commit('setUserId',{id:123,flag:true})
				console.log(this.$store.state.userId)
				uni.navigateTo({
					url: '/pages/tabbar-4-detail/releaseurgence'
				})
			},
			JoinClick() {
				// console.log(this.$store.state.userId)
				uni.navigateTo({
					url: '/pages/tabbar-4-detail/joinvolunteer'
				})
			},
			Recognition() {
				uni.navigateTo({
					url: '/pages/tabbar-4-detail/recognition'
				})
			},
			DotStyle(e) {
				this.dotStyle = e.detail.value
			},
			// cardSwiper
			cardSwiper(e) {
				this.cardCur = e.detail.current
			},
			// towerSwiper
			// 初始化towerSwiper
			TowerSwiper(name) {
				let list = this[name];
				for (let i = 0; i < list.length; i++) {
					list[i].zIndex = parseInt(list.length / 2) + 1 - Math.abs(i - parseInt(list.length / 2))
					list[i].mLeft = i - parseInt(list.length / 2)
				}
				this.swiperList = list
			},
			
			// towerSwiper触摸开始
			TowerStart(e) {
				this.towerStart = e.touches[0].pageX
			},
			
			// towerSwiper计算方向
			TowerMove(e) {
				this.direction = e.touches[0].pageX - this.towerStart > 0 ? 'right' : 'left'
			},
			
			// towerSwiper计算滚动
			TowerEnd(e) {
				let direction = this.direction;
				let list = this.swiperList;
				if (direction == 'right') {
					let mLeft = list[0].mLeft;
					let zIndex = list[0].zIndex;
					for (let i = 1; i < this.swiperList.length; i++) {
						this.swiperList[i - 1].mLeft = this.swiperList[i].mLeft
						this.swiperList[i - 1].zIndex = this.swiperList[i].zIndex
					}
					this.swiperList[list.length - 1].mLeft = mLeft;
					this.swiperList[list.length - 1].zIndex = zIndex;
				} else {
					let mLeft = list[list.length - 1].mLeft;
					let zIndex = list[list.length - 1].zIndex;
					for (let i = this.swiperList.length - 1; i > 0; i--) {
						this.swiperList[i].mLeft = this.swiperList[i - 1].mLeft
						this.swiperList[i].zIndex = this.swiperList[i - 1].zIndex
					}
					this.swiperList[0].mLeft = mLeft;
					this.swiperList[0].zIndex = zIndex;
				}
				this.direction = ""
				this.swiperList = this.swiperList
			}
		}
	}
</script>

<style scoped>
	.content {
		text-align: center;
		height: 400upx;
		margin-top: 200upx;
	}

	.topView {
		width: 100%;
		height: var(--status-bar-height);
	}
</style>
