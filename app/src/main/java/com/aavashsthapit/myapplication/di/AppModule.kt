package com.aavashsthapit.myapplication.di

import android.content.Context
import android.view.View
import androidx.appcompat.widget.SearchView
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.adapters.StreamersAdapter
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Module provides instance of:
 * 1. FakeRepo
 * 2. Glide
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFakeRepo() = FakeRepo

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_cloud_off)
            .error(R.drawable.ic_cloud_off)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

}