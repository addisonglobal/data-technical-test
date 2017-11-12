package com.addisonglobal.data

import java.io.File

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object MedicalData {

  case class MedicalArticle(id: String, text: String)

  private[data] def filePath = {
    new File(this.getClass.getClassLoader.getResource("example_data.csv").toURI).getPath
  }

  private[data] def parse(line: String): MedicalArticle = {
    val i = line.indexOf(",")
    val id = line.substring(0, i)
    val text = line.substring(i + 1, line.length)
    val unquoted = if(text.startsWith("\"") && text.endsWith("\"")) text.substring(1, text.length - 1) else text
    MedicalArticle(id, unquoted)
  }

  private[data] def readData(sc: SparkContext): RDD[MedicalArticle] =
    sc.textFile(filePath).map(parse)

}
