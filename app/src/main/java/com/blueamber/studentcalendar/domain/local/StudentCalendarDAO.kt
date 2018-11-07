package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.*
import com.blueamber.studentcalendar.models.Day
import java.util.*
import android.arch.persistence.room.RoomMasterTable.TABLE_NAME
import android.database.Cursor


interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T)

    /**
     * Insert a list of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)
}

@Dao
interface DayDao : BaseDao<Day> {

    @Query("SELECT * FROM Day")
    fun getDays() : List<Day>

    @Query("SELECT * FROM Day WHERE date > :dateBegin")
    fun getDaysAfterDate(dateBegin: Date) : List<Day>

    @Query("DELETE FROM Day")
    fun deleteDays()

    // ** Special content provider ** //

    @Query("SELECT * FROM Day WHERE date > :dateBegin")
    fun selectAllBeginToday(dateBegin: Date): Cursor

    @Query("SELECT COUNT(*) FROM Day")
    fun countDayWithData(): Cursor
}