package org.nmochizuki.androidscalaexample001

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity extends AppCompatActivity {

  val TAG = "MainActivity"

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val button = findViewById(R.id.button_increment)
    button.setOnClickListener(new View.OnClickListener {
      override def onClick(view: View): Unit = {
        ClickCountPool.count += 1
        Log.d(TAG, s"click count: ${ClickCountPool.count}")
        val textView = findViewById(R.id.count_text).asInstanceOf[TextView]
        textView.setText(s"${ClickCountPool.count}")
      }
    })
  }

}

object ClickCountPool {
  var count = 0
}
