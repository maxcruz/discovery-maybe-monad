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

// Non functional alternatives:
// - try/catch ArithmeticException = can't be composed, it's not deterministic
// - return -1 when the divisor is zero: require documentation and it's not referential transparent
// - return null: it's not expressive, is prone errors (NPE if is consumed from Java, or could be null by another reason)

// So, let's create a safe version of the division and a type to model the undefined
sealed class Maybe {
    class Just(x: Int): Maybe()
    object Nothing: Maybe()
}

fun safeDivision(x: Int, y: Int): Maybe = if (y == 0) Maybe.Nothing else Maybe.Just(x / y)
