package com.mbt925.realestate.core.domain

import com.mbt925.realestate.core.test.TaskExecutorRule
import com.mbt925.realestate.core.test.testLaunch
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
class ContextTest {

    @get:Rule
    val rule = TaskExecutorRule()

    private fun verifyStates(states: List<State>) {
        assertTrue { states.size == 3 }
        assertEquals("init", states[0].value)
        assertEquals("init,a", states[1].value)
        assertEquals("init,a,b", states[2].value)
    }

    @Test
    fun onCollecting_WithMultipleCollectors_AndDelayBetweenEffects_statesAreBeingCollectedLinearly() =
        rule.runTest {
            val delay = 1L
            val statesOne = mutableListOf<State>()
            val statesTwo = mutableListOf<State>()
            val context = Context(State("init"))
            val useCase = useCase<Effect> {
                flow {
                    emit(Effect("a"))
                    delay(delay)
                    emit(Effect("b"))
                }
            }
            val job1 = testLaunch {
                context.state.take(3).toCollection(statesOne)
            }
            val job2 = testLaunch {
                context.state.take(3).toCollection(statesTwo)
            }
            context.execute(useCase, simpleReducer)
            advanceTimeBy(delay + 1)
            job1.join(); job2.join()
            verifyStates(statesOne)
            verifyStates(statesTwo)

            val statesThree = mutableListOf<State>()
            val job3 = launch { context.state.take(1).toCollection(statesThree) }
            job3.join()
            assertEquals(listOf(State("init,a,b")), statesThree)
        }

    /**
     * A use case might emit effects immediately once they are hooked to the upstream
     * even without receiving an action from the upstream.
     *
     * Eventually effects will be emitted before any collectors observe the downstream.
     * The model needs to replay all these effects and must make sure they do not get
     * lost.
     */
    @Test
    fun onHotUseCase_whichExecutesImmediately_StateGetsReducedCorrectly() = rule.runTest {
        val initialState = "initial"
        val effects = (0..256).map { "effect$it" }
        val expectedState = (listOf(initialState) + effects).joinToString(",")
        val context = Context<State>(
            initialState = State(initialState),
        )
        val useCase = useCase {
            flowOf(*effects.map { Effect(it) }.toTypedArray())
        }

        context.execute(useCase, simpleReducer)

        context.state
            // verify we get the most recent state
            .filter { it.value == expectedState }
            .first()
    }

    private data class State(val value: String)
    private data class Effect(val param: String)

    private val simpleReducer = reducer<State, Effect> { effect ->
        copy(value = "$value,${effect.param}")
    }

}
