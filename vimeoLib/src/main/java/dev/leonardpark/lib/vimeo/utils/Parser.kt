package dev.leonardpark.lib.vimeo.utils

class Parser(
  var url: String
) {

  fun isURLValid(): Boolean {
    val videoID = getExtractedIdentifier()
    return videoID.isNotEmpty() && Utility.isDigitsOnly(videoID)
  }

  fun getExtractedIdentifier(): String {
    val urlParts = url.split("/")
    return if (urlParts.isEmpty()) {
      ""
    } else urlParts[urlParts.size - 1]
  }

}