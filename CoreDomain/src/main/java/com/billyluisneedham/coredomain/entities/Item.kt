package com.billyluisneedham.coredomain.entities

import com.billyluisneedham.coredomain.Quantity

data class ItemToBuy(
    val id: ItemId,
    val name: ItemName,
    val isChecked: ItemIsChecked,
    val quantity: Quantity,
)

@JvmInline
value class ItemId(val value: String) {
    companion object {
        fun generate(): ItemId = ItemId(java.util.UUID.randomUUID().toString())
    }
}
@JvmInline
value class ItemName(val value: String)
@JvmInline
value class ItemIsChecked(val value: Boolean)
