from sklearn.feature_extraction.text import CountVectorizer
from sklearn.decomposition import LatentDirichletAllocation
from sklearn.externals import joblib
import os
from time import time
import jieba
n_topics = list(range(3, 10, 1))
perplexityLst = [1.0] * len(n_topics)
n_top_words = 40


def print_top_words(model, feature_names, n_top_words):
    for topic_idx, topic in enumerate(model.components_):
        print("Topic #%d:" % topic_idx)
        print(" ".join([feature_names[i]
                        for i in topic.argsort()[:-n_top_words - 1:-1]]))
    print()
    with open(os.path.join('result', 'res_topic_word.csv'), 'w') as f:
        f.write("Topic, Top Word\n")
        for topic_idx, topic in enumerate(model.components_):
            f.write(str(topic_idx) + ',')
            topic_word_dist = [(feature_names[i], topic[i]) for i in topic.argsort()[:-n_top_words - 1:-1]]
            for word, score in topic_word_dist:
                f.write(word + '#' + str(score) + ';')
            f.write('\n')


def lda(data):
    tf_ModelPath = os.path.join('model', 'tfVector.model')  # 保存词频模型
    lda_ModelPath = os.path.join('model', 'ldaModels.model')  # 保存训练的lda模型
    bestModelPath = os.path.join('model', 'bestLDAModel.model')
    tf_vectorizer = CountVectorizer(max_df=0.95, min_df=2,)
    tf = tf_vectorizer.fit_transform(data)

    lda_models = []
    for idx, n_topic in enumerate(n_topics):
        lda = LatentDirichletAllocation(n_components = n_topic,
                                        max_iter=8000,
                                        learning_method='batch',
                                        evaluate_every=200,
                                        perp_tol=0.01)
        t0 = time()
        lda.fit(tf)
        perplexityLst[idx] = lda.perplexity(tf)
        lda_models.append(lda)
    print("残差数组结果为：", perplexityLst)
    print("# of Topic: %d, " % n_topics[idx], end=' ')
    print("done in %0.3fs, N_iter %d, " % ((time() - t0), lda.n_iter_), end=' ')
    print("Perplexity Score %0.3f" % perplexityLst[idx])

    # 打印最佳模型
    best_index = perplexityLst.index(min(perplexityLst))
    best_n_topic = n_topics[best_index]
    best_model = lda_models[best_index]
    print("Best # of Topic: ", best_n_topic)
    print("Best Model: ")

    # 保存每个n_topics下的LDA模型，以便后续查看使用
    joblib.dump(tf_vectorizer, tf_ModelPath)
    joblib.dump(lda_models, lda_ModelPath)
    joblib.dump(best_model, bestModelPath)

    # 保存并输出topic_word矩阵
    print("#########Topic-Word Distribution#########")
    tf_vectorizer._validate_vocabulary()
    tf_feature_names = tf_vectorizer.get_feature_names()
    print_top_words(best_model, tf_feature_names, n_top_words)
    # print(docres)
    # joblib.dump(tf_vectorizer, tf_ModelPath)
    return best_model, tf_vectorizer


def getStopWord():
    stopwords_file = "data/stop_word.txt"

    stop_f = open(stopwords_file, "r", encoding='utf-8')
    stop_words = list()
    for line in stop_f.readlines():
        line = line.strip()
        if not len(line):
            continue
        stop_words.append(line)
    stop_f.close
    return stop_words

def fenci(path:str,outPath:str):
    stop_words=getStopWord()
    f = open(path, 'r', encoding='utf-8')
    fw=open(outPath, 'w', encoding='utf-8')
    result = list()
    for line in f.readlines():
        line = line.strip()
        if not len(line):
            continue
        outstr = ''
        seg_list = jieba.cut(line, cut_all=False)
        for word in seg_list:

            if word not in stop_words:
                if word != '\t':
                    outstr += word
                    outstr += " "
                    # seg_list = " ".join(seg_list)

        result.append(outstr)
        fw.write(outstr+'\n')
    fw.close()
    return result



def getData(path: str):
    fr = open(path, 'r', encoding='utf-8')
    line = fr.readline()
    trainData = []
    i = 1
    while line != None and line != '':

        trainData.append(line)
        i += 1
        line = fr.readline()
    return trainData


def trian():
    modelPath = 'model'
    resPath = 'result'
    if not os.path.exists(modelPath):
        os.makedirs(modelPath)
        os.makedirs(resPath)

    trainData = getData('data/fenci.txt')
    model, vec = lda(trainData)
    res = model.transform(vec.transform(trainData))
    print(res)


def getBestLDA():
    tf_ModelPath = os.path.join('model', 'tfVector.model')  # 保存词频模型
    bestModelPath = os.path.join('model', 'bestLDAModel.model')
    tf_vectorizer = joblib.load(tf_ModelPath)
    lda = joblib.load(bestModelPath)
    return lda, tf_vectorizer

def test():
    testData = fenci('data/test.txt', 'data/fenci_test.txt')
    model, vec = getBestLDA()
    trainData = getData('data/fenci.txt')
    trainRes = model.transform(vec.transform(trainData))
    testRes = model.transform(vec.transform(testData))
    for i in range(0, len(testRes[0])):
        if(testRes[0][i]>0.6):
            for j in range(0,len(trainRes)):

                if trainRes[j][i]>0.6:
                    print(trainData[j])

# trian()
#fenci('data/1.txt','data/fenci.txt')
test()