package com.blueamber.studentcalendar.domain.local

import androidx.room.*
import android.database.Cursor
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.PrimaryGroups
import com.blueamber.studentcalendar.models.TasksCalendar
import java.util.*


interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg obj: T)

    /**
     * Insert a list of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
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
interface TasksCalendarDao : BaseDao<TasksCalendar> {

    @Query("SELECT * FROM TasksCalendar")
    fun getTasks(): List<TasksCalendar>

    @Query("SELECT * FROM TasksCalendar WHERE date > :dateBegin")
    fun getTasksAfterDate(dateBegin: Date): List<TasksCalendar>

    @Query("DELETE FROM TasksCalendar WHERE type!=\"personnel\"")
    fun deleteTasks()

    // ** Special content provider ** //

    @Query("SELECT * FROM TasksCalendar WHERE date > :dateBegin AND date < :dateEnd")
    fun selectAllBeginTodayForTwoWeek(dateBegin: Date, dateEnd: Date): Cursor
}

@Dao
interface GroupsDao : BaseDao<Groups> {

    @Query("SELECT * FROM Groups")
    fun getGroups(): List<Groups>

    @Query("DELETE FROM Groups")
    fun deleteGroups()

    @Query("SELECT newGroups FROM Groups WHERE originalGroups = :group")
    fun getNewGroupsByOriginal(group: String): String

    @Query("UPDATE Groups SET visibility = :newVisibility WHERE originalGroups = :group")
    fun updateVisibility(newVisibility: Boolean, group: String)

    @Query("UPDATE Groups SET newGroups = :newGroup WHERE originalGroups = :group")
    fun updateNewGroup(newGroup: String, group: String)
}

@Dao
interface PrimaryGroupsDao : BaseDao<PrimaryGroups> {

    @Query("SELECT * FROM PrimaryGroups")
    fun getPrimaryGroups(): List<PrimaryGroups>

    @Query("DELETE FROM PrimaryGroups")
    fun deletePrimaryGroups()

    @Query("SELECT newPrimaryGroup FROM PrimaryGroups WHERE originalPrimaryGroup = :group")
    fun getNewPrimaryGroupsByOriginal(group: String): String

    @Query("UPDATE PrimaryGroups SET visibility = :newVisibility WHERE originalPrimaryGroup = :group")
    fun updateVisibility(newVisibility: Boolean, group: String)

    @Query("UPDATE PrimaryGroups SET newPrimaryGroup = :newPrimaryGroup WHERE originalPrimaryGroup = :primaryGroup")
    fun updateNewPrimaryGroup(newPrimaryGroup: String, primaryGroup: String)
}