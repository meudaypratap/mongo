package com.mongo.util

import com.gmongo.GMongo
import com.mongodb.DB
import grails.util.Holders


class Util {

    static def getBean(String bean) {
        Holders.findApplicationContext().getBean(bean)
    }

    static DB getDB(String dbName) {
        GMongo mongo = getBean("mongo") as GMongo
        return mongo.getDB(dbName)
    }

    static GMongo getBlogDB() {
        getDB("blog")
    }
}
