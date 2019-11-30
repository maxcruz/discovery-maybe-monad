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

// This is the most simple.
// Evaluates the division and return a Maybe with Just if the operations complete or with Nothing if is undefined
fun evaluator(exp: Exp): Maybe {
    return when(exp) {
        is Exp.Val -> Maybe.Just(exp.a)
        is Exp.Div -> {
            when (val divisor = evaluator(exp.b)) {
                is Maybe.Just -> {
                    when (val dividend = evaluator(exp.a)) {
                        is Maybe.Just -> safeDivision(dividend.x, divisor.x)
                        Maybe.Nothing -> Maybe.Nothing
                    }
                }
                Maybe.Nothing -> Maybe.Nothing
            }
        }
    }
}

// So, let's create a safe version of the division and a type to model the undefined
sealed class Maybe {
    data class Just(val x: Int): Maybe()
    object Nothing: Maybe()
}

fun safeDivision(x: Int, y: Int): Maybe = if (y == 0) Maybe.Nothing else Maybe.Just(x / y)
