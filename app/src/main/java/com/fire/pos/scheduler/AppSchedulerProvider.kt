package com.fire.pos.scheduler

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


/**
 * Created by Chandra.
 **/

class AppSchedulerProvider: SchedulerProvider {
    override fun computation(): CoroutineContext {
        return Dispatchers.Default
    }

    override fun ui(): CoroutineContext {
        return Dispatchers.Main
    }

    override fun io(): CoroutineContext {
        return Dispatchers.IO
    }
}