package com.nullpointer.dogedex.core.delegate

import androidx.lifecycle.SavedStateHandle
import kotlin.reflect.KProperty

class SavableProperty<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defaultValue: T,
) {
    private var _property = savedStateHandle[key] ?: defaultValue


    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        return _property
    }

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        _property = value
        savedStateHandle[key] = value
    }
}