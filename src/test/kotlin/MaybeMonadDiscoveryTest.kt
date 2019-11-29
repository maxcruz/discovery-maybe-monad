import org.junit.Assert.assertEquals
import org.junit.Test

class MaybeMonadDiscoveryTest {

    @Test
    fun `GIVEN a expression with just a value WHEN the evaluator is called SHOULD return the value`() {
        val singleValue = evaluator(Exp.Val(1))
        assertEquals(1, singleValue)
    }

    @Test
    fun `GIVEN a division WHEN the evaluator is called SHOULD evaluate the result`() {
        val singleDivision = evaluator(Exp.Div(Exp.Val(6), Exp.Val(2)))
        assertEquals(3, singleDivision)
    }

    @Test
    fun `GIVEN a composed expression WHEN the evaluator is called SHOULD resolve the nested operations`() {
        val composedExpression = evaluator(Exp.Div(Exp.Val(8), Exp.Div(Exp.Val(4), Exp.Val(2))))
        assertEquals(4, composedExpression)
    }
}