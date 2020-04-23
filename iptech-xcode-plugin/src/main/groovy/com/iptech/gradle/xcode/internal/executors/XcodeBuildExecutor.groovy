package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.process.ExecOperations
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec

import javax.inject.Inject

class XcodeBuildExecutor {
    ExecOperations execOperations

    @Inject
    XcodeBuildExecutor(ExecOperations execOperations) {
        this.execOperations = execOperations
    }

    ExecResult exec(XcodeBuildSpec spec) {
        List<String> args = []
        if(spec.configuration.isPresent()) args << '-configuration' << spec.configuration.get()
        if(spec.scheme.isPresent()) args << '-scheme' << spec.scheme.get()
        if(spec.archivePath.isPresent()) args << '-archivePath' << spec.archivePath.get().asFile.absolutePath
        if(spec.derivedDataPath.isPresent()) args << '-derivedDataPath' << spec.derivedDataPath.get().asFile.absolutePath
        if(spec.exportOptionsPlist.isPresent()) args << '-exportOptionsPlist' << spec.exportOptionsPlist.get().asFile.absolutePath
        if(spec.exportPath.isPresent()) args << '-exportPath' << spec.exportPath.get().asFile.absolutePath
        if(spec.projectPath.isPresent()) {
            File file = spec.projectPath.get().asFile
            if(file.name.endsWith('.xcodeproj')) {
                args << '-project' << file.absolutePath
            } else {
                args << '-workspace' << file.absolutePath
            }
        }
        if(spec.additionalArguments.size()>0) args.addAll(spec.additionalArguments.get().asList())

        return execOperations.exec { ExecSpec es ->
            es.executable('xcodebuild')
            es.args(args)
        }
    }
}
