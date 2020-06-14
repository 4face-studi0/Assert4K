package assert4k

import kotlin.js.JsName
import kotlin.jvm.JvmName
import kotlin.test.Test
import kotlin.test.assertFails

internal class AnyTest {

    data class A(private val s: String) {
        val isTrue get() = true
        val isFalse get() = false
    }

    // region reflective
    @Test
    @JsName("succeed_with_true_value")
    fun `succeed with true value`() {
        assert that A("Hello").isTrue
        assert that { A("Hello") == A("Hello") }
    }

    @Test
    @JsName("fails_with_false_value")
    fun `fails with false value`() {
        assertFails {
            assert that { A("Hello") != A("Hello") }
        }
    }

    @Test
    @JsName("WithMessage_works_on_reflective_assertion")
    fun `WithMessage works on reflective assertion`() {
        val e = assertFails {
            assert that A("Hello").isFalse { "That must be true!" }
        }
        assert that e.message equals "That must be true!"
    }
    // endregion

    // region equals
    @Test
    @JsName("succeed_with_equal_objects")
    fun `succeed with equal objects`() {
        assert that A("Hello") equals A("Hello")
    }

    @Test
    @JsName("fails_with_null_actual")
    fun `fails with null actual`() {
        val nullValue: A? = null
        assertFails {
            assert that nullValue equals A("Hello")
        }
    }

    @Test
    @JsName("fails_with_different_objects")
    fun `fails with different objects`() {
        assertFails {
            assert that A("Hello") equals A("Ciao")
        }
    }
    // endregion
}
