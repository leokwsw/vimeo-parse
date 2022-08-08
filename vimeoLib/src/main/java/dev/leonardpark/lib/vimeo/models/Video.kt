package dev.leonardpark.lib.vimeo.models

import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Video(json: String) {
  var title: String? = null
    private set
  var duration: Long = 0
    private set
  private val streams: MutableMap<String, String>
  private val thumbs: MutableMap<String, String>
  private var videoUser: User? = null

  private fun parseJson(json: String) {
    try {
      val requestJson = JSONObject(json)
      val videoInfo = requestJson.getJSONObject("video")
      duration = videoInfo.getLong("duration")
      title = videoInfo.getString("title")
      val userInfo = videoInfo.getJSONObject("owner")
      videoUser = User(userInfo)
      val thumbsInfo = videoInfo.getJSONObject("thumbs")
      val iterator: Iterator<String> = thumbsInfo.keys()
      while (iterator.hasNext()) {
        val key = iterator.next()
        thumbs[key] = thumbsInfo.getString(key)
      }
      val streamArray = requestJson.getJSONObject("request")
        .getJSONObject("files")
        .getJSONArray("progressive")
      for (streamIndex in 0 until streamArray.length()) {
        val stream = streamArray.getJSONObject(streamIndex)
        val url = stream.getString("url")
        val quality = stream.getString("quality")
        streams[quality] = url
      }
    } catch (e: JSONException) {
      e.printStackTrace()
    }
  }

  fun hasStreams(): Boolean {
    return streams.isNotEmpty()
  }

  val isHD: Boolean
    get() = streams.containsKey("1080p") || streams.containsKey("4096p")

  fun getStreams(): Map<String, String> {
    return streams
  }

  val sortingStreams: Map<String, String>
    get() {
      val treeMap: MutableMap<String, String> = TreeMap { o1, o2 ->
        o1.substring(
          0, o1.length - 1
        )
          .toInt().compareTo(
            o2.substring(
              0, o2.length - 1
            )
              .toInt()
          )
      }
      treeMap.putAll(streams)
      return treeMap
    }
  val sortingStreams2: Map<String, String>
    get() {
      val treeMap: MutableMap<String, String> = TreeMap { o1, o2 ->
        o2.substring(
          0, o2.length - 1
        )
          .toInt().compareTo(
            o1.substring(
              0, o1.length - 1
            )
              .toInt()
          )
      }
      treeMap.putAll(streams)
      return treeMap
    }

  fun hasThumbs(): Boolean {
    return thumbs.isNotEmpty()
  }

  fun getThumbs(): Map<String, String> {
    return thumbs
  }

  init {
    streams = HashMap()
    thumbs = HashMap()
    parseJson(json)
  }
}