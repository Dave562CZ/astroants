package cz.richter.david.astroants

import java.util.*

fun <T> indexedArrayListSpliterator(iterable: Iterable<IndexedValue<T>>, size: Int): Spliterator<IndexedValue<T>> =
        Spliterators.spliterator(iterable.iterator(), size.toLong(),
                Spliterator.ORDERED.or(Spliterator.SIZED).or(Spliterator.SUBSIZED).or(Spliterator.NONNULL))
