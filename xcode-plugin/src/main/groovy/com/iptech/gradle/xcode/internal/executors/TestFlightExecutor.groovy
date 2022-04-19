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
            '--file', spec.appFile.get().asFile.absolutePath,
            '--username', spec.userName.get(),
            '--password', spec.password.get()
        ]

        return execAlTool(args)
    }

    ExecResult upload(TestFlightSpec spec) {
        List<String> args = [
            'altool', '--upload-app',
            '--type', spec.appType.get(),
            '--file', spec.appFile.get().asFile.absolutePath,
            '--username', spec.userName.get(),
            '--password', spec.password.get()
        ]

        return execAlTool(args)
    }

    private ExecResult execAlTool(List<String> args) {
        return execOperations.exec { ExecSpec es ->
            es.executable('xcrun')
            es.args(args)
        }
    }
}