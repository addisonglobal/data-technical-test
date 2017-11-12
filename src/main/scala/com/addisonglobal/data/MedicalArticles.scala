package com.addisonglobal.data

import com.addisonglobal.data.MedicalData.MedicalArticle
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object MedicalArticles {

  /**
    * 1ST TASK
    * - Function that returns the list of ids of articles containing any of the terms
    */


  def articleIdsContaining(terms: Seq[String], rdd: RDD[MedicalArticle]): Seq[String] = ???


  /**
    * 2ND TASK
    * - Function that creates a reverse index which key is the term and the value is the set of articles containing that term
    * - Function that ranks the terms using the previous reverse index
    */


  /**
    * @return Whether the word is contained in the text
    */
  private def textContains(word: String, text: String): Boolean =
    text.split(" ").contains(word)

  /**
    * @return Which of the terms provided are contained in a given article
    */
  private def findTerms(terms: List[String], article: MedicalArticle): Seq[String] =
    terms.filter(textContains(_, article.text))

  def createIndex(terms: List[String], rdd: RDD[MedicalArticle]): RDD[(String, Iterable[MedicalArticle])] = ???

  def rankTermsUsingIndex(index: RDD[(String, Iterable[MedicalArticle])]): List[(String, Int)] = ???


  /**
    * 3RD TASK
    * - The former method is not optimal, specially when it comes to large datasets. Provide a better implementation
    */

  def rankTermsOptimized(langs: List[String], rdd: RDD[MedicalArticle]): List[(String, Int)] = ???


  def main(args: Array[String]): Unit = {

    val terms = List("Auditory", "Wrist", "Endoscopic", "Pulmonary", "Drainage")

    val conf: SparkConf = new SparkConf().setAppName("medical").setMaster("local[2]")
    val sc: SparkContext = new SparkContext(conf)

    val articlesRDD: RDD[MedicalArticle] = MedicalData.readData(sc).persist()

    val articlesContainingAnyTerm = articleIdsContaining(terms, articlesRDD)
    println(s"Total Articles Containing Any Term: ${articlesContainingAnyTerm.size}")

    val termsRankedReverseIndex = rankTermsUsingIndex(createIndex(terms, articlesRDD))
    println(s"Reverse Index Ranking: $termsRankedReverseIndex")

    val termsRankedCustom = rankTermsOptimized(terms, articlesRDD)
    println(s"Custom Ranking: $termsRankedCustom")
  }

}
