package com.merabk.moviesapp.presentation

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.merabk.moviesapp.R
import com.merabk.moviesapp.util.obtainStyledAttributes

class MultiStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var contentView: View? = null

    private var errorView: View? = null

    private var emptyView: View? = null

    var viewState: ViewState = ViewState.CONTENT
        set(value) {
            if (value != field) {
                field = value
                setView()
            }
        }

    init {
        attrs.obtainStyledAttributes(context, R.styleable.MultiStateView) {
            val inflater = LayoutInflater.from(getContext())
            val emptyViewResId = getResourceId(R.styleable.MultiStateView_empty_view, NO_VIEW)
            if (emptyViewResId > NO_VIEW) {
                val inflatedEmptyView = inflater.inflate(emptyViewResId, this@MultiStateView, false)
                emptyView = inflatedEmptyView
                addView(inflatedEmptyView, inflatedEmptyView.layoutParams)
            }
            val errorViewResId = getResourceId(R.styleable.MultiStateView_error_view, NO_VIEW)
            if (errorViewResId > NO_VIEW) {
                val inflatedErrorView = inflater.inflate(errorViewResId, this@MultiStateView, false)
                errorView = inflatedErrorView
                addView(inflatedErrorView, inflatedErrorView.layoutParams)
            }
            viewState = when (getInt(
                R.styleable.MultiStateView_view_state,
                ViewState.CONTENT.ordinal
            )) {
                ViewState.ERROR.ordinal -> ViewState.ERROR
                ViewState.EMPTY.ordinal -> ViewState.EMPTY
                else -> ViewState.CONTENT
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (contentView == null) throw IllegalArgumentException("Content view is not defined")
        when (viewState) {
            ViewState.CONTENT -> setView()
            else -> contentView?.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let { parcelable ->
            SavedState(parcelable, viewState)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            viewState = state.state
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun addView(child: View) {
        if (isValidContentView(child)) contentView = child
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index, params)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, width, height)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams,
        preventRequestLayout: Boolean
    ): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    private fun isValidContentView(view: View): Boolean {
        return if (contentView != null && contentView !== view) {
            false
        } else view != errorView && view != emptyView
    }

    private fun setView() {
        when (viewState) {
            ViewState.EMPTY -> {
                requireNotNull(emptyView).apply {
                    contentView?.visibility = GONE
                    errorView?.visibility = GONE
                    visibility = VISIBLE
                }
            }

            ViewState.ERROR -> {
                requireNotNull(errorView).apply {
                    contentView?.visibility = GONE
                    emptyView?.visibility = GONE
                    visibility = VISIBLE
                }
            }

            ViewState.CONTENT -> {
                requireNotNull(contentView).apply {
                    errorView?.visibility = GONE
                    emptyView?.visibility = GONE
                    visibility = VISIBLE
                }
            }
        }
    }

    enum class ViewState {
        CONTENT,
        ERROR,
        EMPTY,
    }

    companion object {
        private const val NO_VIEW = -1
    }

    private class SavedState : BaseSavedState {
        val state: ViewState

        constructor(superState: Parcelable, state: ViewState) : super(superState) {
            this.state = state
        }

        constructor(parcel: Parcel) : super(parcel) {
            state = parcel.readSerializable() as ViewState
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSerializable(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}