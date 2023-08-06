package com.billyluisneedham.additemfeature.datasources

import com.billyluisneedham.coredomain.entities.ItemToBuy

interface ItemToBuyLocalDataSource {
    suspend fun addItemToBuy(itemToBuy: ItemToBuy): Result<Unit>
}