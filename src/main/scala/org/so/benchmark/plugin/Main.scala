package org.so.benchmark.plugin

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.so.benchmark.util.{Logger, Util}

/**
  * @author shabbir.ahussain
  */
object Main {
  val conf = new SparkConf()
    .setAppName(this.getClass.getName)
    .setMaster("local")
  val sc = new SparkContext(conf)
  sc.setLogLevel("ERROR")
  
  val util = new Util(sc)
  
  var outDir = "out/results/plugin/"
  var inDir = "input/"
  var timeFile = "results/"
  var runId = ""
  var numIter = 10
  var buffSize = 10000

  def main(args: Array[String]) {
    if (args.length > 0) inDir = args(0)
    if (args.length > 1) outDir = args(1)
    if (args.length > 2) timeFile = args(2)
    if (args.length > 3) runId = args(3)
    if (args.length > 4) numIter = args(4).toInt
    if (args.length > 5) buffSize = args(5).toInt
    
    val logr = new Logger(timeFile, buffSize, sc)

    val rdda = createTuple22(sc).persist()
    val rddb = rdda.mapValues[(Null)](x => null).persist()
    val c = new BenchmarkCases(rdda, rddb)

    // Warmup iteration
    timeNdSave(logr, "_0_0", c.tstJoinTuple_0_0)
    c.tstJoinTuple_0_0.count()
    c.tstJoinTuple_0_0.count()
    c.tstJoinTuple_0_0.count()

    // Actual iteration
    val rnd = new scala.util.Random
    for (i <- 1 to numIter) {
      print("Iter " + i + " of " + numIter + "\t:")
      val r = rnd.nextInt(27)
      r match {
        case 0 => timeNdSave(logr, "_1_0", c.tstJoinTuple_1_0)
        case 1 => timeNdSave(logr, "_1_1", c.tstJoinTuple_1_1)
        case 2 => timeNdSave(logr, "_5_0", c.tstJoinTuple_5_0)
        case 3 => timeNdSave(logr, "_5_1", c.tstJoinTuple_5_1)
        case 4 => timeNdSave(logr, "_5_5", c.tstJoinTuple_5_5)
        case 5 => timeNdSave(logr, "_10_0", c.tstJoinTuple_10_0)
        case 6 => timeNdSave(logr, "_10_1", c.tstJoinTuple_10_1)
        case 7 => timeNdSave(logr, "_10_5", c.tstJoinTuple_10_5)
        case 8 => timeNdSave(logr, "_10_10", c.tstJoinTuple_10_10)
        case 9 => timeNdSave(logr, "_15_0", c.tstJoinTuple_15_0)
        case 10 => timeNdSave(logr, "_15_1", c.tstJoinTuple_15_1)
        case 11 => timeNdSave(logr, "_15_5", c.tstJoinTuple_15_5)
        case 12 => timeNdSave(logr, "_15_10", c.tstJoinTuple_15_10)
        case 13 => timeNdSave(logr, "_15_15", c.tstJoinTuple_15_15)
        case 14 => timeNdSave(logr, "_20_0", c.tstJoinTuple_20_0)
        case 15 => timeNdSave(logr, "_20_1", c.tstJoinTuple_20_1)
        case 16 => timeNdSave(logr, "_20_5", c.tstJoinTuple_20_5)
        case 17 => timeNdSave(logr, "_20_10", c.tstJoinTuple_20_10)
        case 18 => timeNdSave(logr, "_20_15", c.tstJoinTuple_20_15)
        case 19 => timeNdSave(logr, "_20_20", c.tstJoinTuple_20_20)
        case 20 => timeNdSave(logr, "_22_0", c.tstJoinTuple_22_0)
        case 21 => timeNdSave(logr, "_22_1", c.tstJoinTuple_22_1)
        case 22 => timeNdSave(logr, "_22_5", c.tstJoinTuple_22_5)
        case 23 => timeNdSave(logr, "_22_10", c.tstJoinTuple_22_10)
        case 24 => timeNdSave(logr, "_22_15", c.tstJoinTuple_22_15)
        case 25 => timeNdSave(logr, "_22_20", c.tstJoinTuple_22_20)
        case 26 => timeNdSave(logr, "_22_22", c.tstJoinTuple_22_22)
        case _ =>
      }
    }
  }

  /** Times an exection and saves the output to file and print it.
    *
    * @param logr is the logger to use to record the results.
    * @param testCaseId is the directory to use to store the collected rdd.
    * @param rdd is the RDD to collect and save.
    */
  def timeNdSave(logr: Logger,
                 testCaseId: String,
                 rdd: RDD[(Long, Long)]): Unit = {

    def saveRDD(rdd: RDD[(Long, Long)], outFile: String)
    : Unit = rdd
      .sortByKey().coalesce(1, shuffle = false)
      .saveAsTextFile(outFile)
    
    val key = runId + testCaseId
    val outFile = outDir + testCaseId + "/" + runId
    
    print("Running " + key + ":")
    util.deleteFiles(outFile)
    val t0 = System.currentTimeMillis()
    saveRDD(rdd, outFile)
    val t1 = System.currentTimeMillis()

    val value = "\treal\t" + (t1 - t0) / 1000.0
    val tRow = key + value
//    scala.tools.nsc.io.File(timeFile).appendAll(tRow + "\n")
    println(tRow)
    logr.log(key, value)
  }

  /** Creates a tuple of 22 columns.
    *
    * @param sc is the spark context to use.
    * @return is the RDD of Tuple22 to created for testing.
    */
  def createTuple22(sc: SparkContext): RDD[(Long, (Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long))] = {
    sc.textFile(inDir + "similar_artists.csv.gz")
      .mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }
      .map(x => {
        val y = x.split(";")
        (y(0).hashCode.toLong, (
          y(1).charAt(0).hashCode().toLong,
          y(1).charAt(1).hashCode().toLong,
          y(1).charAt(2).hashCode().toLong,
          y(1).charAt(3).hashCode().toLong,
          y(1).charAt(4).hashCode().toLong,
          y(1).charAt(5).hashCode().toLong,
          y(1).charAt(6).hashCode().toLong,
          y(1).charAt(7).hashCode().toLong,
          y(1).charAt(8).hashCode().toLong,
          y(1).charAt(9).hashCode().toLong,
          y(1).charAt(10).hashCode().toLong,
          y(1).charAt(11).hashCode().toLong,
          y(1).charAt(12).hashCode().toLong,
          y(1).charAt(13).hashCode().toLong,
          y(1).charAt(14).hashCode().toLong,
          y(1).charAt(15).hashCode().toLong,
          y(1).charAt(16).hashCode().toLong,
          y(1).charAt(17).hashCode().toLong,
          y(1).charAt(1).hashCode().toLong,
          y(1).charAt(2).hashCode().toLong,
          y(1).charAt(3).hashCode().toLong,
          y(1).charAt(4).hashCode().toLong))
      })
  }
}