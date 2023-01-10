package com.samsung.healthcare.dataqueryservice.application.service

internal const val SHOW_TABLE_QUERY = "SHOW TABLES"

internal const val TASK_ID_COLUMN = "task_id"
internal const val AVERAGE_COMPLETION_TIME_COLUMN = "avg_completion_time"
internal const val AVERAGE_COMPLETION_TIME_QUERY =
    """
       SELECT $TASK_ID_COLUMN, avg(date_diff('millisecond', started_at, submitted_at)) as $AVERAGE_COMPLETION_TIME_COLUMN
       FROM task_results
    """

internal const val NUMBER_OF_RESPONDED_USERS = "num_users_responded"
internal const val TASK_RESPONSE_COUNT_QUERY =
    """
        SELECT $TASK_ID_COLUMN, count(distinct(tr.user_id)) as $NUMBER_OF_RESPONDED_USERS
        FROM task_results tr
        JOIN user_profiles up on up.user_id = tr.user_id
    """

internal const val FIND_TASK_QUERY =
    """
        SELECT DISTINCT id as $TASK_ID_COLUMN from tasks
    """

internal const val USER_ID_COLUMN = "user_id"
internal const val ITEM_NAME_COLUMN = "item_name"
internal const val REVISION_ID_COLUMN = "revision_id"
internal const val RESULT_COLUMN = "result"
internal const val TASK_ITEM_RESPONSE_QUERY_FORMAT =
    """
        SELECT ir.id as id, ir.task_id as $TASK_ID_COLUMN, ir.revision_id as $REVISION_ID_COLUMN, ir.user_id as $USER_ID_COLUMN,
                $ITEM_NAME_COLUMN, $RESULT_COLUMN %s
        FROM item_results ir
        JOIN user_profiles up on up.user_id = ir.user_id
    """

internal const val AVERAGE_HR_COLUMN = "avg_hr_bpm"

// NOTES check where clause, is this right condition?
internal const val GET_AVERAGE_HR_QUERY =
    """
        SELECT hr.user_id as $USER_ID_COLUMN, avg(hr.bpm) as $AVERAGE_HR_COLUMN
        FROM heartrates as hr
        JOIN user_profiles up on hr.user_id = up.user_id
        WHERE date(hr.time) = date(up.last_synced_at)
    """

internal const val PROFILE_COLUMN = "profile"

internal const val LAST_SYNC_TIME_COLUMN = "last_synced_at"

internal const val GET_USER_QUERY =
    """
        SELECT $USER_ID_COLUMN, $PROFILE_COLUMN, $LAST_SYNC_TIME_COLUMN
        FROM user_profiles
    """

internal const val AVERAGE_SLEEP_COLUMN = "avg_sleep_mins"

internal const val GET_AVERAGE_SLEEP_QUERY =
    """
        SELECT $USER_ID_COLUMN, avg(date_diff('minute', start_time, end_time)) as $AVERAGE_SLEEP_COLUMN
        FROM sleepsessions
    """

internal const val LAST_TOTAL_STEP_COLUMN = "total_steps"

internal const val GET_LAST_TOTAL_STEP_QUERY =
    """
      SELECT steps.user_id as $USER_ID_COLUMN, sum(steps.count) as $LAST_TOTAL_STEP_COLUMN
      FROM steps
      JOIN user_profiles up on steps.user_id = up.user_id
      WHERE date(steps.end_time) = date(up.last_synced_at)
    """

internal const val TIME_COLUMN = "time"

internal const val BPM_COLUMN = "bpm"

internal const val GET_HEART_RATE_QUERY =
    """
        SELECT $USER_ID_COLUMN, $TIME_COLUMN, $BPM_COLUMN
        from heartrates
        where time >= ? and time <= ?
    """

internal const val AVERAGE_HEART_RATE_QUERY =
    """
        SELECT $USER_ID_COLUMN, avg(bpm) as $AVERAGE_HR_COLUMN
        FROM heartrates
        WHERE time >= ? and time <= ?
        GROUP BY user_id
    """

internal fun makeQueryToGetAttributesOfUsers(count: Int): String =
    """
        $GET_USER_QUERY
         WHERE user_id IN ${makeInConditionString(count)}
    """

internal fun makeQueryToGetSleepOfUsers(count: Int): String =
    """
        $GET_AVERAGE_SLEEP_QUERY
         WHERE user_id IN ${makeInConditionString(count)}
         GROUP BY $USER_ID_COLUMN
    """

internal fun makeQueryToGetStepOfUsers(count: Int): String =
    """
        $GET_LAST_TOTAL_STEP_QUERY
         AND up.user_id IN ${makeInConditionString(count)}
         group by steps.user_id
    """

internal fun makeGetUserQuery(offset: Int, limit: Int) =
    """
        $GET_USER_QUERY
        OFFSET $offset LIMIT $limit
    """

internal fun makeAverageHRQuery(count: Int) =
    """
        $GET_AVERAGE_HR_QUERY
        AND up.$USER_ID_COLUMN IN ${makeInConditionString(count)}
        GROUP BY hr.$USER_ID_COLUMN
    """

internal fun makeInConditionString(count: Int): StringBuilder {
    val inConditions = StringBuilder((count * 2) + 2).apply {
        append(" (")
        repeat(count - 1) {
            append("?,")
        }
        append("?)")
    }
    return inConditions
}
