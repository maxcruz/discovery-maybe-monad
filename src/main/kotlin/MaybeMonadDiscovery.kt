/*
 Suppose that we want to create an evaluator for divisions
 Math             Program
 1                Value 1
 6 / 2            Division Value 6 Value 2
 8 / (4 / 2)      Division (Value 8) (Division (Value 4) (Value 2))
 */

sealed class Exp {
    class Val(val a: Int): Exp()
    class Div(val a: Exp, val b: Exp): Exp()
}

// This is the most simple and naive solution.
// But, when this method is called with an undefined division the ArithmeticException is thrown :(
fun evaluator(exp: Exp): Int {
    return when(exp) {
        is Exp.Val -> exp.a
        is Exp.Div -> evaluator(exp.a) / evaluator(exp.b)
    }
}