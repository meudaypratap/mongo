grails {
    mongo {
        dbCreate = "update"
        host = "127.0.0.1"
        port = 27017
        options {
            autoConnectRetry = true
            connectTimeout = 3000
            connectionsPerHost = 40
            socketTimeout = 60000
            threadsAllowedToBlockForConnectionMultiplier = 5
            maxAutoConnectRetryTime = 5
            maxWaitTime = 120000
        }
    }
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
        }
        grails {
            mongo {
                username = "root"
                password = "igdefault"
//                databaseName = "sonysports_dev"
            }
        }
    }
}
