package com.aavashsthapit.myapplication.databinding

import androidx.databinding.DataBindingComponent
import com.aavashsthapit.myapplication.databinding.scope.BindingScope
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@BindingScope
@DefineComponent(parent = SingletonComponent::class)
interface BindingComponent : DataBindingComponent