package com.billyluisneedham.coredomain

sealed interface Quantity {
    @JvmInline value class Grams(val value: Double) : Quantity
    @JvmInline value class Kilograms(val value: Double) : Quantity
    @JvmInline value class Millilitres(val value: Double) : Quantity
    @JvmInline value class Litres(val value: Double) : Quantity
    @JvmInline value class Teaspoons(val value: Double) : Quantity
    @JvmInline value class Tablespoons(val value: Double) : Quantity
    @JvmInline value class Cups(val value: Double) : Quantity
    @JvmInline value class Ounces(val value: Double) : Quantity
    @JvmInline value class Pounds(val value: Double) : Quantity
    @JvmInline value class Each(val value: Double) : Quantity
}
