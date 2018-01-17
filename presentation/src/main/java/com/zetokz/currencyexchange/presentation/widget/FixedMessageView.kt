package com.zetokz.currencyexchange.presentation.widget

import android.content.Context
import android.support.transition.Fade
import android.support.transition.Slide
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.zetokz.currencyexchange.R
import com.zetokz.currencyexchange.presentation.util.extension.bindView

class FixedMessageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val buttonAction: Button by bindView(R.id.button_action)
    private val progress: ProgressBar by bindView(R.id.progress)
    private val textMessage: TextView by bindView(R.id.text_message)

    internal var actionClickListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.widget_fixed_message, this)
        buttonAction.setOnClickListener { actionClickListener?.invoke() }

        if (!isInEditMode) visibility = View.GONE

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.FixedMessageView)
            if (a.hasValue(R.styleable.FixedMessageView_text)) {
                textMessage.text = a.getString(R.styleable.FixedMessageView_text)
            }
            if (a.hasValue(R.styleable.FixedMessageView_actionText)) {
                buttonAction.text = a.getString(R.styleable.FixedMessageView_actionText)
            }
            a.recycle()
        }
    }

    fun show() {
        TransitionManager.beginDelayedTransition(parent as ViewGroup, Slide(Gravity.TOP).addTarget(this))
        visibility = View.VISIBLE
    }

    fun dismiss() {
        TransitionManager.beginDelayedTransition(parent as ViewGroup, Slide(Gravity.TOP).addTarget(this))
        visibility = View.GONE
    }

    fun showProgress() {
        TransitionManager.beginDelayedTransition(this, Fade().addTarget(progress))
        progress.visibility = View.VISIBLE
        buttonAction.visibility = View.INVISIBLE
    }

    fun hideProgress() {
        TransitionManager.beginDelayedTransition(this, Fade().addTarget(buttonAction))
        progress.visibility = View.GONE
        buttonAction.visibility = View.VISIBLE
    }

    fun shake() {
        val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
        startAnimation(shakeAnimation)
    }
}