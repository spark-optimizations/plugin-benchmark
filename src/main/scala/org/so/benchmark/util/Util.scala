package org.so.benchmark.util

import java.io.{File, FileOutputStream, PrintStream}
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.SparkContext

import scala.io.Source

/**
  * @author shabbir.ahussain
  */
class Util(sc: SparkContext) {
  val DF = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss")
  val hadoopURI = "hdfs://ip-172-31-0-111.us-east-2.compute.internal:8020"

  def deleteFiles(fileName: String): Unit = {
    /** Deletes a regular file system file.
      *
      * @param file is the file to delete.
      */
    def deleteRecursively(file: File): Unit = {
      if (file.isDirectory)
        file.listFiles.foreach(deleteRecursively)
      if (file.exists && !file.delete)
        throw new Exception(s"Unable to delete ${file.getAbsolutePath}")
    }

    /** Deletes a hdfs file.
      *
      * @param fileName is the file name to delete.
      */
    def delHadoopFile(sc: SparkContext, fileName: String): Unit = {
      val fs = org.apache.hadoop.fs.FileSystem.get(new java.net.URI(hadoopURI), sc.hadoopConfiguration)
      fs.delete(new org.apache.hadoop.fs.Path(fileName), true)
    }

    if (fileName.startsWith("hdfs://"))
      delHadoopFile(sc, fileName)
    else
      deleteRecursively(new File(fileName))
  }


  /** Merges multiple files output from local file system into one file.
    *
    * @param file is the directory to start scanning.
    * @param headers is the list of headers to append in the end.
    * @param prefix is the prefix to search for in the given directory.
    */
  def mergeFiles(file: File, headers:Seq[String], prefix:String):Unit={
    if (!file.isDirectory) return

    val ps = new PrintStream(new FileOutputStream(file.getPath + "/" + prefix + "merged.csv"))
    ps.println(headers.mkString(";"))

    file.listFiles.foreach(d=> {
      if (d.isDirectory && d.getName.startsWith(prefix)) {
        d.listFiles.foreach(f=>{
          if (f.getName.startsWith("part-")) {
            Source.fromFile(f)
              .getLines
              .foreach(ps.println)
          }
        })
        deleteFiles(d.getAbsolutePath)
      }
    })
  }


}