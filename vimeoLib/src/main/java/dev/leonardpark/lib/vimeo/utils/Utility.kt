package dev.leonardpark.lib.vimeo.utils

class Utility {
  companion object {
    fun isDigitsOnly(str: CharSequence): Boolean {
      val len = str.length
      for (i in 0 until len) {
        if (!Character.isDigit(str[i])) {
          return false
        }
      }
      return true
    }

    fun isEmpty(str: CharSequence?): Boolean {
      return str == null || str.isEmpty()
    }
  }
}