# Technical Assesement

## Introduction

Welcome to the Data technical assesment.

The main goal of the exercise is to asses your approach to problem solving and knowledge of Spark Core.

### Brief
Your objective of the exercise is to rank a set of words based on their apperarance in some medical articles.

The articles are loaded in a CSV file and look like:

```text
120,"Occlusion of Middle Esophagus with Extraluminal Device, Open Approach"
121,"Transfer Vagus Nerve to Acoustic Nerve, Open Approach"
122,"Repair Cervicothoracic Vertebral Disc, External Approach"
```

When the job starts, it loads the data from the file and creates:

```scala
val articlesRDD: RDD[MedicalArticle] = MedicalData.readData(sc).persist()
```

Where the definition of MedicalArticle is as follows:

```scala
case class MedicalArticle(id: String, text: String)
```

For the assignments, we're considering the following list of terms to be ranked:

```scala
val terms = List("Auditory", "Wrist", "Endoscopic", "Pulmonary", "Drainage")
```

### 1st Assignment

Your **task** is to write a function that returns the list of ids of articles containing any of the terms:
```scala
def articleIdsContaining(terms: Seq[String], rdd: RDD[MedicalArticle]): Seq[String] = ???
```

### 2nd Assignment

For this assignments, we provide the following functions. You can choose to use them if you find them useful:

```scala
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
```

Your **task** is to write:
1. A function that creates a **reverse index** which key is the term and the value is the set of articles containing that term.
    ```scala
    def createIndex(terms: List[String], rdd: RDD[MedicalArticle]): RDD[(String, Iterable[MedicalArticle])] = ???
    ```
    The following tries to illustrate how the reverse index should look like:
    ```text
    "Auditory" -> [article_1, article_5, ...]
    ...
    "Drainage" -> [article_6, article_30, article_180, ...]
    ```
2. A function that **ranks the terms** using the previous reverse index.
    ```scala
    def rankTermsUsingIndex(index: RDD[(String, Iterable[MedicalArticle])]): List[(String, Int)] = ???
    ```
    The following tries to illustrate how the rank should look like:
    ```scala
    List((Endoscopic,298), (Drainage,113), (Pulmonary,14), (Wrist,8), (Auditory,6))
    ```
    
### 3rd Assignment

Your last **task** is to provide an **optimized implementation for ranking the terms**, specially when it comes to large datasets.
You'll also profive a justification for your solution.

```scala
def rankTermsOptimized(langs: List[String], rdd: RDD[MedicalArticle]): List[(String, Int)] = ???
```