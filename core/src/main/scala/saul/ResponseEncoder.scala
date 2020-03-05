package saul

trait ResponseEncoder[A] {
  def encode(value: A): Response
}
