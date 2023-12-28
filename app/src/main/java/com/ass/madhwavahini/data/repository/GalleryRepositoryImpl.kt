package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.local.dao.GalleryDao
import com.ass.madhwavahini.data.local.mapper.mapToGalleryList
import com.ass.madhwavahini.data.local.mapper.mapToHomeGalleryList
import com.ass.madhwavahini.data.remote.apis.GalleryApi
import com.ass.madhwavahini.domain.modals.HomeGallery
import com.ass.madhwavahini.domain.repository.GalleryRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.util.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GalleryRepositoryImpl(
    private val galleryDao: GalleryDao, private val galleryApi: GalleryApi
) : GalleryRepository {
    override fun getGalleryState(): Flow<UiStateList<HomeGallery>> = flow {
        var state = UiStateList<HomeGallery>(isLoading = true)
        emit(state)

        val localGallery = galleryDao.getGalleryList().mapToHomeGalleryList()

        if (localGallery.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localGallery)
            emit(state)
        }
        try {
            val result = galleryApi.getGalleryData()
            if (result.isSuccessful && result.body() != null) {
                state = if (result.body()!!.success) {
                    val data = result.body()?.data!!
                    if (data != localGallery) galleryDao.insertGallery(data.mapToGalleryList())
                    else return@flow

                    state.copy(isLoading = false, data = data)
                } else {
                    state.copy(
                        isLoading = false, error = StringUtil.DynamicText(result.body()!!.message)
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false, error = StringUtil.DynamicText("Unable to fetch")
                )
            }
        } catch (e: Exception) {
            if (localGallery.isEmpty()) state = state.copy(
                isLoading = false, error = e.getError()
            )
        } finally {
            emit(state)
        }
    }
}