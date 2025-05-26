package com.example.yumifi1.features.recipe_details.interactor.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PHOTOS_TABLE
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PhotoEntity
import com.example.yumifi1.features.recipe_details.interactor.database.entity.PhotoEntity.Companion.PHOTO_ID

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("DELETE FROM $PHOTOS_TABLE WHERE $PHOTO_ID = :photoId")
    suspend fun deletePhoto(photoId: Long)
}