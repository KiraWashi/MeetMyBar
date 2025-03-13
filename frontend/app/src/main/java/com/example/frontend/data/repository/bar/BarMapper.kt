package com.example.frontend.data.repository.bar

import com.example.frontend.data.repository.toModel
import com.example.frontend.data.repository.toVo
import com.example.frontend.data.vo.BarVo
import com.example.frontend.data.vo.BasicBarVo
import com.example.frontend.data.vo.ScheduleDayVo
import com.example.frontend.data.vo.SimpleBarVo
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.model.BasicBarModel
import com.example.frontend.domain.model.ScheduleDayModel
import com.example.frontend.domain.model.SimpleBarModel

fun BarVo.toModel() = BarModel(
    id = id,
    address = address,
    name = name,
    capacity = capacity,
    drinks = drinks.map { drink ->
        drink.toModel()
    },
    planning = planning.map { day ->
        day.toModel()
    },
    city = city,
    postalCode = postalCode,
    latitude = null,
    longitude = null
)

fun SimpleBarVo.toModel() = SimpleBarModel(
    address = address,
    name = name,
    capacity = capacity,
    city = city,
    postalCode = postalCode,
    planning = planning.map { scheduleDay ->
        scheduleDay.toModel()
    }
)

fun SimpleBarModel.toVo() = SimpleBarVo(
    address = address,
    name = name,
    capacity = capacity,
    city = city,
    postalCode = postalCode,
    planning = planning.map { scheduleDay ->
        scheduleDay.toVo()
    }
)

fun ScheduleDayVo.toModel() = ScheduleDayModel(
    opening = opening,
    closing = closing,
    day = day
)

fun ScheduleDayModel.toVo() = ScheduleDayVo(
    opening = opening,
    closing = closing,
    day = day
)

fun BasicBarModel.toVo() = BasicBarVo(
    id = id,
    address = address,
    name = name,
    capacity = capacity,
    city = city,
    postalCode = postalCode,
)