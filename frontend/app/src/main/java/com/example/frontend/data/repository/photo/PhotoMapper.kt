package com.example.frontend.data.repository.photo

import com.example.frontend.data.vo.PhotoVo
import com.example.frontend.domain.model.PhotoModel

fun PhotoVo.toModel() = PhotoModel(
    id = id,
    description = description,
    mainPhoto = mainPhoto,
    imageData = imageData,
)