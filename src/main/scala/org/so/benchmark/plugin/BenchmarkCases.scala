package org.so.benchmark.plugin

import org.apache.spark.rdd.RDD

/**
  * @author shabbir.ahussain
  * @param rdda is the left RDD to use in join.
  * @param rddb is the right RDD to use in join.
  */
class BenchmarkCases(rdda: RDD[(Long, (Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long, Long))],
                     rddb: RDD[(Long, Null)]) {

  // Warmup
  def tstJoinTuple_0_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (null) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  def tstJoinTuple_1_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  def tstJoinTuple_1_1: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1) }
      .join(rddb)
      .mapValues { x => x._1 }
  }

  def tstJoinTuple_5_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  // 10 Column tuple

  def tstJoinTuple_5_1: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5) }
      .join(rddb)
      .mapValues { x => x._1._1 }
  }

  def tstJoinTuple_5_5: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 }
  }

  def tstJoinTuple_10_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  def tstJoinTuple_10_1: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10) }
      .join(rddb)
      .mapValues { x => x._1._1 }
  }

  // 15 Column tuple

  def tstJoinTuple_10_5: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 }
  }

  def tstJoinTuple_10_10: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 }

  }

  def tstJoinTuple_15_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  def tstJoinTuple_15_1: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15) }
      .join(rddb)
      .mapValues { x => x._1._1 }
  }

  def tstJoinTuple_15_5: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 }
  }

  def tstJoinTuple_15_10: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 }
  }

  def tstJoinTuple_15_15: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 + x._1._11 + x._1._12 + x._1._13 + x._1._14 + x._1._15 }
  }

  def tstJoinTuple_20_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._16, x._17, x._18, x._19, x._20) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  def tstJoinTuple_20_1: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._16, x._17, x._18, x._19, x._20) }
      .join(rddb)
      .mapValues { x => x._1._1 }
  }

  def tstJoinTuple_20_5: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._16, x._17, x._18, x._19, x._20) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 }
  }

  def tstJoinTuple_20_10: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._16, x._17, x._18, x._19, x._20) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 }
  }

  def tstJoinTuple_20_15: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._16, x._17, x._18, x._19, x._20) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 + x._1._11 + x._1._12 + x._1._13 + x._1._14 + x._1._15 }
  }

  def tstJoinTuple_20_20: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._16, x._17, x._18, x._19, x._20) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 + x._1._11 + x._1._12 + x._1._13 + x._1._14 + x._1._15 + x._1._16 + x._1._17 + x._1._18 + x._1._19 + x._1._20 }
  }

  def tstJoinTuple_22_0: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => 0 }
  }

  def tstJoinTuple_22_1: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => x._1._1 }
  }

  def tstJoinTuple_22_5: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 }
  }

  def tstJoinTuple_22_10: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 }
  }

  def tstJoinTuple_22_15: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 + x._1._11 + x._1._12 + x._1._13 + x._1._14 + x._1._15 }
  }

  def tstJoinTuple_22_20: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 + x._1._11 + x._1._12 + x._1._13 + x._1._14 + x._1._15 + x._1._16 + x._1._17 + x._1._18 + x._1._19 + x._1._20 }
  }

  def tstJoinTuple_22_22: RDD[(Long, Long)] = {
    rdda.mapValues { x => (x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, x._11, x._12, x._13, x._14, x._15, x._16, x._17, x._18, x._19, x._20, x._21, x._22) }
      .join(rddb)
      .mapValues { x => x._1._1 + x._1._2 + x._1._3 + x._1._4 + x._1._5 + x._1._6 + x._1._7 + x._1._8 + x._1._9 + x._1._10 + x._1._11 + x._1._12 + x._1._13 + x._1._14 + x._1._15 + x._1._16 + x._1._17 + x._1._18 + x._1._19 + x._1._20 + x._1._21 + x._1._22 }
  }
}