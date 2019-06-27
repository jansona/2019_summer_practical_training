let mode = 'dev'
let baseUrl = ''
if (mode === 'dev') {
  baseUrl = 'http://localhost:18080'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else {
}

const fillFormUrl = baseUrl + "/lost-baby/insert"
const uploadPictureUrl = baseUrl + "/file/temp"
const URLS = {
  fillFormUrl,
  uploadPictureUrl,
}

module.exports = URLS
