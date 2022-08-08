package dev.leonardpark.lib.vimeo.models

import org.json.JSONObject

class User(userObject: JSONObject) {
  var accountType: String? = null
    private set
  var name: String? = null
    private set
  var imageUrl: String? = null
    private set
  var image2xUrl: String? = null
    private set
  var url: String? = null
    private set
  var id: Long = 0
    private set

  init {
    accountType = userObject.optString("account_type")
    name = userObject.optString("name")
    imageUrl = userObject.optString("img")
    image2xUrl = userObject.optString("img_2x")
    url = userObject.optString("url")
    id = userObject.optLong("id")
  }
}