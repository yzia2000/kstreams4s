package kstreams4s.kstream

import org.apache.kafka.streams.kstream.{KTable => KTableJ, Named, ValueJoiner}
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.{KTable => KTableS, Materialized}

final case class KTable[K, V](override val inner: KTableJ[K, V])
    extends KTableS[K, V](inner) {
  def from[K, V](table: KTableS[K, V]) = KTable(table.inner)

  def filterOption(predicate: FilterPredicate[K, V]) =
    filter(filterPredicate(predicate))

  def filterOption(predicate: FilterPredicate[K, V])(
      materialized: Materialized[K, V, ByteArrayKeyValueStore]
  ) = filter(filterPredicate(predicate), materialized)

  def filterOption(predicate: FilterPredicate[K, V])(named: Named) =
    filter(filterPredicate(predicate), named)

  def filterOption(predicate: FilterPredicate[K, V])(named: Named)(
      materialized: Materialized[K, V, ByteArrayKeyValueStore]
  ) = filter(filterPredicate(predicate), named, materialized)

  def filterNotOption(predicate: FilterPredicate[K, V]) =
    filterNot(filterPredicate(predicate))

  def filterNotOption(predicate: FilterPredicate[K, V])(
      materialized: Materialized[K, V, ByteArrayKeyValueStore]
  ) = filterNot(filterPredicate(predicate), materialized)

  def filterNotOption(predicate: FilterPredicate[K, V])(named: Named) =
    filterNot(filterPredicate(predicate), named)

  def filterNotOption(predicate: FilterPredicate[K, V])(named: Named)(
      materialized: Materialized[K, V, ByteArrayKeyValueStore]
  ) = filterNot(filterPredicate(predicate), named, materialized)

  def leftJoinOption[VO, VR](other: KTable[K, VO])(
      joiner: LeftValueJoiner[V, VO, VR]
  ) =
    this.from(
      this.leftJoin(other)(leftValueJoiner(joiner))
    )

  def leftJoinOption[VO, VR](other: KTable[K, VO])(named: Named)(
      joiner: LeftValueJoiner[V, VO, VR]
  ) =
    this.from(
      this.leftJoin(other, named)(
        leftValueJoiner[V, VO, VR](joiner)
      )
    )

  def leftJoinOption[VO, VR](other: KTable[K, VO])(
      materialized: Materialized[K, VR, ByteArrayKeyValueStore]
  )(joiner: V => Option[VO] => VR) =
    this.from(
      this.leftJoin(other, materialized)(
        leftValueJoiner[V, VO, VR](joiner)
      )
    )

  def leftJoinOption[VO, VR](other: KTable[K, VO])(named: Named)(
      materialized: Materialized[K, VR, ByteArrayKeyValueStore]
  )(joiner: V => Option[VO] => VR) =
    this.from(
      this.leftJoin(other, named, materialized)(
        leftValueJoiner[V, VO, VR](joiner)
      )
    )

  def leftJoinOption[VR, KO, VO](
      other: KTable[KO, VO]
  )(keyExtractor: KeyExtractor[V, KO])(joiner: LeftValueJoiner[V, VO, VR])(
      materialized: Materialized[K, VR, ByteArrayKeyValueStore]
  ) = this.from(
    this.leftJoin(
      other,
      keyExtractor,
      leftValueJoiner(joiner).asInstanceOf[ValueJoiner[V, VO, VR]],
      materialized
    )
  )

  def leftJoinOption[VR, KO, VO](other: KTable[KO, VO])(
      keyExtractor: KeyExtractor[V, KO]
  )(joiner: LeftValueJoiner[V, VO, VR])(named: Named)(
      materialized: Materialized[K, VR, ByteArrayKeyValueStore]
  ): KTable[K, VR] =
    this.from(
      this.leftJoin(
        other,
        keyExtractor,
        leftValueJoiner(joiner).asInstanceOf[ValueJoiner[V, VO, VR]],
        named,
        materialized
      )
    )

  def outerJoinOption[VO, VR](other: KTable[K, VO])(
      joiner: OuterValueJoiner[V, VO, VR]
  ) =
    this.from(
      this.outerJoin(other)(outerValueJoiner(joiner))
    )

  def outerJoinOption[VO, VR](other: KTable[K, VO])(named: Named)(
      joiner: OuterValueJoiner[V, VO, VR]
  ) =
    this.from(
      this.outerJoin(other, named)(
        outerValueJoiner[V, VO, VR](joiner)
      )
    )

  def outerJoinOption[VO, VR](other: KTable[K, VO])(
      materialized: Materialized[K, VR, ByteArrayKeyValueStore]
  )(joiner: OuterValueJoiner[V, VO, VR]) =
    this.from(
      this.outerJoin(other, materialized)(
        outerValueJoiner[V, VO, VR](joiner)
      )
    )

  def outerJoinOption[VO, VR](other: KTable[K, VO])(named: Named)(
      materialized: Materialized[K, VR, ByteArrayKeyValueStore]
  )(joiner: OuterValueJoiner[V, VO, VR]) =
    this.from(
      this.outerJoin(other, named, materialized)(
        outerValueJoiner[V, VO, VR](joiner)
      )
    )
}
