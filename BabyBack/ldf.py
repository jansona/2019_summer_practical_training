import pymysql
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import TfidfVectorizer, CountVectorizer
import jieba
import numpy as np
import sys
def getConnectData():
    # 创建连接
    conn = pymysql.connect(host='localhost', port=3306, user='root', passwd='', db='babyback')
    # 创建游标
    cursor = conn.cursor()
    cursor.execute("select id,baby_description,back_ground,miss_description," +
                   "native_place,other_description,other_explain,place from lost_baby")
    trainData = cursor.fetchall()
    trainData = np.array(trainData)
    idArr = trainData[:, 0].astype(np.int32)
    valueArr = trainData[:, 1:].astype(str)
    shape = valueArr.shape
    dataArr = []
    for i in range(shape[0]):
        string=''
        for j in range(shape[1]):
            string +=valueArr[i,j]+' '
        dataArr.append(string)
    return idArr, dataArr
# 获取停用词
def getStopWord():
    stopwords_file = "data/stop_word.txt"
    stop_f = open(stopwords_file, "r", encoding='utf-8')
    stop_words = list()
    for line in stop_f.readlines():
        line = line.strip()
        if not len(line):
            continue
        stop_words.append(line)
    stop_f.close()
    return stop_words
# 将指定目录下的文件进行分词
def fenci(path:str,outPath:str):
    stop_words = getStopWord()
    fr = open(path, 'r', encoding='utf-8')
    fw = open(outPath, 'w', encoding='utf-8')
    result = list()
    for line in fr.readlines():
        line = line.strip()
        if not len(line):
            continue
        outstr = ''
        seg_list = jieba.cut(line, cut_all=False)
        for myWord in seg_list:
            if myWord not in stop_words:
                if myWord != '\t':
                    outstr += myWord
                    outstr += " "

        result.append(outstr)
        fw.write(outstr+'\n')
    fw.close()
    return result

def fenci(data: list, outPath:str):
    stop_words = getStopWord()
    fw = open(outPath, 'w', encoding='utf-8')
    result = list()
    for line in data:
        line = line.strip()
        if not len(line):
            continue
        outstr = ''
        seg_list = jieba.cut(line, cut_all=False)
        for myWord in seg_list:
            if myWord not in stop_words:
                if myWord != '\t':
                    outstr += myWord
                    outstr += " "

        result.append(outstr)
        fw.write(outstr+'\n')
    fw.close()
    return result
# 返回已经分词完成的字符串数组
def getFileData(path:str):
    trainfile = open(path, "r", encoding="utf-8")  # 不同的documents用换行符隔开
    traincorpus = trainfile.readlines()
    trainfile.close()
    return traincorpus


# 训练得到TFModel
def getTfModel(path: str):
    corpus = getFileData(path)
    tfidf_vectorizer = TfidfVectorizer()
    transformer = TfidfTransformer()  # 该类会统计每个词语的tf-idf权值
    tfidf = transformer.fit_transform(
        tfidf_vectorizer.fit_transform(corpus))  # 第一个fit_transform是计算tf-idf，第二个fit_transform是将文本转为词频矩阵
    word = tfidf_vectorizer.get_feature_names()  # 获取词袋模型中的所有词语
    weight = tfidf.toarray()  # 将tf-idf矩阵抽取出来，元素a[i][j]表示j词在i类文本中的tf-idf权重
    f = open("data/res/tif.txt", "w+")
    for i in range(len(weight)):  # 打印每类文本的tf-idf词语权重，第一个for遍历所有文本，第二个for便利某一类文本下的词语权重
        #         print u"-------这里输出第",i,u"类文本的词语tf-idf权重------"
        f.write(str(i + 1) + "\t")
        for j in range(len(word)):
            if (weight[i][j] > 0): f.write(str(j + 1) + ":" + str(weight[i][j]) + " ")
        f.write("\n")
    f.close()
    f = open("data/res/dictionary.txt", "w+", encoding="utf-8")
    for i in range(len(word)):
        f.write(str(i) + "\t" + word[i] + "\n")
    f.close()
    return tfidf_vectorizer, transformer, tfidf

# # 得到匹配结果
# def getMatchRes(tfidfTrain,tfidfTest,trainData):
#     SimMatrix = (tfidfTrain * tfidfTest.T).A
#     shape=SimMatrix.shape
#     for i in range(0,shape[0]):
#         if SimMatrix[i]>0.1:
#             print(trainData[i],'被匹配')
# 得到匹配结果
def getMatchRes(tfidfTrain,tfidfTest,trainData,idArr:list):
    SimMatrix = (tfidfTrain * tfidfTest.T).A
    shape=SimMatrix.shape
    Res=[]
    for i in range(0,shape[0]):
        if SimMatrix[i]>0.1:
            # print(trainData[i],'被匹配')
            Res.append(idArr[i])
    return Res

# def main1():
#     fenci('data/details.txt', 'data/fenci.txt')
#     fenci('data/test.txt', 'data/fenci_test.txt')
#     tfidf_vectorizer, transformer, tfidf = getTfModel("data/fenci.txt")
#     trainData= getFileData("data/fenci.txt")
#     testData = getFileData("data/fenci_test.txt")
#     tfTest=transformer.transform(tfidf_vectorizer.transform(testData))
#     getMatchRes(tfidf,tfTest,trainData)

def main2(testArr: list):
    idArr, valueArr = getConnectData()
    fenci(valueArr, 'data/fenci.txt')
    fenci(testArr, 'data/fenci_test.txt')
    tfidf_vectorizer, transformer, tfidf = getTfModel("data/fenci.txt")
    trainData= getFileData("data/fenci.txt")
    testData = getFileData("data/fenci_test.txt")
    tfTest=transformer.transform(tfidf_vectorizer.transform(testData))
    Res=getMatchRes(tfidf,tfTest,trainData,idArr)
    for i in Res:
        print(i)


if __name__ == '__main__':
    try:
        main2(sys.argv[1:])
    except Exception:
        pass
