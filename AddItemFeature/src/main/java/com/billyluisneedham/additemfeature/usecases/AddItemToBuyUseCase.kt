package com.billyluisneedham.additemfeature.usecases

import com.billyluisneedham.additemfeature.datasources.ItemToBuyLocalDataSource
import com.billyluisneedham.coredomain.Quantity
import com.billyluisneedham.coredomain.entities.ItemName
import com.billyluisneedham.coredomain.entities.ItemToBuy

private const val MAX_ITEM_NAME_LENGTH = 250
internal suspend fun addItemToBuyUseCase(
    name: ItemName,
    quantity: Quantity,
    localDataSource: ItemToBuyLocalDataSource,
    maxNameLength: Int = MAX_ITEM_NAME_LENGTH,
): AddItemToBuyResult = when {
    nameIsBlank(name) -> AddItemToBuyResult.FailureEmptyName
    nameIsTooLong(
        name = name,
        maxNameLength = maxNameLength
    ) -> AddItemToBuyResult.FailureNameTooLong

    quantityIsZeroOrLess(quantity) -> AddItemToBuyResult.FailureQuantityZeroOrLess
    else -> saveItemToBuyInLocalDataSource(
        localDataSource = localDataSource,
        name = name,
        quantity = quantity
    )
}

private suspend fun saveItemToBuyInLocalDataSource(
    localDataSource: ItemToBuyLocalDataSource,
    name: ItemName,
    quantity: Quantity
) = localDataSource.addItemToBuy(
    ItemToBuy.create(
        name = name,
        quantity = quantity
    )
).fold(
    onSuccess = { AddItemToBuyResult.Success },
    onFailure = { AddItemToBuyResult.FailureWritingItem(it) }
)

private fun quantityIsZeroOrLess(quantity: Quantity) = quantity.value <= 0

private fun nameIsTooLong(
    name: ItemName,
    maxNameLength: Int
) = name.value.length > maxNameLength

private fun nameIsBlank(name: ItemName) = name.value.isBlank()


internal sealed interface AddItemToBuyResult {
    object Success : AddItemToBuyResult
    object FailureEmptyName : AddItemToBuyResult
    object FailureNameTooLong : AddItemToBuyResult
    object FailureQuantityZeroOrLess : AddItemToBuyResult
    data class FailureWritingItem(val throwable: Throwable?) : AddItemToBuyResult
}
