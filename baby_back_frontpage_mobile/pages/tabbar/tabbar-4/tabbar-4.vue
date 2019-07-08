<template>
	<view>
		<view class="topView"></view>
		<view style="margin-top: 50upx;">
			<view class="padding text-center">
				<view class="padding-lr bg-white">
					<view class="solid-bottom padding">
						<text>{{urgentHint}}</text>
					</view>
					<view class="padding">
						<button class="cu-btn bg-blue margin-tb-sm lg" style="width:300upx;margin: 0 auto;margin-bottom: 20upx;" @click="ReleaseUrgence">发布紧急寻人</button>
					</view>
				</view>
			</view>
			<view class="padding text-center">
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:400upx;margin: 0 auto;margin-top: 50upx;" @click="JoinClick">加入紧急寻人志愿者</button>
				<button class="cu-btn bg-blue margin-tb-sm lg" style="width:400upx;margin: 0 auto;margin-top: 50upx;" @click="Recognition">实名认证</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello',
				urgentHint: '正确输入必要信息并提交，系统审核后将按照2小时，8小时，24小时，大于3天等时间分布，分别向不同位置客户端推送消息，以帮助建立信息网。'
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
