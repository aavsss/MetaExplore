package com.aavashsthapit.myapplication.databinding

import com.aavashsthapit.myapplication.databinding.scope.BindingScope
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@BindingScope
@InstallIn(BindingComponent::class)
interface DataBindingEntryPoint {

    @BindingScope
    fun getBindingProperties(): BindingProperties
}
