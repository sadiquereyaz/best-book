package com.nabssam.bestbook.utils

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, text: Any) {
   Toast.makeText(context, text.toString(), Toast.LENGTH_SHORT).show()
}
fun showToastL(context: Context, text: Any) {
   Toast.makeText(context, text.toString(), Toast.LENGTH_LONG).show()
}