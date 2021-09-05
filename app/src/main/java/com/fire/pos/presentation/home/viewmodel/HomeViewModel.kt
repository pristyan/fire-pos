package com.fire.pos.presentation.home.viewmodel

import com.fire.core.base.viewmodel.BaseViewModel
import com.fire.core.scheduler.SchedulerProvider
import javax.inject.Inject


/**
 * Created by Chandra.
 **/

class HomeViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider
): BaseViewModel(), HomeViewModelContract {
}