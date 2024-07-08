package campus.tech.kakao.map.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.doOnTextChanged(onTextChanged: (text: CharSequence?) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s)
        }

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }
    }
    addTextChangedListener(textWatcher)
    tag = textWatcher
}

fun EditText.clearTextWatcher() {
    (tag as? TextWatcher)?.let {
        removeTextChangedListener(it)
    }
}
