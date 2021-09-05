package com.fire.pos.presentation.main.viewmodel

import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class MainViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider
): BaseViewModel(), MainViewModelContract {
}