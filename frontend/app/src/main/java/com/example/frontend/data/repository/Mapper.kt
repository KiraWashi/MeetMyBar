package com.example.frontend.data.repository

import com.example.frontend.data.vo.DrinkVo
import com.example.frontend.domain.model.DrinkModel

fun DrinkVo.toModel() = DrinkModel(
    id = id,
    alcoholDegree = alcoholDegree,
    name = name,
    brand = brand,
    type = type
)

fun DrinkModel.toVo() = DrinkVo(
    id = id,
    alcoholDegree = alcoholDegree,
    name = name,
    brand = brand,
    type = type
)