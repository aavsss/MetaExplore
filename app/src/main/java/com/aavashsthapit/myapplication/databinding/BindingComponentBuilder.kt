package com.aavashsthapit.myapplication.databinding

import dagger.hilt.DefineComponent

@DefineComponent.Builder
interface BindingComponentBuilder {
    fun build(): BindingComponent
}
