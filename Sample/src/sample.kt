import com.presisco.gsonhelper.ArrayHelper

fun main(args : Array<String>){
/*    val src = mapOf(
            "mode" to "debug",
            "nimbus" to "localhost",
            "dbpool" to mapOf(
                    "mysql" to mapOf(
                            "dataSourceClassName" to "com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
                            "dataSource.serverName" to "localhost",
                            "dataSource.port" to "3306",
                            "dataSource.databaseName" to "106_data_collection",
                            "dataSource.user" to "root",
                            "dataSource.password" to "admin"
                    ),
                    "oracle" to mapOf(
                            "dataSourceClassName" to "oracle.jdbc.pool.OracleDataSource",
                            "dataSource.serverName" to "localhost",
                            "dataSource.port" to "1521",
                            "dataSource.driverType" to "thin",
                            "dataSource.databaseName" to "106_data_collection",
                            "dataSource.user" to "system",
                            "dataSource.password" to "admin"
                    )
            ),
            "topic" to "test-topic",
            "slaves" to arrayOf(
                    "1",
                    "2",
                    "3"
            ),
            "zkhosts" to "localhost",
            "zkroot" to "storm",
            "id" to "word"
    )*/

/*    val src = arrayOf<Any>(
            arrayOf<Any>(
                    1,
                    2,
                    3
            ),
            arrayOf<Any>(
                    "4",
                    "5",
                    "6"
            ),
            arrayOf<Any>(
                    true,
                    false,
                    true
            )
    )*/

    val src = arrayOf<Any>(
            mapOf(
                    "a" to 1,
                    "b" to "2",
                    "c" to false
            ),
            mapOf(
                    "a" to 3,
                    "b" to "4",
                    "c" to true
            ),
            mapOf(
                    "a" to 5,
                    "b" to "6",
                    "c" to false
            )
    )
/*    val helper = com.presisco.gsonhelper.ConfigMapHelper()
    helper.writeConfigMap("config.json",src)

    val dst : Map<String,Any> = helper.readConfigMap("config.json")*/

    val helper = ArrayHelper()
    val json = helper.toJson(src)
    println("json: "+json)
    val dst = helper.fromJson(json)

    for(item in dst){
        println("type of item: " + item::class.java.simpleName +
                ", item: " + item)
    }

/*    for(key in dst.keys){
        println("type of key: " + key::class.java.simpleName +
                ", key: " + key +
                ", type of value: " + dst[key]!!::class.java.simpleName +
                ", value: " + dst[key].toString())
    }*/
}