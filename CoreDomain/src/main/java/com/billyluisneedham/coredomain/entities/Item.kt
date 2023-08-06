package com.billyluisneedham.coredomain.entities

import com.billyluisneedham.coredomain.Quantity

data class ItemToBuy(
    val id: ItemId,
    val name: ItemName,
    val isChecked: ItemIsChecked,
    val quantity: Quantity,
) {
    companion object {
        fun create(name: ItemName, quantity: Quantity): ItemToBuy =
            ItemToBuy(
                id = ItemId.generate(),
                name = name,
                isChecked = ItemIsChecked(false),
                quantity = quantity,
            )
    }
}

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
