package com.aavashsthapit.myapplication.di

import android.content.Context
import com.aavashsthapit.myapplication.R
import com.aavashsthapit.myapplication.data.repo.FakeRepo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Singleton
    @Provides
    @Named("test_repo")
    fun provideFakeRepo() = FakeRepo()

    @Singleton
    @Provides
    @Named("test_glide")
    fun provideGlideInstance(
            @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                    .placeholder(R.drawable.ic_cloud_off)
                    .error(R.drawable.ic_cloud_off)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
    )
}