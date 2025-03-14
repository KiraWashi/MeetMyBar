package com.example.frontend.data.repository

import com.example.frontend.data.vo.DrinkOfBarVo
import com.example.frontend.data.vo.DrinkTypeVo
import com.example.frontend.domain.model.DrinkOfBarModel
import com.example.frontend.domain.model.DrinkTypeModel

fun DrinkTypeVo.toModel() = DrinkTypeModel(
    id = id,
    alcoholDegree = alcoholDegree,
    name = name,
    brand = brand,
    type = type ?: "",
)

fun DrinkTypeModel.toVo() = DrinkTypeVo(
    id = id,
    alcoholDegree = alcoholDegree,
    name = name,
    brand = brand,
    type = type,
)

fun DrinkOfBarVo.toModel() = DrinkOfBarModel(
    id = id,
    alcoholDegree = alcoholDegree,
    name = name,
    brand = brand,
    type = type ?: "",
    price = price,
    volume = volume
)
