package dev.leonardpark.lib.vimeo.implement

import dev.leonardpark.lib.vimeo.models.Video

interface OnExtractionListener {
  fun onSuccess(video: Video)
  fun onFailure(throwable: Throwable)
}