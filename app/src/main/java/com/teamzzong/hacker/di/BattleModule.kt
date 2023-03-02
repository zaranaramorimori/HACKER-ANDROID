package com.teamzzong.hacker.di

import com.teamzzong.hacker.data.datasource.BattleDataSource
import com.teamzzong.hacker.data.datasource.remote.BattleDataSourceImpl
import com.teamzzong.hacker.data.remote.BattleService
import com.teamzzong.hacker.data.repository.BattleRepositoryImpl
import com.teamzzong.hacker.domain.repository.battle.BattleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BattleModule {

    @Provides
    @Singleton
    fun provideBattleService(retrofit: Retrofit): BattleService = retrofit.create(BattleService::class.java)

    @Singleton
    @Provides
    fun provideBattleDataSource(battleDataSource: BattleDataSourceImpl): BattleDataSource = battleDataSource

    @Provides
    @Singleton
    fun provideBattleRepository(
        battleRepository: BattleRepositoryImpl): BattleRepository = battleRepository
}
