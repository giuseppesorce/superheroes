package com.giuseppesorce.vodafone.persistence.db.dao


import androidx.room.*
import com.giuseppesorce.vodafone.persistence.db.entities.*
/**
 * @author Giuseppe Sorce
 */

@Dao
interface HeroesDao {

    @Query("SELECT * FROM hero")
    suspend fun getHeroes(): List<RHero>

    @Query("SELECT * FROM hero where islike=:liked")
     suspend fun getLikedHeroes(liked:Int): List<RHero>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hero: RHero) :Long

    @Update
    fun update(hero: RHero)

    @Delete
    fun delete(hero: RHero): Int

    @Query("DELETE FROM hero")
    fun deleteAll()

    @Query("UPDATE hero SET islike = :isLike WHERE id = :id")
    fun changeLike(id: Int, isLike: Int)
}


