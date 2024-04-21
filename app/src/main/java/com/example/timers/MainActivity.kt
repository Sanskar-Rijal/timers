package com.example.timers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // variable for Timer which will be inirialized later
    var temp:Long=0
    private  var countDownTimer:CountDownTimer?=null
    // the duration of the timer in milliseconds
    private  var timerDuration:Long =60000
    // pauseOffset = timerDuration -time left
    private var pauseOffset :Long =0
    private  var tvTimer:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // tvTimer?.findViewById<TextView>(R.id.tv1)
        val btnstart:Button=findViewById(R.id.bt1)
        val btnpause:Button=findViewById(R.id.bt2)
        val reset:Button=findViewById(R.id.bt3)
        tvTimer=findViewById(R.id.tv1)
        tvTimer?.text="${(timerDuration/1000).toString()}"
        btnstart.setOnClickListener {
          //  Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            startTimer(pauseOffset)
        }
        btnpause.setOnClickListener {

            pauseTimer()
        }
        reset.setOnClickListener {
            resetTimer()
        }
    }
    /** function is used to start the timer of 60 seconds
     */
    private fun startTimer(pauseOffsetL:Long)
    {
        /**
         * @param millisINfuture the number of millis in the future from the to
         * {#start()} until the coutndown is done and {#onFinish()}
         * is called.
         * @param countDownInterval The interval along the way to receive
         * {#onTick(Long)} callbacks.
         */
        if(countDownTimer ==null) {
            countDownTimer = object : CountDownTimer(
                timerDuration - pauseOffsetL,
                1000
            )//1000 milliseconds==1 sec
            {
                override fun onTick(millisUntilFinished: Long)
                //millisuntilfinished is the duration that is still left
                //for the whole timer
                {
                    pauseOffset = timerDuration - millisUntilFinished//which is whatever time has passed
                    //timerDuration=millisUntilFinished
                    tvTimer?.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity, "timer is finished", Toast.LENGTH_SHORT)
                        .show()
                }
            }.start()
        }
        else
        {
            Toast.makeText(this@MainActivity,"Start button clicked",Toast.LENGTH_SHORT).show()
            countDownTimer?.cancel()
            countDownTimer = null
            startTimer(pauseOffsetL)
        }


    }
    private fun pauseTimer()
    {
        if(countDownTimer !=null)
        {
            countDownTimer!!.cancel()
        }
    }
    private fun resetTimer()
    {
        if (countDownTimer !=null)
        {
            countDownTimer!!.cancel()//if its not null cancel the current timer
            tvTimer?.text="${(timerDuration/1000).toString()}"//setting back to orginal timer i.e 60 seconds
            countDownTimer=null//setting back to null
            pauseOffset=0
        }
    }
}