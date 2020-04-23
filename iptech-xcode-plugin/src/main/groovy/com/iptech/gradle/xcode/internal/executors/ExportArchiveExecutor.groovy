package com.iptech.gradle.xcode.internal.executors

import com.iptech.gradle.xcode.XcodeExtension
import com.iptech.gradle.xcode.api.ExportArchiveSpec
import com.iptech.gradle.xcode.api.XcodeBuildSpec
import org.gradle.api.model.ObjectFactory
import org.gradle.process.internal.ExecAction

import javax.inject.Inject


class ExportArchiveExecutor {
    private final XcodeBuildExecutor xcodeBuildExecutor
    private final ObjectFactory objectFactory

    @Inject
    ExportArchiveExecutor(XcodeBuildExecutor xcodeBuildExecutor, ObjectFactory objectFactory) {
        this.xcodeBuildExecutor = xcodeBuildExecutor
        this.objectFactory = objectFactory
    }

    ExecAction exec(ExportArchiveSpec exportArchiveSpec) {
        XcodeBuildSpec spec = objectFactory.newInstance(XcodeBuildSpec.class)
        spec.archivePath = exportArchiveSpec.archivePath
        spec.exportOptionsPlist = exportArchiveSpec.exportOptionsPlist
        spec.exportPath = exportArchiveSpec.exportPath
        spec.additionalArguments.addAll(['clean', 'archive'])

        return xcodeBuildExecutor.exec(spec)
    }
}
