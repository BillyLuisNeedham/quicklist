package com.billyluisneedham.coredomain

sealed interface Quantity {
    val value: Double
    @JvmInline value class Grams(override val value: Double) : Quantity
    @JvmInline value class Kilograms(override val value: Double) : Quantity
    @JvmInline value class Millilitres(override val value: Double) : Quantity
    @JvmInline value class Litres(override val value: Double) : Quantity
    @JvmInline value class Teaspoons(override val value: Double) : Quantity
    @JvmInline value class Tablespoons(override val value: Double) : Quantity
    @JvmInline value class Cups(override val value: Double) : Quantity
    @JvmInline value class Ounces(override val value: Double) : Quantity
    @JvmInline value class Pounds(override val value: Double) : Quantity
    @JvmInline value class Each(override val value: Double) : Quantity
}
