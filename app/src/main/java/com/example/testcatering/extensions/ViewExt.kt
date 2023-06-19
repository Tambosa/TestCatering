package com.example.testcatering.extensions

import android.animation.Animator
import android.view.View

fun View.alphaAnimated(alpha: Float, duration: Long, onStart: () -> Unit, onFinish: () -> Unit) =
    this.apply {
        animate()
            .alpha(alpha)
            .setDuration(duration)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    onStart()
                }

                override fun onAnimationEnd(animation: Animator) {
                    onFinish()
                }

                override fun onAnimationCancel(animation: Animator) {
                    //nothing
                }

                override fun onAnimationRepeat(animation: Animator) {
                    //nothing
                }
            })
            .start()
    }