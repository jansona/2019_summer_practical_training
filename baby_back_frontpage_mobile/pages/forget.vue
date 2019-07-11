<template>
	<view class="forget">

		<view class="content">
			<!-- 主体 -->
			<view class="main">
				<view class="tips">若你忘记了密码，可在此重置新密码。</view>
				<view class="main-list oBorder">
					<input class="main-input" type="text" v-model="phoneData" maxlength="11" placeholder="请输入手机号" />
				</view>
				<view class="main-list oBorder">
					<input class="main-input" type="text" v-model="passData" maxlength="32" placeholder="请输入新密码" :password="!showPassword" />
					<image class="img" :class="showPassword?'cuIcon-attention':'cuIcon-attentionforbid'" @tap="isShowPass"></image>
				</view>
				<view class="main-list oBorder">
					<input class="main-input" type="number" v-model="verCode" maxlength="6" placeholder="验证码" />
					<view :class="['vercode',{'vercode-run': second>0}]" @tap="getVerCode()">{{ getVerCodeSecond }}</view>
				</view>
			</view>

			<button class="dlbutton buttonBorder" @tap="startRePass()">重置密码</button>

		</view>
	</view>
</template>

<script>
	var _this, countDown;

	export default {
		data() {
			return {
				phoneData: "", //电话
				passData: "", //密码
				showPassword: false, //密码是否显示
				second: 0, //倒计时
				verCode: "", //验证码
			}
		},
		mounted() {
			_this = this;
			clearInterval(countDown); //先清理一次循环，避免缓存
		},
		methods: {
			isShowPass() {
				//是否显示密码
				_this.showPassword = !_this.showPassword
			},
			getVerCode() {
				//获取验证码
				if (_this.phoneData.length != 11) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '手机号不正确'
					});
					return false;
				}
				if (_this.second > 0) {
					return false;
				}

				this.$api.get(this.URLS.sendForgetSMSUrl + "?tel=" + _this.phoneData).then(data => {
					console.log(data)
					if (data.data.rtnCode != 200) {
						uni.showToast({
							position: 'bottom',
							title: data.data.msg
						})
					} else {
						uni.showToast({
							position: 'bottom',
							title: '发送成功'
						})
						_this.second = 60;
						countDown = setInterval(function() {
							_this.second--;
							if (_this.second == 0) {
								clearInterval(countDown)
							}
						}, 1000)
					}
				}).catch(error => {
					this.myToast('网络错误')
				})
			},
			startRePass() {
				//重置密码
				if (this.phoneData.length != 11) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '手机号不正确'
					});
					return false;
				}
				if (this.passData.length < 6) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '密码不正确'
					});
					return false;
				}
				if (this.verCode.length != 6) {
					uni.showToast({
						icon: 'none',
						position: 'bottom',
						title: '验证码不正确'
					});
					return false;
				}
				console.log("重置密码成功")
				
				this.$api.post(this.URLS.resetPwdUrl+"?code="+this.verCode+"&tel="+this.phoneData,this.passData).then(data => {
					console.log(data)
					if(data.data.rtnCode != 200 ){
						this.myToast(data.data.msg)
					} else {
						this.myToast('重置密码成功!')
						this.setTimeout(function() {
							uni.navigateBack()
						}, 500);
					}
				}).catch(error => {
					this.myToast('网络错误')
				})
			}
		},
		computed: {
			getVerCodeSecond() {
				//验证码倒计时计算
				if (this.second <= 0) {
					return '获取验证码';
				} else {
					if (this.second < 10) {
						return '0' + this.second;
					} else {
						return this.second;
					}
				}

			}
		},
	}
</script>

<style>
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
		box-shadow: 0upx 0upx 60upx 0upx rgba(0, 0, 0, 0.1);
		border-radius: 50%;
		background-color: #000000;
		margin-top: 128upx;
		margin-bottom: 48upx;
		margin-left: auto;
		margin-right: auto;
	}

	.header image {
		width: 161upx;
		height: 161upx;
		border-radius: 50%;
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
		box-shadow: 0upx 0upx 13upx 0upx rgba(164, 217, 228, 0.4);
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
		color: rgba(0, 0, 0, 0.7);
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
