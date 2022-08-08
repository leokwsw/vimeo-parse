package dev.leonardpark.lib.vimeo

import androidx.annotation.Nullable
import dev.leonardpark.lib.vimeo.utils.Utility
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException

class APIManager {
  companion object {
    const val URL = "https://vimeo.com/%s"
    const val CONFIG_URL = "https://player.vimeo.com/video/%s/config"
  }

  @Throws(IOException::class)
  fun extractWithIdentifier(@NotNull identifier: String, @Nullable referrer: String): Call =
    OkHttpClient().newCall(
      Request.Builder()
        .url(String.format(CONFIG_URL, identifier))
        .header("Content-Type", "application/json")
        .header(
          "Referer", if (Utility.isEmpty(referrer)) {
            String.format(URL, identifier)
          } else {
            referrer
          }
        )
        .build()
    )

  fun getError(response: Response): Throwable {
    return when (response.code) {
      404 -> IOException("Video could not be found")
      403 -> IOException("Video has restricted playback")
      else -> IOException("An unknown error occurred : response $response")
    }
  }
}