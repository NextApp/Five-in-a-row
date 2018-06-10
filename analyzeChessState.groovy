String chessState = "";

num = 0
map = [:]

def printState(str, chess, deep) {
    if (deep == 1) {
//        println str
        map.put(str, true)
        num++
        return
    } else {
        if (chess == -1) {
            str += "B"
        } else if (chess == 1) {
            str += "W"
        } else if (chess == 0) {
            str += "A"
        }
    }
    printState(str, 1, deep - 1)
    printState(str, 0, deep - 1)
    printState(str, -1, deep - 1)
}

printState("", 1, 5)
printState("", 0, 5)
printState("", -1, 5)

//println num
mapK = [:]

map.each {
    k, v ->
        m = ""
        find = false
        for (c in k) {
            if (c == "B") {
//                println m + "B"
                mapK.put(m + "B", true)
                find = true
                break;
            } else {
                m += c
            }
        }
        if (!find) {
            mapK.put(m, true)
        }
}

mapK1 = [:]
mapK.each {
    k, v ->
        mapK1.put(k, v)
}
mapKK = [:]
mapK.each {
    k, v ->
        println k
        mapK1.each {
            k1, v1 ->
                mapKK.put(k.reverse() + "W" + k1, true)
        }
}



println mapK.size() * mapK1.size()
SCORE_LIAN_WU = 100000000
SCORE_HUO_SI = SCORE_LIAN_WU / 10
SCORE_CHONG_SI = SCORE_HUO_SI / 10
SCORE_HUO_SAN = SCORE_CHONG_SI / 10
SCORE_MIAN_SAN = SCORE_HUO_SAN / 10
SCORE_HUO_ER = SCORE_MIAN_SAN / 10
SCORE_MIAN_ER = SCORE_HUO_ER / 10
SCORE_HUO_YI = SCORE_MIAN_ER / 10
SCORE_MIAN_YI = SCORE_HUO_YI / 10

fiveAiMap = [
        "WWWWW"  : SCORE_LIAN_WU,

        "AWWWWA" : SCORE_HUO_SI,

        "BWWWWA" : SCORE_CHONG_SI,
        "WWWAW"  : SCORE_CHONG_SI,
        "WWAWW"  : SCORE_CHONG_SI,

        "AWWWAA" : SCORE_HUO_SAN,
        "AWWAWA" : SCORE_HUO_SAN,

        "BWWWAA" : SCORE_MIAN_SAN,
        "BWWAWA" : SCORE_MIAN_SAN,
        "BWAWWA" : SCORE_MIAN_SAN,
        "WWAAW"  : SCORE_MIAN_SAN,
        "WAWAW"  : SCORE_MIAN_SAN,
        "BAWWWAB": SCORE_MIAN_SAN,


        "AAWWAA" : SCORE_HUO_ER,
        "AWAWAA" : SCORE_HUO_ER,
        "AWAAWA" : SCORE_HUO_ER,

        "BWWAAA" : SCORE_MIAN_ER,
        "BWAWAA" : SCORE_MIAN_ER,
        "BWAAWA" : SCORE_MIAN_ER,
        "WAAAW"  : SCORE_MIAN_ER,

        "AAAWAA" : SCORE_HUO_YI,
        "AAWAAA" : SCORE_HUO_YI,
        "AWAAAA" : SCORE_HUO_YI,

        "WAAAA"  : SCORE_MIAN_YI,
        "AWAAA"  : SCORE_MIAN_YI,
        "AAWAA"  : SCORE_MIAN_YI,

]

def caculateMaxScore(k) {
    int max = 0;
    fiveAiMap.each {
        k1, v1 ->
            if (k.contains(k1) || k.contains(k1.reverse())) {
                max = max > v1 ? max : v1;
            }
    }
    return max;
}

str = ""
mapKK.each {
    k, v ->
        str += k + " " + caculateMaxScore(k) + "\n"
}

writeFile("ai.txt", str)

static def writeFile(fileName, str) {
    File file = new File(fileName)
    file.mkdirs()
    if (file.exists()) file.delete()
    def printWriter = file.newPrintWriter()
    printWriter.write(str)
    printWriter.flush()
    printWriter.close()
}
