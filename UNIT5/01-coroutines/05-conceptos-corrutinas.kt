// 05 - Conceptos de corrutinas
// Explicación y demos: Job, jerarquía, CoroutineScope, CoroutineContext, Dispatchers y withContext.

import kotlinx.coroutines.*

fun demoJobLifecycle() {
    runBlocking {
        val job = launch {
            println("Job: started")
            try {
                repeat(10) { i ->
                    println("Job working $i")
                    delay(200)
                }
            } finally {
                println("Job: finally block - cleaning up")
            }
        }

        delay(700)
        println("Main: cancelling job")
        job.cancel()
        job.join()
        println("Main: job cancelled and joined")
    }
}

fun demoJobHierarchy() {
    runBlocking {
        val parent = launch {
            println("Parent: start")
            val child = launch {
                delay(300)
                println("Child: finished")
            }
            child.join()
            println("Parent: end")
        }
        parent.join()
    }
}

fun demoWithContextAndDispatchers() {
    runBlocking {
        println("${'$'}{Thread.currentThread().name} - runBlocking function")
        launch {
            println("${'$'}{Thread.currentThread().name} - launch function")
            withContext(Dispatchers.Default) {
                println("${'$'}{Thread.currentThread().name} - withContext(Dispatchers.Default)")
                delay(500)
                println("10 results found.")
            }
            println("${'$'}{Thread.currentThread().name} - end of launch function")
        }
        println("Loading...")
    }
}

// Por defecto este archivo no sobrescribe el main principal del proyecto. Si quieres ejecutar una demo
// concreta, reemplaza temporalmente el main de este archivo por:
// fun main() { demoJobLifecycle() }
// o
// fun main() { demoJobHierarchy() }
// o
// fun main() { demoWithContextAndDispatchers() }
