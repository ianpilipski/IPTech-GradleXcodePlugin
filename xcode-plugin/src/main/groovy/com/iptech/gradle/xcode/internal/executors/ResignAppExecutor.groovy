package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.api.ResignAppSpec
import org.gradle.api.Project
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec
import org.gradle.process.internal.ExecAction

import javax.inject.Inject

class ResignAppExecutor {
    ExecOperations execOperations

    @Inject
    ResignAppExecutor(ExecOperations execOperations) {
        this.execOperations = execOperations
    }

    ExecAction exec(ResignAppSpec resignAppSpec) {
        List<String> arguments = resignAppSpec.entitlementsFile.isPresent() ? ['--entitlements', resignAppSpec.entitlementsFile.get().asFile.absolutePath ] : []
        arguments.addAll(['-f', '-s', resignAppSpec.signingCertName.get(), resignAppSpec.appPath.get().asFile.absolutePath])

        return execOperations.exec { ExecSpec es ->
            es.executable 'codesign'
            es.args arguments
        }
    }
}
