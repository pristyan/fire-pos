package com.fire.core.scheduler

import kotlin.coroutines.CoroutineContext


/**
 * Created by Chandra.
 **/

interface SchedulerProvider {

    fun computation(): CoroutineContext

    fun ui(): CoroutineContext

    fun io(): CoroutineContext

}