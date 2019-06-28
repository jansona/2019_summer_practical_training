let mode = 'dev'
let baseUrl = ''
if (mode === 'dev') {
  baseUrl = 'http://localhost:18080'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else {
}

const lostBabyInsertUrl = baseUrl + "/lost-baby/insert"
const matchBabyInsertUrl = baseUrl + "/MatchBaby/insert"
const uploadPictureUrl = baseUrl + "/file/temp"
const loginUrl = baseUrl + "/login"
const registUrl = baseUrl + "/user/insert"

const URLS = {
  lostBabyInsertUrl,
  uploadPictureUrl,
  loginUrl,
  registUrl,
  matchBabyInsertUrl
}

module.exports = URLS
