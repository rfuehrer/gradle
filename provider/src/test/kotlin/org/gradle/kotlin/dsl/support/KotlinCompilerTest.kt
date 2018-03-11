package org.gradle.kotlin.dsl.support

import org.gradle.kotlin.dsl.fixtures.TestWithTempFiles

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test

import java.io.File

import java.net.URLClassLoader


class KotlinCompilerTest : TestWithTempFiles() {

    @Test
    fun `can compile Kotlin source file into jar`() {

        val sourceFile =
            newFile("DeepThought.kt", """
                package hhgttg

                class DeepThought {
                    fun compute(): Int = 42
                }
            """)

        val outputJar = newFile("output.jar")
        compileToJar(outputJar, listOf(sourceFile), loggerFor<KotlinCompilerTest>())

        val answer =
            classLoaderFor(outputJar).use { it
                .loadClass("hhgttg.DeepThought")
                .newInstance()
                .run {
                    this::class.java.getMethod("compute").invoke(this)
                }
            }

        assertThat(
            answer,
            equalTo<Any>(42))

        assert(outputJar.delete())
    }

    private
    fun classLoaderFor(outputJar: File) =
        URLClassLoader.newInstance(
            arrayOf(outputJar.toURI().toURL()))
}
