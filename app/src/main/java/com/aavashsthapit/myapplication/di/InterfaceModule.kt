package com.aavashsthapit.myapplication.di

import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.aavashsthapit.myapplication.data.repo.StreamerRepo
import com.aavashsthapit.myapplication.data.repo.UserCache
import com.aavashsthapit.myapplication.data.repo.UserCacheImpl
import com.aavashsthapit.myapplication.ui.fragments.homeFragment.FilterStreamers
import com.aavashsthapit.myapplication.ui.fragments.homeFragment.FilterStreamersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Singleton
    @Binds
    abstract fun bindFilterStreamers(
        filterStreamersImpl: FilterStreamersImpl
    ): FilterStreamers

    @Singleton
    @Binds
    abstract fun bindRepo(
        fakeRepo: FakeRepo
    ): StreamerRepo

    @Singleton
    @Binds
    abstract fun bindUserCache(
        userCacheImpl: UserCacheImpl
    ): UserCache
}
