package com.myntelligence

import java.nio.file.{Files, Paths}
import java.text.SimpleDateFormat
import java.util.Date

object FileSystemHelper {

  def getExistedDirPath() = {
    val datePattern = new SimpleDateFormat("YYYY/MM/dd/HH")
    val dirPath = "data/" + datePattern.format(new Date())
    Files.createDirectories(Paths.get(dirPath))
    dirPath
  }

  val fullPath = s"${getExistedDirPath()}/${Config.outFile}"
}
