package dev.leonardpark.lib.vimeo

import androidx.annotation.Nullable
import dev.leonardpark.lib.vimeo.implement.OnExtractionListener
import dev.leonardpark.lib.vimeo.models.Video
import dev.leonardpark.lib.vimeo.utils.Parser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.annotations.NotNull
import java.io.IOException

class Extractor {
  fun fetchVideoWithURL(
    @NotNull videoURL: String,
    @Nullable referrer: String,
    @NotNull listener: OnExtractionListener
  ) {
    if (videoURL.isEmpty()) {
      listener.onFailure(IllegalArgumentException("Video URL cannot be empty"))
      return
    }

    val parser = Parser(videoURL)

    if (!parser.isURLValid()) {
      listener.onFailure(IllegalArgumentException("Vimeo URL is not valid"))
      return
    }

    val identifier = parser.getExtractedIdentifier()

    fetchVideoWithIdentifier(identifier, referrer, listener)
  }

  fun fetchVideoWithIdentifier(
    @NotNull identifier: String,
    @Nullable referrer: String,
    @NotNull listener: OnExtractionListener
  ) {
    if (identifier.isEmpty()) {
      listener.onFailure(IllegalArgumentException("Video identifier cannot be empty"))
      return
    }

    val manager = APIManager()
    try {
      manager.extractWithIdentifier(identifier, referrer).enqueue(object : Callback {
        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
          if (response.isSuccessful) {
            listener.onSuccess(Video(response.body!!.string()))
          } else {
            listener.onFailure(manager.getError(response))
          }
        }

        override fun onFailure(call: Call, e: IOException) {
          listener.onFailure(e)
        }
      })
    } catch (e: IOException) {
      listener.onFailure(e)
      e.printStackTrace()
    }
  }
}