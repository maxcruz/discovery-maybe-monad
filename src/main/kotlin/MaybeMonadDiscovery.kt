/*
 Suppose that we want to create an evaluator for divisions
 Math             Program
 1                Value 1
 6 / 2            Division Value 6 Value 2
 8 / (4 / 2)      Division (Value 8) (Division (Value 4) (Value 2))
 */

sealed class Exp {
    class Val(a: Int): Exp()
    class Div(a: Exp, b: Exp): Exp()
}

fun evaluator(exp: Exp): Int {
    return 0
}