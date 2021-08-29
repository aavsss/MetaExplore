package com.aavashsthapit.myapplication.di

import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.data.repo.StreamerRepo
import com.aavashsthapit.myapplication.ui.fragments.homeFragment.FilterStreamers
import com.aavashsthapit.myapplication.ui.fragments.homeFragment.FilterStreamersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    abstract fun bindFilterStreamers(
        filterStreamersImpl: FilterStreamersImpl
    ): FilterStreamers

    @Binds
    abstract fun bindRepo(
        fakeRepo: FakeRepo
    ): StreamerRepo
}
