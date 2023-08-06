package com.billyluisneedham.additemfeature.usecases

import com.billyluisneedham.additemfeature.datasources.ItemToBuyLocalDataSource
import com.billyluisneedham.coredomain.Quantity
import com.billyluisneedham.coredomain.entities.ItemName
import com.billyluisneedham.coredomain.entities.ItemToBuy
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test


internal class AddItemToBuyUseCaseTest {

    @Test
    fun `given an item, when the item name is empty, then return failure empty name`() = runTest {
        val itemNames = listOf(ItemName(""), ItemName("     "))

        itemNames.forEach { itemName ->
            // Given
            val quantity = Quantity.Each(2.0)

            // When
            val result = addItemToBuyUseCase(
                name = itemName,
                quantity = quantity,
                localDataSource = MockItemToBuyLocalDataSourceSuccess()
            )

            // Then
            assertEquals(result, AddItemToBuyResult.FailureEmptyName)
        }
    }

    @Test
    fun `given an item, when the item name is too long, then return failure name too long`() =
        runTest {
            // Given
            val itemName = ItemName("a".repeat(101))
            val quantity = Quantity.Each(2.0)

            // When
            val result = addItemToBuyUseCase(
                name = itemName,
                quantity = quantity,
                localDataSource = MockItemToBuyLocalDataSourceSuccess(),
                maxNameLength = 100
            )

            // Then
            assertEquals(result, AddItemToBuyResult.FailureNameTooLong)
        }

    @Test
    fun `given an item, when the item is valid, then save the item in the local data source`() =
        runTest {
            // Given
            val itemName = ItemName("Milk")
            val quantity = Quantity.Each(2.0)
            val localDataSource = MockItemToBuyLocalDataSourceSuccess()

            // When
            addItemToBuyUseCase(
                name = itemName,
                quantity = quantity,
                localDataSource = localDataSource
            )

            // Then
            assertEquals(itemName, localDataSource.lastItemSaved?.name)
            assertEquals(quantity, localDataSource.lastItemSaved?.quantity)
        }

    @Test
    fun `given an item is saved in the local data source, when the save is successful, then return success`() =
        runTest {
            // Given
            val itemName = ItemName("Milk")
            val quantity = Quantity.Each(2.0)
            val localDataSource = MockItemToBuyLocalDataSourceSuccess()

            // When
            val result = addItemToBuyUseCase(
                name = itemName,
                quantity = quantity,
                localDataSource = localDataSource
            )

            // Then
            assertEquals(result, AddItemToBuyResult.Success)
        }

    @Test
    fun `given an item is saved in the local data source, when the save is unsuccessful, then return failure`() =
        runTest {
            // Given
            val itemName = ItemName("Milk")
            val quantity = Quantity.Each(2.0)
            val throwable = Throwable("Error")

            // When
            val result = addItemToBuyUseCase(
                name = itemName,
                quantity = quantity,
                localDataSource = MockItemToBuyLocalDataSourceFailure(throwable)
            )

            // Then
            assertEquals(result, AddItemToBuyResult.FailureWritingItem(throwable))
        }

    @Test
    fun `given an item, when the quantity is zero or less, then return failure quantity zero or less`() =
        runTest {
            val quantities = listOf(
                Quantity.Grams(-10.0),
                Quantity.Kilograms(0.0),
                Quantity.Millilitres(-100.0),
                Quantity.Litres(0.0),
                Quantity.Teaspoons(-1.0),
                Quantity.Tablespoons(0.0),
                Quantity.Cups(-1.0),
                Quantity.Ounces(0.0),
                Quantity.Pounds(-1.0),
                Quantity.Each(0.0)
            )

            quantities.forEach { quantity ->
                // Given
                val itemName = ItemName("Milk")

                // When
                val result = addItemToBuyUseCase(
                    name = itemName,
                    quantity = quantity,
                    localDataSource = MockItemToBuyLocalDataSourceSuccess()
                )

                // Then
                assertEquals(result, AddItemToBuyResult.FailureQuantityZeroOrLess)
            }
        }
}

private class MockItemToBuyLocalDataSourceSuccess : ItemToBuyLocalDataSource {
    var lastItemSaved: ItemToBuy? = null
    override suspend fun addItemToBuy(itemToBuy: ItemToBuy): Result<Unit> {
        lastItemSaved = itemToBuy
        return Result.success(Unit)
    }
}

private class MockItemToBuyLocalDataSourceFailure(private val throwable: Throwable) :
    ItemToBuyLocalDataSource {
    override suspend fun addItemToBuy(itemToBuy: ItemToBuy): Result<Unit> =
        Result.failure(throwable)
}
