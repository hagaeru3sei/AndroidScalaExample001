package org.nmochizuki.androidscalaexample001

import android.content.{ComponentName, Context, Intent, ServiceConnection}
import android.os.{Bundle, IBinder}
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.{Button, TextView}
import org.nmochizuki.androidscalaexample001.ExampleIntentService.LocalBinder

class MainActivity extends AppCompatActivity {

  val TAG = "MainActivity"

  val conn = new ServiceConnection {
    override def onServiceDisconnected(componentName: ComponentName): Unit = {
      bound = false
    }
    override def onServiceConnected(componentName: ComponentName, iBinder: IBinder): Unit = {
      Log.d(TAG, "onServiceConnected")
      ServicePool.service = iBinder.asInstanceOf[LocalBinder].service
      bound = true
    }
  }

  var bound: Boolean = false

  override def onCreate(savedInstanceState: Bundle): Unit = {
    Log.d(TAG, "onCreate")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val button = findViewById(R.id.button_increment).asInstanceOf[Button]
    button.setOnClickListener(new View.OnClickListener {
      override def onClick(view: View): Unit = {
        ClickCountPool.count += ServicePool.service.rand
        Log.d(TAG, s"click count: ${ClickCountPool.count}")
        val textView = findViewById(R.id.count_text).asInstanceOf[TextView]
        textView.setText(s"${ClickCountPool.count}")
      }
    })
  }

  override def onStart(): Unit = {
    Log.d(TAG, "onStart")
    super.onStart()
    val intent = new Intent(this, classOf[ExampleIntentService])
    bindService(intent, conn, Context.BIND_AUTO_CREATE)
  }

  override def onStop(): Unit = {
    Log.d(TAG, "onStop")
    super.onStop()
    if (bound) {
      unbindService(conn)
      bound = false
    }
  }

}

object ClickCountPool {
  var count = 0
}

object ServicePool {
  var service = ExampleIntentService("pool")
}
