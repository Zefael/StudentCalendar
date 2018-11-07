package com.blueamber.studentcalendar.domain.local

import android.arch.persistence.room.*
import android.database.Cursor
import com.blueamber.studentcalendar.models.TasksCalendar
import java.util.*


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
interface TastsCalendarDao : BaseDao<TasksCalendar> {

    @Query("SELECT * FROM TasksCalendar")
    fun getDays(): List<TasksCalendar>

    @Query("SELECT * FROM TasksCalendar WHERE date > :dateBegin")
    fun getDaysAfterDate(dateBegin: Date): List<TasksCalendar>

    @Query("DELETE FROM TasksCalendar")
    fun deleteDays()

    // ** Special content provider ** //

    @Query("SELECT * FROM TasksCalendar WHERE date > :dateBegin")
    fun selectAllBeginToday(dateBegin: Date): Cursor

    @Query("SELECT COUNT(*) FROM TasksCalendar")
    fun countDayWithData(): Cursor
}