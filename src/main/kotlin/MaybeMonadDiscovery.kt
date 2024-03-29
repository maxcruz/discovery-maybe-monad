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
fun evaluator(exp: Exp): Maybe =
    when(exp) {
        is Exp.Val -> Maybe.Just(exp.a)
        is Exp.Div -> {
            val dividend = evaluator(exp.a)
            val divisor = evaluator(exp.b)
            dividend.join(divisor, ::safeDivision)
        }
    }

// So, let's create a safe version of the division and a type to model the undefined
sealed class Maybe {

    data class Just(val x: Int): Maybe()
    object Nothing: Maybe()

    // This is the pattern: a maybe unwraps his Just in a function call. No-op on Failure
    private fun flatMap(f: (x: Int) -> Maybe): Maybe =
        when (this) {
            is Just -> f.invoke(x)
            Nothing -> Nothing
        }

    // Join applies unwrapped Just value to the block, which should return a Maybe
    fun join(b: Maybe, f: (Int, Int) -> Maybe): Maybe = flatMap { x -> b.flatMap { y -> f.invoke(x, y) } }
}

fun safeDivision(x: Int, y: Int): Maybe = if (y == 0) Maybe.Nothing else Maybe.Just(x / y)

/*
Conclusion:

 We re-discovered the Maybe monad:
 - the Maybe functor that brings pure values to an impure context
 - the join sequence that do something with the pure value if succeed

 What is the point?
 - The same idea works for other effects
 - Support pure programming with effects
 - Use of effects in explicit types
 - Functions that works for any effect

 */