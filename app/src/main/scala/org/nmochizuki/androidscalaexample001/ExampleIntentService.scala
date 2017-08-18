package org.nmochizuki.androidscalaexample001

import android.app.IntentService
import android.content.Intent
import android.os.{Binder, IBinder}
import android.util.Log
import android.widget.Toast

import scala.util.Random


class ExampleIntentService(name: String) extends IntentService(name) {

  val TAG = "ExampleIntentService"

  @volatile private var allowBind = false
  @volatile private var num = 0

  def this() {
    this("ExampleIntentService")
  }

  override def onCreate(): Unit = {
    Log.d(TAG, "Start service")
    super.onCreate()
  }

  override def onHandleIntent(intent: Intent): Unit = {
    Log.d(TAG, s"onHandleIntent. intent: $intent")
  }

  override def onStartCommand(intent: Intent, flags: Int, startId: Int): Int = {
    Log.d(TAG, "onStartCommand")
    Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
    super.onStartCommand(intent, flags, startId)
  }

  override def onBind(intent: Intent): IBinder = {
    Log.d(TAG, "onBind")
    allowBind = false
    ExampleIntentService.binder
  }

  override def onUnbind(intent: Intent): Boolean = {
    Log.d(TAG, "onUnbind")
    allowBind
  }

  override def onDestroy(): Unit = {
    Log.d(TAG, "onDestroy")
    super.onDestroy()
    allowBind = true
  }

  def run(): Unit = {
    while (!allowBind) {
      num = Random.nextInt(10)
      Log.d(TAG, s"onStartCommand loop. num=$num")
      Thread.sleep(5000)
    }
  }

  def value = num

}

object ExampleIntentService {

  def apply: ExampleIntentService = new ExampleIntentService

  def apply(name: String): ExampleIntentService = {
    Log.d("ExampleIntentService", s"called apply. name: $name")
    new ExampleIntentService(name)
  }

  private val binder = new LocalBinder

  class LocalBinder extends Binder {
    def service = ExampleIntentService.apply
  }

}

