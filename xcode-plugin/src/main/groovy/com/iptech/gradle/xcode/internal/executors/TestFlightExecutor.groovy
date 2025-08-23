package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.api.TestFlightSpec
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import org.gradle.process.ExecResult
import javax.inject.Inject

class TestFlightExecutor {
    ExecOperations execOperations

    @Inject
    TestFlightExecutor(ExecOperations execOperations) {
        this.execOperations = execOperations
    }

    ExecResult validate(TestFlightSpec spec) {
        List<String> args = [
            'altool', '--validate-app',
            '--type', spec.appType.get(),
            '--file', spec.appFile.get().asFile.absolutePath
        ]
        Map<String, String> privateArgs = [
            '--username': spec.userName.get(),
            '--password': spec.password.get()
        ]

        return execAlTool(args, privateArgs)
    }

    ExecResult upload(TestFlightSpec spec) {
        List<String> args = [
            'altool', '--upload-app',
            '--type', spec.appType.get(),
            '--file', spec.appFile.get().asFile.absolutePath
        ]
        Map<String, String> privateArgs = [
            '--username': spec.userName.get(),
            '--password': spec.password.get()
        ]

        return execAlTool(args, privateArgs)
    }

    private ExecResult execAlTool(List<String> args, Map<String, String> privateArgs) {
        def privateString = privateArgs.collect { key, value ->
            "${key} *****"
        }.join(" ")
        def argsString = args.join(" ") + " " + privateString
        println "xcrun " + argsString
        return execOperations.exec { ExecSpec es ->
            es.executable('xcrun')
            es.args(args + privateArgs.collect { key,value -> "${key} ${value}"})
        }
    }
}