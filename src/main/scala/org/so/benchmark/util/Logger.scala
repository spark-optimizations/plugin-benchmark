package org.so.benchmark.util

import org.apache.spark.SparkContext

import scala.collection.mutable

/** Logger used for multipurpose logging.
  * @author shabbir.ahussain
  * @param path is the base directory path to use.
  * @param buffSize is the size of buffer to keep.
  * @param sc is the spark context used to create new files.
  */
class Logger(path:String, buffSize: Int, sc: SparkContext) {
  val separator = "\t"
  var ctr = 0
  var q = new mutable.ListBuffer[String]

  /** Logs the given key value pair.
    *
    * @param key is the key to log.
    * @param value is the string value to log.
    */
  def log(key: String, value: String): Unit = {
    q.append(key + separator + value)
    ctr += 1
    if (ctr >= buffSize)
      flush()
  }

  /**
    * Flushes data using the spark context parallelize and saves it as textfile.
    */
  private def flush(): Unit = {
    println("Flushing...")
    ctr = 0
    sc.parallelize(q)
      .coalesce(1, shuffle = true)
      .saveAsTextFile(path + "/" + System.currentTimeMillis.toString)
    q.clear()
  }
}
