package kstreams4s

package object kstream {
  type LeftValueJoiner[V, VO, VR] = V => Option[VO] => VR
  type OuterValueJoiner[V, VO, VR] = Option[V] => Option[VO] => VR
  type KeyExtractor[V, KO] = V => KO

  type FilterPredicate[K, V] = K => Option[V] => Boolean

  def leftValueJoiner[V, VO, VR](joiner: LeftValueJoiner[V, VO, VR]) =
    (left: V, right: VO) => joiner(left)(Option(right))

  def outerValueJoiner[V, VO, VR](
      joiner: OuterValueJoiner[V, VO, VR]
  ) =
    (left: V, right: VO) => joiner(Option(left))(Option(right))

  def filterPredicate[K, V](
      predicate: FilterPredicate[K, V]
  ) =
    (key: K, value: V) => predicate(key)(Option(value))
}
