let model = 'dev'
let baseUrl = 'http://localhost:18080'
if (model === 'dev') {
  baseUrl = 'http://localhost:18080'
} else if (model == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else {
}

let imgUrl = 'http://42.159.4.66:2333'

// 填写信息部分
const lostBabyInsertUrl = baseUrl + "/lost-baby/insert"
const lostBabyFindUrl = baseUrl + "/lost-baby/find"
const matchBabyInsertUrl = baseUrl + "/match-baby/insert"
const matchBabyFindUrl = baseUrl + "/match-baby/find"
const uploadPictureUrl = baseUrl + "/file/upload"

// 登录注册部分
const loginUrl = baseUrl + "/login"
const registUrl = baseUrl + "/register"
const sendSMSUrl = baseUrl + "/sms-verify"

// 论坛部分
const articleFindUrl = baseUrl + '/article/find'
const articleDeleteUrl = baseUrl + '/article/delete'
const articleFindByUserUrl = baseUrl + '/article/find-by-user'
const articleFindByUserNameUrl = baseUrl + '/article/find-by-username'
const articleInsertUrl = baseUrl + '/article/insert'
//评论部份
const commentFindUrl = baseUrl + '/comment/find'
const commentDeleteUrl = baseUrl + '/comment/delete'
const commentFindByArticleUrl = baseUrl + '/comment/find-by-article'
const commentFindByUserUrl = baseUrl + '/comment/find-by-user'
const commentInsertUrl = baseUrl + '/comment/insert'

//查询用户信息
const userFindUrl = baseUrl + '/user/find'
const userFindByIdUrl = baseUrl + '/user/find-by-id'

const URLS = {
	imgUrl,
  baseUrl,
  lostBabyInsertUrl,
  uploadPictureUrl,
  loginUrl,
  registUrl,
  matchBabyInsertUrl,
  lostBabyFindUrl,
  matchBabyFindUrl,
  sendSMSUrl,
  articleFindUrl,
  articleDeleteUrl,
  articleFindByUserUrl,
	articleFindByUserNameUrl,
  articleInsertUrl,
	commentFindUrl,
	commentDeleteUrl,
	commentFindByArticleUrl,
	commentFindByUserUrl,
	commentInsertUrl,
  userFindUrl,
	userFindByIdUrl
}

module.exports = URLS