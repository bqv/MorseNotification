package com.ken.morse.base

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.PropertyChangeRegistry

open class ObservableViewModel : ViewModel(), Observable {
    private val callbacks = PropertyChangeRegistry()
    override fun addOnPropertyChangedCallback(
            callback: OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
            callback: OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyChange(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}