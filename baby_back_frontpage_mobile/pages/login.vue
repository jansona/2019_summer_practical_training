<template>
	<view class="login">
		<view class="content">
			<!-- 头部logo -->
			<view class="header">
				<image src="../static/img/applogo.png"></image>
			</view>
			<!-- 主体表单 -->
			<view class="main">
				<view class="main-list oBorder">
					<input class="main-input" v-model="phoneData" type="text" maxlength="11" placeholder="用户名/电话" />
				</view>
				<view class="main-list oBorder">
					<input class="main-input" v-model="passData" type="text" maxlength="32" placeholder="密码" password="true" />
				</view>
			</view>
			<view>
				<button class="dlbutton buttonBorder" @tap="startLogin()">登 陆</button>
			</view>
			<!-- 底部信息 -->
			<view class="footer">
				<navigator url="forget" open-type="navigate">找回密码</navigator>
				<text>|</text>
				<navigator url="register" open-type="navigate">注册账号</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	var _this;

	export default {
		data() {
			return {
				logoImage: '',
				phoneData: '', //用户/电话
				passData: '', //密码
			};
		},
		mounted() {
			_this = this;
			this.isLogin();
		},
		onLoad: function() {
			//uni.clearStorage();
			this.loadExecution();
		},
		methods: {
			isLogin() {
				//判断缓存中是否登陆过，直接登陆
				console.log(this.$store.state.hasLogin, this.$store.state.userId, this.$store.state.token)
				try {
					console.log("userinfourl:", this.URLS.userInfoUrl)
					const value = uni.getStorageSync('token');
					if (value) {
						//有登陆信息
						console.log("已登录用户：", value);
						this.$api.get(this.URLS.userInfoUrl).then(data => {
							this.$store.commit('setUserId', data.data.data.id)
							this.$store.commit('setUserInfo', data.data.data)
							console.log('userInfo:', this.$store.state.userInfo)
							// console.log(this.connect)
							this.$store.commit('setConnected', true)
							uni.reLaunch({
								url: './index/index',
							});
						}).catch(error => {
							console.log(error)
							this.$store.commit('logout');
							uni.showToast({
								title: "登录过期",
								position: 'bottom',
								icon: 'none'
							})
						})
					}
				} catch (e) {
					// error
				}
			},
			startLogin() {
				//登陆
				// if (this.phoneData.length == "") {
				//      uni.showToast({
				//         icon: 'none',
				// 		position: 'bottom',
				//         title: '用户名不能为空'
				//     });
				//     return;
				// }
				//       if (this.passData.length < 5) {
				//           uni.showToast({
				//               icon: 'none',
				// 		position: 'bottom',
				//               title: '密码不正确'
				//           });
				//           return;
				//       }

				// this.$store.commit('setToken','')
				// this.$api.post( this.URLS.loginUrl,{
				// 			'username': this.phoneData,
				// 			'password': this.passData,
				// 			'grant_type': 'password'
				// 		}

				this.$api.request({
					url: this.URLS.loginUrl,
					data: JSON.parse(JSON.stringify({
						'username': this.phoneData,
						'password': this.passData,
						'grant_type': 'password'
					})),
					method: "POST",
					header: {
						'Content-Type': 'application/x-www-form-urlencoded',
						// 'Content-Type':'application/x-www-form-urlencoded',
						'Access-Control-Allow-Origin': '*',
						'Authorization': 'Basic Y2xpZW50OnNlY3JldA=='
					}
				}).then(data => {
					console.log("login:", data);
					this.$store.commit('setToken', data.data.access_token)
					this.isLogin()
				}).catch(error => {
					console.log(error)
					uni.showToast({
						title: "登录失败",
						position: 'bottom'
					})
				})

			},
			loadExecution: function() {
				/**
				 * 获取本地存储中launchFlag的值
				 * 若存在，说明不是首次启动，直接进入首页；
				 * 若不存在，说明是首次启动，进入引导页；
				 */
				try {
					const value = uni.getStorageSync('launchFlag');
					console.log(value);
					if (value) {
						if (value == true) {
							return;
						} else {
							uni.redirectTo({
								url: '/pages/index/guide'
							});
						}
					} else {
						uni.setStorage({
							key: 'launchFlag',
							data: true,
							success: function() {
								console.log('存储launchFlag');
							}
						});
						uni.redirectTo({
							url: '/pages/index/guide'
						});
					}
				} catch (e) {
					// error 
					uni.setStorage({
						key: 'launchFlag',
						data: true,
						success: function() {
							console.log('error时存储launchFlag');
						}
					});
					uni.redirectTo({
						url: '/pages/index/guide'
					});
				}
				return;
			}
		}
	}
</script>

<style>
	.btn {
		display: flex;
	}

	.content {
		display: flex;
		flex-direction: column;
		justify-content: center;
		/* margin-top: 128upx; */
	}

	/* 头部 logo */
	.header {
		width: 161upx;
		height: 161upx;
		box-shadow: 0upx 0upx 60upx 0upx rgba(0, 129, 255, 0.1);
		border-radius: 50upx;
		margin-top: 128upx;
		margin-bottom: 48upx;
		margin-left: auto;
		margin-right: auto;
	}

	.header image {
		width: 161upx;
		height: 161upx;
		border-radius: 50upx;
	}

	/* 主体 */
	.main {
		display: flex;
		flex-direction: column;
		padding-left: 70upx;
		padding-right: 70upx;
	}

	.main-list {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		height: 50upx;
		color: #333333;
		padding: 32upx;
		margin-top: 48upx;
	}

	.main-list .img {
		width: 32upx;
		height: 32upx;
		font-size: 32upx;
	}

	.main-list .main-input {
		flex: 1;
		text-align: left;
		font-size: 28upx;
		line-height: 100upx;
		padding-right: 10upx;
		margin-left: 20upx;
	}

	.vercode {
		color: rgba(0, 0, 0, 0.7);
		font-size: 24upx;
		line-height: 100upx;
	}

	.vercode-run {
		color: rgba(0, 0, 0, 0.4) !important;
	}

	.tips {
		color: #999999;
		font-size: 28upx;
		margin-top: 128upx;
		margin-left: 48upx;
	}

	.dlbutton {
		color: #FFFFFF;
		font-size: 30upx;
		width: 601upx;
		height: 100upx;
		background: #0081FF;
		border-radius: 2.5rem;
		line-height: 100upx;
		text-align: center;
		margin-left: auto;
		margin-right: auto;
		margin-top: 96upx;
	}

	/* 底部 */
	.footer {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 28upx;
		margin-top: 80upx;
		color: rgba(51, 51, 51, 0.7);
		text-align: center;
		height: 40upx;
		line-height: 40upx;
	}

	.footer text {
		font-size: 24upx;
		margin-left: 15upx;
		margin-right: 15upx;
	}


	/* 表单/按钮 阴影及圆边 */
	.oBorder {
		border: none;
		border-radius: 2.5rem;
		-webkit-box-shadow: 0 0 60upx 0 rgba(43, 86, 112, .1);
		box-shadow: 0 0 60upx 0 rgba(43, 86, 112, .1);
	}

	.oBorder_left {
		border: none;
		border-radius: 2.5rem 0 0 2.5rem;
		-webkit-box-shadow: 0 0 60upx 0 rgba(43, 86, 112, .1);
		box-shadow: 0 0 60upx 0 rgba(43, 86, 112, .1);
	}

	.oBorder_right {
		border: none;
		border-radius: 0 2.5rem 2.5rem 0;
		-webkit-box-shadow: 0 0 60upx 0 rgba(43, 86, 112, .1);
		box-shadow: 0 0 60upx 0 rgba(43, 86, 112, .1);
	}

	.buttonBorder {
		border: none;
		border-radius: 2.5rem;
		-webkit-box-shadow: 0 0 60upx 0 rgba(0, 0, 0, .2);
		box-shadow: 0 0 60upx 0 rgba(0, 0, 0, .2);
		-webkit-transition: all 0.4s cubic-bezier(.57, .19, .51, .95);
		-moz-transition: all 0.4s cubic-bezier(.57, .19, .51, .95);
		-ms-transition: all 0.4s cubic-bezier(.57, .19, .51, .95);
		-o-transition: all 0.4s cubic-bezier(.57, .19, .51, .95);
		transition: all 0.4s cubic-bezier(.57, .19, .51, .95);
	}
</style>
