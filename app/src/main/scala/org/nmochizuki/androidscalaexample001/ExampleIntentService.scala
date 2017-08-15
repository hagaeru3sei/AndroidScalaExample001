package org.nmochizuki.androidscalaexample001

import android.app.IntentService
import android.content.Intent
import android.os.{Binder, IBinder}
import android.util.Log
import android.widget.Toast

import scala.util.Random


class ExampleIntentService(name: String) extends IntentService(name) {

  val TAG = "ExampleIntentService"

  def this() {
    this("ExampleIntentService")
  }

  override def onBind(intent: Intent): IBinder = ExampleIntentService.binder

  override def onCreate(): Unit = {
    Log.d(TAG, "Start service")
  }

  override def onHandleIntent(intent: Intent): Unit = {
    Log.d(TAG, "onHandleIntent")
    Thread.sleep(5000)
  }

  override def onStartCommand(intent: Intent, flags: Int, startId: Int): Int = {
    Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
    super.onStartCommand(intent, flags, startId)
    Log.d(TAG, "onStartCommand")
  }

  def rand: Int = Random.nextInt(10)
}

object ExampleIntentService {

  def apply: ExampleIntentService = new ExampleIntentService("ExampleIntentService")

  def apply(name: String): ExampleIntentService = {
    Log.d("ExampleIntentService", s"called apply. name: $name")
    new ExampleIntentService(name)
  }

  private val binder = new LocalBinder

  class LocalBinder extends Binder {
    def service = ExampleIntentService("local binder")
  }

}

